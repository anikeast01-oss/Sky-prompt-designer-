package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PromptStructure
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCardSurface
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkyPink
import com.example.ui.theme.SkyPurple
import com.example.ui.theme.SkySuccess
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary

data class StructureNode(
    val title: String,
    val description: String,
    val accentColor: Color,
    val getValue: (PromptStructure) -> String
)

@Composable
fun StructureVisualizer(
    structure: PromptStructure,
    modifier: Modifier = Modifier
) {
    var selectedNodeIndex by remember { mutableStateOf<Int?>(1) } // Default select Role

    val nodes = remember {
        listOf(
            StructureNode(
                title = "TOPIC",
                description = "The raw user idea or problem to solve. Serves as the foundational seed for context expansion.",
                accentColor = SkyCyan,
                getValue = { it.topic }
            ),
            StructureNode(
                title = "ROLE",
                description = "Assigns an authoritative persona and domain mastery, calibrating the model's vocabulary and cognitive depth.",
                accentColor = SkyIndigo,
                getValue = { it.role }
            ),
            StructureNode(
                title = "CONTEXT",
                description = "Frames situational background, reader persona, and environment to prevent generic or ungrounded responses.",
                accentColor = SkyPurple,
                getValue = { it.context }
            ),
            StructureNode(
                title = "OBJECTIVE",
                description = "Explicitly states the primary deliverable, preventing scope creep and ensuring focused problem-solving.",
                accentColor = SkyBlue,
                getValue = { it.objective }
            ),
            StructureNode(
                title = "INSTRUCTIONS",
                description = "Sequenced, step-by-step numbered directives that guide the model through deep logical phases.",
                accentColor = SkyCyan,
                getValue = { it.instructions.joinToString("\n• ") { item -> item } }
            ),
            StructureNode(
                title = "CONSTRAINTS",
                description = "Negative constraints that filter out AI cliches, speculative hallucinations, and unnecessary fluff.",
                accentColor = SkyPink,
                getValue = { it.constraints.joinToString("\n• ") { item -> item } }
            ),
            StructureNode(
                title = "OUTPUT FORMAT",
                description = "Enforces strict structural boundaries, markdown tables, callout blocks, or specific schemas.",
                accentColor = SkySuccess,
                getValue = { it.outputFormat }
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SkyCardDark.copy(alpha = 0.9f),
                        SkyCardSurface.copy(alpha = 0.85f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(SkyIndigo.copy(alpha = 0.4f), SkyCyan.copy(alpha = 0.3f))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "AI Prompt Architecture",
                        color = SkyTextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap any layer to inspect synthesized parameters",
                        color = SkyTextSecondary,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SkyIndigo.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "7 LAYERS",
                        color = SkyIndigo,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Connected Stack of Nodes
            nodes.forEachIndexed { index, node ->
                val isSelected = selectedNodeIndex == index
                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) node.accentColor else Color(0xFF1E293B),
                    animationSpec = tween(250),
                    label = "node_border"
                )
                val bgGlow by animateColorAsState(
                    targetValue = if (isSelected) node.accentColor.copy(alpha = 0.12f) else Color(0xFF0B132B).copy(alpha = 0.7f),
                    animationSpec = tween(250),
                    label = "node_bg"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgGlow)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable {
                            selectedNodeIndex = if (isSelected) null else index
                        }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(node.accentColor)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = node.title,
                                color = if (isSelected) node.accentColor else SkyTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Details",
                            tint = if (isSelected) node.accentColor else SkyTextMuted,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    // Expanded explanation & extracted value
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = node.description,
                                color = SkyTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF070B18))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = node.getValue(structure).ifBlank { "(Auto-inferred)" },
                                    color = SkyTextPrimary,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }

                // Downward connector line
                if (index < nodes.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = SkyTextMuted.copy(alpha = 0.5f),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
