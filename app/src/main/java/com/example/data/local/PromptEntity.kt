package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prompts")
data class PromptEntity(
    @PrimaryKey val id: String,
    val topic: String,
    val fullPrompt: String,
    val role: String,
    val context: String,
    val objective: String,
    val instructionsJson: String, // Delimited or JSON string
    val constraintsJson: String,
    val outputFormat: String,
    val audience: String,
    val tone: String,
    val overallScore: Int,
    val clarityScore: Int,
    val contextScore: Int,
    val specificityScore: Int,
    val structureScore: Int,
    val outputScore: Int,
    val recommendation: String,
    val engineName: String,
    val timestamp: Long,
    val isFavorite: Boolean = false
)
