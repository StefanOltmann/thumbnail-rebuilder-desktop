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

package ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import model.CompressionQuality
import model.ThumbnailResolution
import ui.theme.DoubleSpacer
import ui.theme.defaultRoundedCornerShape
import ui.theme.halfPadding
import ui.theme.lightGray
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
    val compressionQualitySettingState = remember { mutableStateOf(CompressionQuality.GOOD) }
    val skipExistingStettingState = remember { mutableStateOf(true) }
    val preserveModificationDateStettingState = remember { mutableStateOf(false) }

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

                    val result = rebuildThumbnail(
                        file = file,
                        longSidePx = longSidePx,
                        quality = quality,
                        skipExisting = skipExisting,
                        preserveModificationDate = preserveModificationDate
                    )

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

                    // TODO Animation
                    Text(
                        text = "Processing files...",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

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


