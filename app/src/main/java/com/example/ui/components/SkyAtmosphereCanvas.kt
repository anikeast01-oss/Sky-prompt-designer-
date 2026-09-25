package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class SkyParticle(
    val xRatio: Float,
    val yRatio: Float,
    val radius: Float,
    val alpha: Float,
    val speed: Float
)

@Composable
fun SkyAtmosphereCanvas(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sky_transition")

    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_glow"
    )

    val driftOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "drift_offset"
    )

    val particles = remember {
        val random = Random(42)
        List(32) {
            SkyParticle(
                xRatio = random.nextFloat(),
                yRatio = random.nextFloat(),
                radius = random.nextFloat() * 2.8f + 1.2f,
                alpha = random.nextFloat() * 0.5f + 0.25f,
                speed = random.nextFloat() * 0.4f + 0.2f
            )
        }
    }

    val primaryBg = if (isDarkTheme) Color(0xFF070B18) else Color(0xFFF1F5F9)
    val skyGlow = if (isDarkTheme) Color(0xFF0284C7).copy(alpha = 0.18f * pulseGlow) else Color(0xFF38BDF8).copy(alpha = 0.12f)
    val purpleGlow = if (isDarkTheme) Color(0xFF6366F1).copy(alpha = 0.14f * pulseGlow) else Color(0xFF818CF8).copy(alpha = 0.08f)
    val starColor = if (isDarkTheme) Color(0xFFE0F2FE) else Color(0xFF0284C7)

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Base gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = if (isDarkTheme) listOf(
                    Color(0xFF080D1A),
                    Color(0xFF0A1128),
                    Color(0xFF050811)
                ) else listOf(
                    Color(0xFFF0F6FF),
                    Color(0xFFE2E8F0),
                    Color(0xFFF8FAFC)
                )
            )
        )

        // Soft Radial celestial light glow (top right)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(skyGlow, Color.Transparent),
                center = Offset(w * 0.85f, h * 0.15f),
                radius = w * 0.9f
            ),
            center = Offset(w * 0.85f, h * 0.15f),
            radius = w * 0.9f
        )

        // Secondary Soft Nebula glow (bottom left)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(purpleGlow, Color.Transparent),
                center = Offset(w * 0.15f, h * 0.65f),
                radius = w * 0.8f
            ),
            center = Offset(w * 0.15f, h * 0.65f),
            radius = w * 0.8f
        )

        // Draw animated particles/stars
        particles.forEachIndexed { i, p ->
            val animatedY = ((p.yRatio + (driftOffset * 0.0006f * p.speed)) % 1f) * h
            val animatedX = (p.xRatio * w + kotlin.math.sin(driftOffset * 0.02f + i) * 6f).toFloat()
            val particleAlpha = (p.alpha * (0.6f + 0.4f * kotlin.math.sin(driftOffset * 0.05f + i).toFloat())).coerceIn(0.1f, 1f)

            drawCircle(
                color = starColor.copy(alpha = particleAlpha),
                radius = p.radius,
                center = Offset(animatedX, animatedY)
            )
        }
    }
}
