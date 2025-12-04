package HoloX.fireos_tv_app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    background = BgTop,
    surface = Surface,
    primary = Accent,
    secondary = Accent2,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onPrimary = Surface
)

@Composable
fun FireOSTvAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = AppTypography,
        content = content
    )
}
