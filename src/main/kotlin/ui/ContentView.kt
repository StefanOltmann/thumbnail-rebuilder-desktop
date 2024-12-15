/*
 * Thumbnail Rebuilder
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
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.defaultPadding
import ui.theme.defaultRoundedCornerShape
import ui.theme.halfPadding
import ui.theme.halfSpacing
import ui.theme.lightGray
import ui.util.cleanPath
import java.io.File

@Composable
fun ContentView() {

    var files by remember { mutableStateOf(emptyList<File>()) }

    val onFilesImport: (List<String>) -> Unit = {

        val tempFiles = mutableListOf<File>()

        for (path in it) {

            println("Import: $path")

            val file = File(cleanPath(path))

            println("--> $file")

            if (file.isFile)
                tempFiles.add(file)
        }

        files = tempFiles.toList()
    }

    if (files.isEmpty()) {

        ImportScreen(onFilesImport)

    } else {

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
                            color = MaterialTheme.colorScheme.onBackground
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
}
