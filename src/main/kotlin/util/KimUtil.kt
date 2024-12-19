/*
 * 🔧 Thumbnail Fixer 🔧
 * Copyright (C) 2024 Stefan Oltmann
 * https://stefan-oltmann.de/thumbnail-rebuilder-desktop
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package util

import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.ImageWriteException
import com.ashampoo.kim.model.ImageFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.CompressionQuality
import java.io.File

private const val THUMBNAIL_TOO_BIG_MESSAGE = "APP1 Segment is too long"

private val supportedImageFormats = listOf(
    ImageFormat.JPEG, ImageFormat.PNG,
    ImageFormat.WEBP, ImageFormat.JXL
)

enum class ProcessResult {

    /* Skipped */
    UNSUPPORTED_FORMAT,
    ALREADY_UP_TO_DATE,

    /* Successful */
    SUCCESS,
    SUCCESS_WITH_REDUCED_QUALITY,

    /* Failed */
    FAILED
}

suspend fun rebuildThumbnail(
    file: File,
    longSidePx: Int,
    quality: Int
): ProcessResult = withContext(Dispatchers.IO) {

    try {

        val originalBytes = file.readBytes()

        val metadata = Kim.readMetadata(originalBytes)

        /* Check first if we can process this format. */
        if (supportedImageFormats.contains(metadata?.imageFormat))
            return@withContext ProcessResult.UNSUPPORTED_FORMAT

        var newBytes: ByteArray? = null

        var targetQuality: Int = quality

        while (true) {

            try {

                val thumbnailBytes = createThumbnailBytes(
                    originalBytes = originalBytes,
                    longSidePx = longSidePx,
                    quality = targetQuality
                )

                newBytes = Kim.updateThumbnail(
                    bytes = originalBytes,
                    thumbnailBytes = thumbnailBytes
                )

                /*
                 * Break the loop if we managed to create
                 * new bytes that fit into the EXIF.
                 */
                break

            } catch (ex: ImageWriteException) {

                /* Break if the failure has another cause. */
                if (ex.message?.startsWith(THUMBNAIL_TOO_BIG_MESSAGE) == false)
                    break

                /* Reduce quality */
                targetQuality -= 5

                /* Break if we got below our threshold */
                if (targetQuality <= CompressionQuality.MIN.percent)
                    break
            }
        }

        if (newBytes == null)
            return@withContext ProcessResult.FAILED

        val lastModified = file.lastModified()

        val tempFile = File(file.absolutePath + ".tmp")

        try {

            tempFile.writeBytes(newBytes)

            val success = tempFile.renameTo(file)

            if (!success)
                error("Failed to replace $file")

            /* Restore last modified date */
            file.setLastModified(lastModified)

        } finally {

            tempFile.deleteOnExit()
        }

        if (targetQuality != quality)
            return@withContext ProcessResult.SUCCESS_WITH_REDUCED_QUALITY

        return@withContext ProcessResult.SUCCESS

    } catch (ignore: Throwable) {

        ignore.printStackTrace()

        return@withContext ProcessResult.FAILED
    }
}
