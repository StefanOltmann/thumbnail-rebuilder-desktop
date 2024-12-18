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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Quality
import model.Size
import ui.theme.DefaultSpacer
import ui.theme.DoubleSpacer
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.doublePadding
import ui.theme.halfPadding

private val settingsBoxWidth = 272.dp

private val sliderWidth = settingsBoxWidth - 32.dp

@Composable
fun SettingsPanel(
    sizeSettingState: MutableState<Size>,
    qualitySettingState: MutableState<Quality>
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SizeSettingSlider(sizeSettingState)

        DefaultSpacer()

        QualitySettingSlider(qualitySettingState)
    }
}

@Composable
private fun SizeSettingSlider(
    sizeSettingState: MutableState<Size>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black, defaultRoundedCornerShape)
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
                .width(settingsBoxWidth)
                .halfPadding()
        ) {

            Text(
                text = "SIZE",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.doublePadding()
        ) {

            Text(
                text = sizeSettingState.value.displayString,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayMedium
            )

            DoubleSpacer()

            Slider(
                value = sizeSettingState.value.ordinal.toFloat(),
                onValueChange = { sizeSettingState.value = Size.entries[it.toInt()] },
                valueRange = 0f..Size.entries.size.minus(1).toFloat(),
                steps = Size.entries.size - 2,
                modifier = Modifier
                    .height(24.dp)
                    .width(sliderWidth)
            )
        }
    }
}

@Composable
private fun QualitySettingSlider(
    qualitySettingState: MutableState<Quality>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black, defaultRoundedCornerShape)
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
                .width(settingsBoxWidth)
                .halfPadding()
        ) {

            Text(
                text = "QUALITY",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.doublePadding()
        ) {

            Text(
                text = qualitySettingState.value.displayString,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayMedium
            )

            DoubleSpacer()

            Slider(
                value = qualitySettingState.value.ordinal.toFloat(),
                onValueChange = { qualitySettingState.value = Quality.entries[it.toInt()] },
                valueRange = 0f..Quality.entries.size.minus(1).toFloat(),
                steps = Quality.entries.size - 2,
                modifier = Modifier
                    .height(24.dp)
                    .width(sliderWidth)
            )
        }
    }
}
