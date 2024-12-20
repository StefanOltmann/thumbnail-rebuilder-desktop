/*
 * 🔧 Thumbnail Fixer 🔧
 * Copyright (C) 2024 Stefan Oltmann
 * https://stefan-oltmann.de/thumbnail-fixer
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import ui.icons.IconCheckboxChecked
import ui.icons.IconCheckboxUnchecked
import ui.icons.IconLeft
import ui.icons.IconRight
import ui.theme.DefaultSpacer
import ui.theme.DoubleSpacer
import ui.theme.FillSpacer
import ui.theme.buttonSize
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.doubleSpacing
import ui.theme.halfPadding

private val settingsBoxWidth = 208.dp

@Composable
fun SettingsPanel(
    thumbnailResolutionSettingState: MutableState<ThumbnailResolution>,
    compressionQualitySettingState: MutableState<CompressionQuality>,
    skipExistingStettingState: MutableState<Boolean>,
    preserveModificationDateStettingState: MutableState<Boolean>
) {

    Column {

        Column(
            modifier = Modifier
                .background(Color.Black, defaultRoundedCornerShape)
                .width(settingsBoxWidth.times(2).plus(doubleSpacing))
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = defaultSpacing,
                        top = defaultSpacing,
                        end = defaultSpacing,
                        bottom = 2.dp
                    )
            ) {

                ClickableIcon(
                    imageVector = if (skipExistingStettingState.value)
                        IconCheckboxChecked
                    else
                        IconCheckboxUnchecked,
                    onClick = {
                        skipExistingStettingState.value = !skipExistingStettingState.value
                    }
                )

                DefaultSpacer()

                Text(
                    text = "Skip existing thumbnails if resolution matches",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.offset(y = -2.dp)
                )

                FillSpacer()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = defaultSpacing,
                        top = 2.dp,
                        end = defaultSpacing,
                        bottom = defaultSpacing
                    )
            ) {

                ClickableIcon(
                    imageVector = if (preserveModificationDateStettingState.value)
                        IconCheckboxChecked
                    else
                        IconCheckboxUnchecked,
                    onClick = {
                        preserveModificationDateStettingState.value = !preserveModificationDateStettingState.value
                    }
                )

                DefaultSpacer()

                Text(
                    text = "Preserve modification dates",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.offset(y = -2.dp)
                )

                FillSpacer()
            }
        }

        DoubleSpacer()

        Row(
            horizontalArrangement = Arrangement.spacedBy(doubleSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {

            SizeSettingBox(thumbnailResolutionSettingState)

            QualitySettingBox(compressionQualitySettingState)
        }
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
