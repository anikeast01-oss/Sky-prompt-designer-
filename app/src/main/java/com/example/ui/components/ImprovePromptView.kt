package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PromptImprovementResult
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCardSurface
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkySuccess
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary

@Composable
fun ImprovePromptView(
    inputText: String,
    onInputChange: (String) -> Unit,
    isImproving: Boolean,
    improveStepText: String,
    result: PromptImprovementResult?,
    onImproveClick: () -> Unit,
    onLoadIntoDesigner: (PromptImprovementResult) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedComparisonTab by remember { mutableStateOf(1) } // 0 = Original, 1 = Improved

    Column(modifier = modifier.fillMaxWidth()) {
        // Hero title block
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            SkyCardDark.copy(alpha = 0.95f),
                            SkyCardSurface.copy(alpha = 0.85f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(listOf(SkyIndigo.copy(alpha = 0.4f), SkyBlue.copy(alpha = 0.3f))),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SkyIndigo.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = SkyIndigo,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Already Have a Prompt? Make It Better.",
                        color = SkyTextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Paste any draft prompt. Sky AI will diagnose weak spots, eliminate vague instructions, and upgrade structure for maximum LLM performance.",
                    color = SkyTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputText,
                    onValueChange = onInputChange,
                    placeholder = {
                        Text(
                            text = "Paste your existing prompt here... e.g. \"Write an article about investing for beginners and give some tips.\"",
                            color = SkyTextMuted,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = Color(0xFF334155),
                        focusedTextColor = SkyTextPrimary,
                        unfocusedTextColor = SkyTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onImproveClick,
                    enabled = !isImproving && inputText.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBlue,
                        contentColor = Color(0xFF070B18)
                    )
                ) {
                    if (isImproving) {
                        CircularProgressIndicator(
                            color = Color(0xFF070B18),
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = improveStepText.ifBlank { "Optimizing prompt..." },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Improve Prompt ✦",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Comparison Result Block
        AnimatedVisibility(
            visible = result != null,
            enter = fadeIn() + slideInVertically { it / 3 }
        ) {
            result?.let { res ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyCardDark.copy(alpha = 0.95f))
                        .border(1.dp, SkySuccess.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {
                    // Header & Delta Score
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Prompt Optimization Report",
                                color = SkyTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Before vs After Comparison",
                                color = SkyTextSecondary,
                                fontSize = 12.sp
                            )
                        }

                        // Score Delta pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(SkySuccess.copy(alpha = 0.15f))
                                .border(1.dp, SkySuccess.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "${res.originalScore} → ${res.improvedScore} pts (+${res.improvedScore - res.originalScore})",
                                color = SkySuccess,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Key Improvements list
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF070B18).copy(alpha = 0.6f))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Key Upgrades Applied:",
                            color = SkyCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        res.keyImprovements.forEach { item ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text(text = "✓ ", color = SkySuccess, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = item, color = SkyTextSecondary, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Toggle Tabs: Original vs Improved
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF1E293B))
                            .padding(3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedComparisonTab == 0) Color(0xFF0F172A) else Color.Transparent)
                                .border(if (selectedComparisonTab == 0) 1.dp else 0.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.text.ClickableText(
                                text = androidx.compose.ui.text.AnnotatedString("Original Prompt"),
                                onClick = { selectedComparisonTab = 0 },
                                style = androidx.compose.ui.text.TextStyle(
                                    color = if (selectedComparisonTab == 0) SkyTextPrimary else SkyTextMuted,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedComparisonTab == 1) SkyBlue else Color.Transparent)
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.text.ClickableText(
                                text = androidx.compose.ui.text.AnnotatedString("✨ Improved Prompt"),
                                onClick = { selectedComparisonTab = 1 },
                                style = androidx.compose.ui.text.TextStyle(
                                    color = if (selectedComparisonTab == 1) Color.Black else SkyTextMuted,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Display comparison text
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF070B18))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = if (selectedComparisonTab == 0) res.originalPrompt else res.improvedPrompt,
                            color = SkyTextPrimary,
                            fontSize = 12.5.sp,
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 19.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onLoadIntoDesigner(res) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SkyBlue.copy(alpha = 0.5f))
                        ) {
                            Text("Load in Designer", color = SkyBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Improved Prompt", res.improvedPrompt)
                                clipboard.setPrimaryClip(clip)
                                onShowToast("Improved prompt copied!")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SkySuccess,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Copy Prompt", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
