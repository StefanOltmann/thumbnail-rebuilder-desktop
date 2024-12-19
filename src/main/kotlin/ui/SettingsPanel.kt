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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.CompressionQuality
import model.ThumbnailResolution
import ui.icons.IconLeft
import ui.icons.IconRight
import ui.theme.DefaultSpacer
import ui.theme.buttonSize
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.halfPadding

private val settingsBoxWidth = 208.dp

@Composable
fun SettingsPanel(
    thumbnailResolutionSettingState: MutableState<ThumbnailResolution>,
    compressionQualitySettingState: MutableState<CompressionQuality>
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SizeSettingBox(thumbnailResolutionSettingState)

        DefaultSpacer()

        QualitySettingBox(compressionQualitySettingState)
    }
}

@Composable
private fun SizeSettingBox(
    thumbnailResolutionSettingState: MutableState<ThumbnailResolution>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black, defaultRoundedCornerShape)
            .width(settingsBoxWidth)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    Color.DarkGray,
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
                .halfPadding()
                .fillMaxWidth()
        ) {

            Text(
                text = "THUMBNAIL RESOLUTION",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val lower = thumbnailResolutionSettingState.value.lower()
            val higher = thumbnailResolutionSettingState.value.higher()

            ClickableIcon(
                imageVector = IconLeft,
                enabled = lower != null,
                onClick = {
                    lower?.let { thumbnailResolutionSettingState.value = it }
                },
                boxModifier = Modifier.size(buttonSize)
            )

            Text(
                text = thumbnailResolutionSettingState.value.displayString,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1F)
            )

            ClickableIcon(
                imageVector = IconRight,
                enabled = higher != null,
                onClick = {
                    higher?.let { thumbnailResolutionSettingState.value = it }
                },
                boxModifier = Modifier.size(buttonSize)
            )
        }
    }
}

@Composable
private fun QualitySettingBox(
    compressionQualitySettingState: MutableState<CompressionQuality>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black, defaultRoundedCornerShape)
            .width(settingsBoxWidth)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    Color.DarkGray,
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
                .halfPadding()
                .fillMaxWidth()
        ) {

            Text(
                text = "COMPRESSION QUALITY",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val lower = compressionQualitySettingState.value.lower()
            val higher = compressionQualitySettingState.value.higher()

            ClickableIcon(
                imageVector = IconLeft,
                enabled = lower != null,
                onClick = {
                    lower?.let { compressionQualitySettingState.value = it }
                },
                boxModifier = Modifier.size(buttonSize)
            )

            Text(
                text = compressionQualitySettingState.value.displayString,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1F)
            )

            ClickableIcon(
                imageVector = IconRight,
                enabled = higher != null,
                onClick = {
                    higher?.let { compressionQualitySettingState.value = it }
                },
                boxModifier = Modifier.size(buttonSize)
            )
        }
    }
}
