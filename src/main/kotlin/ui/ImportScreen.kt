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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.dragData
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.icons.IconDownload
import ui.icons.IconPhotoStaple
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.doubleSpacing

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ImportScreen(
    onFilesImport: (List<String>) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(doubleSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var showTargetBorder by remember { mutableStateOf(false) }

        val dragAndDropTarget = remember {
            object : DragAndDropTarget {

                override fun onStarted(event: DragAndDropEvent) {
                    showTargetBorder = true
                }

                override fun onEnded(event: DragAndDropEvent) {
                    showTargetBorder = false
                }

                override fun onDrop(event: DragAndDropEvent): Boolean {

                    val fileList = event.dragData() as? DragData.FilesList

                    if (fileList != null)
                        onFilesImport(fileList.readFiles())

                    return true
                }
            }
        }

        val borderColor = MaterialTheme.colorScheme.onBackground

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = 400.dp,
                    height = 200.dp
                )
                .then(
                    if (showTargetBorder)
                        Modifier.background(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                            shape = defaultRoundedCornerShape
                        )
                    else
                        Modifier
                )
                .drawBehind {
                    drawRoundRect(
                        color = borderColor,
                        style = Stroke(
                            width = if (showTargetBorder) 6f else 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 15f), 0f)
                        ),
                        cornerRadius = CornerRadius(
                            defaultSpacing.toPx()
                        )
                    )
                }
                .dragAndDropTarget(
                    shouldStartDragAndDrop = { true },
                    target = dragAndDropTarget
                )
        ) {

            if (showTargetBorder) {

                Icon(
                    imageVector = IconDownload,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(96.dp)
                )

            } else {

                Column(
                    verticalArrangement = Arrangement.spacedBy(doubleSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = IconPhotoStaple,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(48.dp)
                    )

                    Text(
                        text = "Drag & drop your photos here",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

