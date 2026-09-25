package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Segment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GenerationStep
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkySuccess
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary

@Composable
fun GenerationSequenceView(
    currentStep: GenerationStep,
    inferredRole: String = "Marketing Strategist",
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_gen")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dots"
    )

    val stepProgress = when (currentStep) {
        GenerationStep.UNDERSTANDING -> 0.16f
        GenerationStep.ANALYZING_INTENT -> 0.33f
        GenerationStep.BUILDING_ROLE -> 0.50f
        GenerationStep.STRUCTURING_INSTRUCTIONS -> 0.67f
        GenerationStep.OPTIMIZING_OUTPUT -> 0.84f
        GenerationStep.PROMPT_READY -> 1.0f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = stepProgress,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SkyCardDark.copy(alpha = 0.95f),
                        Color(0xFF070B18).copy(alpha = 0.98f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        SkyBlue.copy(alpha = 0.5f),
                        SkyCyan.copy(alpha = 0.4f),
                        SkyIndigo.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top Stage Indicator Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF1E293B))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(SkyBlue, SkyCyan, SkyIndigo)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Step Header & Icon
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(280)) + slideInVertically { height -> height / 2 })
                        .togetherWith(fadeOut(animationSpec = tween(200)))
                },
                label = "step_content"
            ) { step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Stage icon representation
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    listOf(SkyBlue.copy(alpha = 0.25f), Color.Transparent)
                                )
                            )
                            .border(1.dp, SkyBlue.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        when (step) {
                            GenerationStep.UNDERSTANDING -> {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = SkyCyan,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            GenerationStep.ANALYZING_INTENT -> {
                                Icon(
                                    imageVector = Icons.Default.Hub,
                                    contentDescription = null,
                                    tint = SkyBlue,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            GenerationStep.BUILDING_ROLE -> {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = SkyIndigo,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            GenerationStep.STRUCTURING_INSTRUCTIONS -> {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Segment,
                                    contentDescription = null,
                                    tint = SkyCyan,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            GenerationStep.OPTIMIZING_OUTPUT -> {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SkySuccess,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            GenerationStep.PROMPT_READY -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = SkySuccess,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .scale(1.1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = step.title,
                        color = SkyTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.2.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = step.subtitle,
                        color = SkyTextSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Visual representations of the steps
            when (currentStep) {
                GenerationStep.UNDERSTANDING -> {
                    // Animated pulsing dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) { index ->
                            val currentDotAlpha = ((dotAlpha + index * 0.3f) % 1f).coerceIn(0.2f, 1f)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SkyBlue.copy(alpha = currentDotAlpha))
                            )
                        }
                    }
                }
                GenerationStep.ANALYZING_INTENT -> {
                    // Connected nodes canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(180.dp, 40.dp)) {
                            val w = size.width
                            val h = size.height
                            val p1 = Offset(20f, h / 2)
                            val p2 = Offset(w / 2, h / 2)
                            val p3 = Offset(w - 20f, h / 2)
                            drawLine(SkyBlue.copy(alpha = 0.4f), p1, p2, strokeWidth = 3f)
                            drawLine(SkyCyan.copy(alpha = 0.4f), p2, p3, strokeWidth = 3f)
                            drawCircle(SkyBlue, 7f, p1)
                            drawCircle(SkyCyan, 9f, p2)
                            drawCircle(SkyIndigo, 7f, p3)
                        }
                    }
                }
                GenerationStep.BUILDING_ROLE -> {
                    // Role Card
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E293B).copy(alpha = 0.8f))
                            .border(1.dp, SkyIndigo.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Role → ",
                                color = SkyIndigo,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = inferredRole.ifBlank { "Domain Specialist" },
                                color = SkyTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                GenerationStep.STRUCTURING_INSTRUCTIONS -> {
                    // Animated lines being assembled
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.width(180.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SkyCyan.copy(alpha = 0.8f))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SkyBlue.copy(alpha = 0.7f))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SkyIndigo.copy(alpha = 0.75f))
                        )
                    }
                }
                GenerationStep.OPTIMIZING_OUTPUT -> {
                    // Structure Check
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(SkySuccess.copy(alpha = 0.15f))
                            .border(1.dp, SkySuccess.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Structure ✓ Constraints Verified",
                            color = SkySuccess,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                GenerationStep.PROMPT_READY -> {
                    // Glowing Check Animation
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SkySuccess)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
