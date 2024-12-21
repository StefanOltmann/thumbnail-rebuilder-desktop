/*
 * 🔧 Thumbnail Fixer 🔧
 * Copyright (C) 2024 Stefan Oltmann
 * https://github.com/StefanOltmann/thumbnail-fixer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.ashampoo.kim.Kim
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.CompressionQuality
import model.ThumbnailResolution
import org.jetbrains.skia.Image
import ui.theme.DoubleSpacer
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.halfPadding
import ui.theme.halfSpacing
import ui.theme.lightGray
import util.ProcessResult
import util.cleanPath
import util.findAllFilesRecursive
import util.rebuildThumbnail
import java.io.File

private const val VIPS_INSTALL_URL = "https://www.libvips.org/install.html"

@Composable
fun ContentView(
    vipsLoaded: Boolean
) {

    val processingFilesState = remember { mutableStateOf(false) }

    val thumbnailResolutionSettingState = remember { mutableStateOf(ThumbnailResolution.GOOD) }
    val compressionQualitySettingState = remember { mutableStateOf(CompressionQuality.MAX) }
    val skipExistingStettingState = remember { mutableStateOf(true) }
    val preserveModificationDateStettingState = remember { mutableStateOf(true) }

    val counter = remember { mutableStateOf(0) }
    val resultsMap = remember { mutableStateMapOf<String, ProcessResult>() }

    val images = remember { mutableStateListOf<ImageBitmap>() }

    val scope = rememberCoroutineScope()

    val onFilesImport: (List<String>) -> Unit = { filePaths ->

        val longSidePx = thumbnailResolutionSettingState.value.longSidePx
        val quality = compressionQualitySettingState.value.percent
        val skipExisting = skipExistingStettingState.value
        val preserveModificationDate = preserveModificationDateStettingState.value

        scope.launch {

            try {

                processingFilesState.value = true

                val files = filePaths.map { File(cleanPath(it)) }

                findAllFilesRecursive(files).collect { file ->

                    /* Ignore XMP sidecars and don't count them. */
                    if (file.name.endsWith(".xmp", ignoreCase = true))
                        return@collect

                    val result = rebuildThumbnail(
                        file = file,
                        longSidePx = longSidePx,
                        quality = quality,
                        skipExisting = skipExisting,
                        preserveModificationDate = preserveModificationDate
                    )

                    resultsMap[file.absolutePath] = result

                    counter.value = counter.value + 1

                    if (result.isSuccess()) {

                        val originalBytes = file.readBytes()

                        val metadata = Kim.readMetadata(originalBytes)

                        val exifBytes = metadata!!.getExifThumbnailBytes()!!

                        val image = Image.makeFromEncoded(exifBytes).toComposeImageBitmap()

                        if (images.size > 5)
                            images.removeAt(0)

                        images.add(image)

                        delay(2000)
                    }

                    println(file.absolutePath + " -> " + result)
                }

            } finally {

                processingFilesState.value = false
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {

            if (vipsLoaded) {

                if (processingFilesState.value) {

                    /* Entertain with a nice photo stack animation while we are working. */

                    AnimatedPhotoStack(images)

                } else if (resultsMap.isNotEmpty()) {

                    val successCount = derivedStateOf { resultsMap.count { it.value.isSuccess() } }

                    val skippedCount = derivedStateOf {
                        resultsMap.count { it.value == ProcessResult.ALREADY_UP_TO_DATE }
                    }

                    val unsupportedCount = derivedStateOf {
                        resultsMap.count { it.value == ProcessResult.UNSUPPORTED_FORMAT }
                    }

                    val failedCount = derivedStateOf { resultsMap.count { it.value == ProcessResult.FAILED } }

                    Text(
                        text = "Processed ${counter.value} files",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    DoubleSpacer()

                    Text(
                        text = "${successCount.value} successful",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${skippedCount.value} already up to date",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${unsupportedCount.value} unsupported",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${failedCount.value} failed",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    DoubleSpacer()

                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onBackground,
                                defaultRoundedCornerShape
                            )
                            .padding(
                                horizontal = defaultSpacing,
                                vertical = halfSpacing
                            )
                            .clickable {
                                resultsMap.clear()
                            }
                    ) {

                        Text(
                            text = "Clear results",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                } else {

                    DropTarget(onFilesImport)

                    DoubleSpacer()

                    SettingsPanel(
                        thumbnailResolutionSettingState = thumbnailResolutionSettingState,
                        compressionQualitySettingState = compressionQualitySettingState,
                        skipExistingStettingState = skipExistingStettingState,
                        preserveModificationDateStettingState = preserveModificationDateStettingState
                    )
                }

            } else {

                Text(
                    text = "Please install libvips!",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )

                DoubleSpacer()

                val uriHandler = LocalUriHandler.current

                Text(
                    text = VIPS_INSTALL_URL,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.clickable {
                        uriHandler.openUri(VIPS_INSTALL_URL)
                    }
                )
            }
        }
    }
}

@Composable
fun FileList(
    files: List<File>
) {

    val lazyListState = rememberLazyListState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.halfPadding()
        ) {

            items(files) { file ->

                Box(
                    modifier = Modifier
                        .halfPadding()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            defaultRoundedCornerShape
                        )
                        .fillMaxWidth()
                        .height(48.dp)
                ) {

                    Text(
                        text = file.path,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(lazyListState),
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
            style = defaultScrollbarStyle().copy(
                unhoverColor = lightGray.copy(alpha = 0.4f),
                hoverColor = lightGray
            )
        )
    }
}


