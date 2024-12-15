package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import io.github.composegears.valkyrie.AppIcon
import ui.icons.IconClose
import ui.icons.IconMaximize
import ui.icons.IconMinimize
import ui.theme.FillSpacer
import ui.theme.HalfSpacer
import ui.theme.halfSpacing

@Composable
fun WindowScope.AppTitleBar(
    windowState: WindowState,
    exitApplication: () -> Unit
) {

    WindowDraggableArea {

        Row(
            horizontalArrangement = Arrangement.spacedBy(halfSpacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(32.dp)
                .background(Color.Black)
        ) {

            HalfSpacer()

            Icon(
                imageVector = AppIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Thumbnail Rebuilder",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.offset(y = -1.dp)
            )

            FillSpacer()

            AppTitleBarIcon(
                imageVector = IconMinimize,
                onClick = {
                    windowState.isMinimized = true
                }
            )

            AppTitleBarIcon(
                imageVector = IconMaximize,
                onClick = {

                    if (windowState.placement != WindowPlacement.Maximized)
                        windowState.placement = WindowPlacement.Maximized
                    else
                        windowState.placement = WindowPlacement.Floating
                }
            )

            AppTitleBarIcon(
                imageVector = IconClose,
                onClick = exitApplication
            )

            HalfSpacer()
        }
    }
}

@Composable
private fun AppTitleBarIcon(
    imageVector: ImageVector,
    onClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = if (isHovered)
            MaterialTheme.colorScheme.secondary
        else
            MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .hoverable(interactionSource)
            .clickable(onClick = onClick)
    )
}
