package ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

@Composable
fun AnimatedPhotoStack(
    images: SnapshotStateList<ImageBitmap>
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        images.forEachIndexed { index, image ->

            val rotation = remember(image) { (-30..30).random().toFloat() }

            val scale = remember(image) { Animatable(2f) }

            // Animate properties when images list changes
            LaunchedEffect(image) {

                launch {
                    scale.animateTo(
                        targetValue = 0.5f,
                        animationSpec = tween(durationMillis = 2000)
                    )
                }
            }

            Image(
                bitmap = image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .rotate(rotation)
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value
                    )
                    .zIndex(index.toFloat())
            )
        }
    }
}
