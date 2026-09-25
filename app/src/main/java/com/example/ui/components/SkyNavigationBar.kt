package com.example.ui.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyIndigo
import com.example.ui.theme.SkyPurple
import com.example.ui.theme.SkyTextMuted
import com.example.ui.theme.SkyTextPrimary
import com.example.ui.theme.SkyTextSecondary
import com.example.viewmodel.NavTab

@Composable
fun SkyTopBar(
    isDarkTheme: Boolean,
    isProUser: Boolean,
    isApiKeyConfigured: Boolean,
    onToggleTheme: () -> Unit,
    onOpenProModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(
                if (isDarkTheme) Color(0xFF070B18).copy(alpha = 0.92f)
                else Color(0xFFF0F6FF).copy(alpha = 0.92f)
            )
            .border(
                width = 1.dp,
                color = if (isDarkTheme) Color(0xFF1E293B).copy(alpha = 0.6f) else Color(0xFFE2E8F0),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Branding
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                listOf(SkyBlue, SkyIndigo)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sky Prompt",
                            color = if (isDarkTheme) SkyTextPrimary else Color(0xFF0F172A),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.3.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isApiKeyConfigured) SkyCyan.copy(alpha = 0.15f) else SkyIndigo.copy(alpha = 0.15f))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isApiKeyConfigured) "GEMINI 3.5" else "SKY AI",
                                color = if (isApiKeyConfigured) SkyCyan else SkyIndigo,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "Intelligent AI Prompt Designer",
                        color = if (isDarkTheme) SkyTextMuted else Color(0xFF64748B),
                        fontSize = 10.5.sp
                    )
                }
            }

            // Right Actions: Pro Status & Theme Switcher
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Pro Badge Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isProUser) SkyPurple.copy(alpha = 0.2f)
                            else SkyBlue.copy(alpha = 0.15f)
                        )
                        .border(
                            1.dp,
                            if (isProUser) SkyPurple.copy(alpha = 0.6f) else SkyBlue.copy(alpha = 0.4f),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onOpenProModal() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (isProUser) SkyPurple else SkyBlue,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isProUser) "PRO ACTIVE" else "GET PRO",
                            color = if (isProUser) SkyPurple else SkyBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.4.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Theme Toggle
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.Brightness7 else Icons.Default.Brightness4,
                        contentDescription = "Toggle theme",
                        tint = if (isDarkTheme) SkyCyan else Color(0xFF0F172A),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SkyBottomNavigationBar(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        Pair(NavTab.DESIGNER, Icons.Default.AutoAwesome),
        Pair(NavTab.IMPROVE, Icons.AutoMirrored.Filled.TrendingUp),
        Pair(NavTab.TEMPLATES, Icons.Default.Widgets),
        Pair(NavTab.CAPABILITIES, Icons.Default.Dashboard),
        Pair(NavTab.LIBRARY, Icons.Default.FolderSpecial)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(Color(0xFF070B18).copy(alpha = 0.96f))
            .border(
                width = 1.dp,
                color = Color(0xFF1E293B).copy(alpha = 0.8f),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { (tab, icon) ->
                val isSelected = currentTab == tab
                val itemColor by animateColorAsState(
                    targetValue = if (isSelected) SkyBlue else SkyTextMuted,
                    animationSpec = tween(200),
                    label = "tab_color"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.title,
                        tint = itemColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = tab.title,
                        color = itemColor,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
