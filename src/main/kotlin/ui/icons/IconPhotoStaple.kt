package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconPhotoStaple: ImageVector
    get() {
        if (_IconPhotoStaple != null) {
            return _IconPhotoStaple!!
        }
        _IconPhotoStaple = ImageVector.Builder(
            name = "IconPhotoStaple",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(360f, 520f)
                horizontalLineToRelative(400f)
                lineTo(622f, 340f)
                lineToRelative(-92f, 120f)
                lineToRelative(-62f, -80f)
                lineToRelative(-108f, 140f)
                close()
                moveTo(120f, 840f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(40f, 760f)
                verticalLineToRelative(-520f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(520f)
                horizontalLineToRelative(680f)
                verticalLineToRelative(80f)
                lineTo(120f, 840f)
                close()
                moveTo(280f, 680f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(200f, 600f)
                verticalLineToRelative(-440f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(280f, 80f)
                horizontalLineToRelative(200f)
                lineToRelative(80f, 80f)
                horizontalLineToRelative(280f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(920f, 240f)
                verticalLineToRelative(360f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(840f, 680f)
                lineTo(280f, 680f)
                close()
                moveTo(280f, 600f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(-360f)
                lineTo(527f, 240f)
                lineToRelative(-80f, -80f)
                lineTo(280f, 160f)
                verticalLineToRelative(440f)
                close()
                moveTo(280f, 600f)
                verticalLineToRelative(-440f)
                verticalLineToRelative(440f)
                close()
            }
        }.build()

        return _IconPhotoStaple!!
    }

@Suppress("ObjectPropertyName")
private var _IconPhotoStaple: ImageVector? = null
