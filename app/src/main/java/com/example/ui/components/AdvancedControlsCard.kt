package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdvancedControlsCard(
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    customRole: String,
    onRoleChange: (String) -> Unit,
    selectedAudience: String,
    onAudienceSelect: (String) -> Unit,
    selectedTone: String,
    onToneSelect: (String) -> Unit,
    selectedOutput: String,
    onOutputSelect: (String) -> Unit,
    selectedLength: String,
    onLengthSelect: (String) -> Unit,
    constraints: String,
    onConstraintsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val audiences = listOf("General", "Beginner", "Student", "Professional", "Expert")
    val tones = listOf("Professional", "Friendly", "Creative", "Technical", "Persuasive", "Educational")
    val outputs = listOf("Markdown", "Text", "Table", "JSON", "Code", "Step-by-step")
    val lengths = listOf("Detailed", "Short", "Medium", "Custom")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SkyCardDark.copy(alpha = 0.8f))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
    ) {
        // Toggle Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpand() }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = SkyCyan,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Advanced Options",
                    color = SkyTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isExpanded) "(Custom controls active)" else "(Optional parameters)",
                    color = SkyTextMuted,
                    fontSize = 11.sp
                )
            }

            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = SkyTextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }

        // Expandable Content
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // AI Role
                Text(
                    text = "AI Role & Persona",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = customRole,
                    onValueChange = onRoleChange,
                    placeholder = { Text("Auto-generated based on topic (or type custom role)", fontSize = 12.sp, color = SkyTextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = Color(0xFF334155),
                        focusedTextColor = SkyTextPrimary,
                        unfocusedTextColor = SkyTextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Audience Chips
                Text(
                    text = "Target Audience",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    audiences.forEach { aud ->
                        FilterChipItem(
                            label = aud,
                            isSelected = aud == selectedAudience,
                            onClick = { onAudienceSelect(aud) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tone Chips
                Text(
                    text = "Communication Tone",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tones.forEach { t ->
                        FilterChipItem(
                            label = t,
                            isSelected = t == selectedTone,
                            onClick = { onToneSelect(t) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Output Format Chips
                Text(
                    text = "Output Format",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    outputs.forEach { out ->
                        FilterChipItem(
                            label = out,
                            isSelected = out == selectedOutput,
                            onClick = { onOutputSelect(out) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Length Chips
                Text(
                    text = "Length & Nuance",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    lengths.forEach { len ->
                        FilterChipItem(
                            label = len,
                            isSelected = len == selectedLength,
                            onClick = { onLengthSelect(len) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Optional Constraints
                Text(
                    text = "Negative Constraints (Optional)",
                    color = SkyTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = constraints,
                    onValueChange = onConstraintsChange,
                    placeholder = { Text("e.g. Do not use buzzwords. Keep explanations under 500 words.", fontSize = 12.sp, color = SkyTextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = Color(0xFF334155),
                        focusedTextColor = SkyTextPrimary,
                        unfocusedTextColor = SkyTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) SkyBlue.copy(alpha = 0.2f) else Color(0xFF1E293B)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) SkyBlue else Color(0xFF334155),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) SkyBlue else SkyTextSecondary,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
