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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import ui.icons.IconPlay
import ui.theme.DefaultSpacer
import ui.theme.FillSpacer
import ui.theme.defaultSpacing

@Composable
fun SettingsPanel() {

    Row(
        horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(48.dp)
    ) {

        DefaultSpacer()

        SettingsSlider("Size")


        SettingsSlider("Quality")

        FillSpacer()

        ClickableIcon(
            imageVector = IconPlay,
            onClick = {
                println("Start!")
            }
        )

        DefaultSpacer()
    }
}

@Composable
private fun SettingsSlider(
    label: String
) {

    var sliderValue by remember { mutableStateOf(0f) }

    Column {

        Text(
            text = "$label: ${sliderValue.toInt()}",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.height(24.dp)
        )

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..3f,
            steps = 2,
            modifier = Modifier
                .height(24.dp)
                .width(200.dp)
        )
    }
}

//@Composable
//private fun SettingsButton(
//    text: String,
//    onClick: () -> Unit
//) {
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .border(
//                width = 2.dp,
//                color = MaterialTheme.colorScheme.onBackground,
//                shape = defaultRoundedCornerShape
//            )
//            .size(32.dp)
//            .clickable(onClick = onClick)
//    ) {
//
//        Text(
//            text = text,
//            color = MaterialTheme.colorScheme.onBackground,
//            style = MaterialTheme.typography.bodyMedium.copy(
//                fontWeight = FontWeight.Bold
//            )
//        )
//    }
//}
