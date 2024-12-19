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

import APP_TITLE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import ui.icons.AppIcon
import ui.icons.IconClose
import ui.icons.IconDonate
import ui.icons.IconMinimize
import ui.icons.MadeByGraphic
import ui.theme.DoubleSpacer
import ui.theme.FillSpacer
import ui.theme.halfSpacing

@Composable
fun WindowScope.AppTitleBar(
    windowState: WindowState,
    exitApplication: () -> Unit
) {

    val uriHandler = LocalUriHandler.current

    WindowDraggableArea {

        Row(
            horizontalArrangement = Arrangement.spacedBy(halfSpacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(32.dp)
                .background(Color.Black)
                .padding(
                    horizontal = 2.dp
                )
        ) {

            Icon(
                imageVector = AppIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = APP_TITLE,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.offset(y = -1.dp)
            )

            FillSpacer()

            /* Must not be clickable or Windows can't be moved. */
            Image(
                imageVector = MadeByGraphic,
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .offset(y = 1.dp)
            )

            DoubleSpacer()

            ClickableIcon(
                imageVector = IconDonate,
                onClick = {
                    uriHandler.openUri("https://github.com/sponsors/StefanOltmann")
                }
            )

            ClickableIcon(
                imageVector = IconMinimize,
                onClick = {
                    windowState.isMinimized = true
                }
            )

            ClickableIcon(
                imageVector = IconClose,
                onClick = exitApplication
            )
        }
    }
}
