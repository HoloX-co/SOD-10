package HoloX.fireos_tv_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import HoloX.fireos_tv_app.ui.theme.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FireOSTvAppTheme {
                AppNav(
                    onSupport = { openUrl("https://github.com/HoloX-co/SOD-10") }
                )
            }
        }
    }

    private fun openUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }
}

@Composable
private fun AppNav(onSupport: () -> Unit) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "main") {
        composable("main") {
            MainScreen(
                onSupport = onSupport,
                onAbout = { nav.navigate("about") }
            )
        }
        composable("about") {
            AboutScreen(onBack = { nav.popBackStack() })
        }
    }
}

@Composable
private fun MainScreen(
    onSupport: () -> Unit,
    onAbout: () -> Unit
) {
    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgTop, BgBottom))
            )
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 1200.dp)
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome to HoloX FireOS TV App",
                fontSize = 34.sp,
                color = TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            TvButton(
                text = "Give Star on Github",
                onClick = onSupport
            )

            TvButton(
                text = "About us",
                onClick = onAbout
            )
        }
    }
}

@Composable
private fun AboutScreen(onBack: () -> Unit) {
    val scroll = rememberScrollState()
    BackHandler { onBack() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgTop, BgBottom))
            )
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 1200.dp)
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "About us",
                fontSize = 26.sp,
                color = TextPrimary
            )

            Text(
                text = "We build TV-friendly apps for FireOS devices.",
                fontSize = 20.sp,
                color = TextPrimary
            )

            Text(
                text = "Visit our website or explore our projects.",
                fontSize = 20.sp,
                color = TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            TvButton(
                text = "Back to Main",
                onClick = onBack
            )
        }
    }
}

@Composable
private fun TvButton(
    text: String,
    onClick: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (focused) 1.05f else 1f,
        label = "scale"
    )

    val bg by animateColorAsState(
        targetValue = if (focused)
            Accent.copy(alpha = 0.18f)
        else
            SurfaceElevated,
        label = "bg"
    )

    val border by animateColorAsState(
        targetValue = if (focused)
            Accent
        else
            Color.White.copy(alpha = 0.06f),
        label = "border"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = if (focused) (-2).dp.toPx() else 0f
                shadowElevation = if (focused) 18.dp.toPx() else 6.dp.toPx()
                shape = RoundedCornerShape(14.dp)
                clip = false
            },
        color = bg,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(2.dp, border)
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.textButtonColors(
                contentColor = TextPrimary
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = text,
                fontSize = 22.sp
            )
        }
    }
}
