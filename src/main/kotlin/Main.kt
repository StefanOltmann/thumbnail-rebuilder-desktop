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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.AppTitleBar
import ui.ContentView
import ui.Footer
import ui.theme.AppTypography
import ui.theme.appColorScheme
import ui.theme.defaultRoundedCornerShape
import java.awt.Dimension

const val WINDOW_WIDTH = 800
const val WINDOW_HEIGHT = 600

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Thumbnail Rebuilder",
        undecorated = true,
        transparent = true,
        state = rememberWindowState(
            size = DpSize(WINDOW_WIDTH.dp, WINDOW_HEIGHT.dp)
        )
    ) {

        this.window.minimumSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)

        MaterialTheme(
            colorScheme = appColorScheme,
            typography = AppTypography()
        ) {

            Column(
                modifier = Modifier
                    .clip(defaultRoundedCornerShape)
                    .background(MaterialTheme.colorScheme.background)
            ) {

                AppTitleBar()

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxSize()
                ) {

                    ContentView()
                }

                Footer()
            }
        }
    }
}

