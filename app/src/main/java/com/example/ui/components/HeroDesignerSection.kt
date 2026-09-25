package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCardSurface
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary
import kotlinx.coroutines.delay

@Composable
fun HeroDesignerSection(
    topicInput: String,
    onTopicInputChange: (String) -> Unit,
    rotatingExamples: List<String>,
    isGenerating: Boolean,
    onDesignPrompt: () -> Unit,
    onExploreHowItWorks: () -> Unit,
    modifier: Modifier = Modifier
) {
    var exampleIndex by remember { mutableStateOf(0) }
    var displayedPlaceholder by remember { mutableStateOf("") }

    LaunchedEffect(topicInput) {
        if (topicInput.isEmpty()) {
            while (true) {
                val fullExample = rotatingExamples[exampleIndex % rotatingExamples.size]
                // Type in
                for (i in 1..fullExample.length) {
                    displayedPlaceholder = fullExample.substring(0, i)
                    delay(30)
                }
                delay(3000)
                // Type out or switch
                exampleIndex++
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        // Glowing Badge
        GlowingBadge(text = "✦ AI Prompt Intelligence")

        Spacer(modifier = Modifier.height(16.dp))

        // Main Heading
        Text(
            text = "Turn Any Topic Into a Powerful AI Prompt.",
            color = SkyTextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 33.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle
        Text(
            text = "Tell Sky Prompt Designer what you want to create. Our AI transforms your idea into a clear, structured prompt designed for better AI results.",
            color = SkyTextSecondary,
            fontSize = 13.5.sp,
            textAlign = TextAlign.Center,
            lineHeight = 19.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Large Interactive Floating Designer Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            SkyCardDark.copy(alpha = 0.96f),
                            SkyCardSurface.copy(alpha = 0.92f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            SkyBlue.copy(alpha = 0.6f),
                            SkyCyan.copy(alpha = 0.45f),
                            SkyIndigo.copy(alpha = 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What do you want to create?",
                        color = SkyTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Quick suggestion cycle button
                    IconButton(
                        onClick = {
                            exampleIndex++
                            onTopicInputChange(rotatingExamples[exampleIndex % rotatingExamples.size])
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Cycle sample topic",
                            tint = SkyCyan,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Input field
                OutlinedTextField(
                    value = topicInput,
                    onValueChange = onTopicInputChange,
                    placeholder = {
                        Text(
                            text = if (displayedPlaceholder.isNotBlank()) "«$displayedPlaceholder»" else "«Create a marketing strategy for my new fitness app»",
                            color = SkyTextMuted,
                            fontSize = 13.5.sp,
                            lineHeight = 18.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = Color(0xFF334155),
                        focusedTextColor = SkyTextPrimary,
                        unfocusedTextColor = SkyTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Button: Design Prompt ✦
                Button(
                    onClick = onDesignPrompt,
                    enabled = !isGenerating,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBlue,
                        contentColor = Color(0xFF070B18)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Design Prompt ✦",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.3.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Secondary CTA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.text.ClickableText(
                        text = androidx.compose.ui.text.AnnotatedString("Explore How It Works →"),
                        onClick = { onExploreHowItWorks() },
                        style = androidx.compose.ui.text.TextStyle(
                            color = SkyCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}
