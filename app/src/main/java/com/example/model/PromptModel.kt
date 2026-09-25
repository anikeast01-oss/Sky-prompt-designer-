package com.example.model

data class PromptStructure(
    val topic: String = "",
    val role: String = "",
    val context: String = "",
    val objective: String = "",
    val instructions: List<String> = emptyList(),
    val constraints: List<String> = emptyList(),
    val outputFormat: String = "",
    val audience: String = "General",
    val tone: String = "Professional"
)

data class PromptScore(
    val overall: Int = 94,
    val clarity: Int = 96,
    val context: Int = 92,
    val specificity: Int = 95,
    val structure: Int = 98,
    val outputDefinition: Int = 90,
    val recommendation: String = "Your prompt is strong. Adding a specific target audience could make the output more consistent."
)

data class PromptResult(
    val id: String,
    val topic: String,
    val fullPrompt: String,
    val structure: PromptStructure,
    val score: PromptScore,
    val engineName: String = "Sky AI Intelligence",
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

data class PromptImprovementResult(
    val originalPrompt: String,
    val improvedPrompt: String,
    val originalScore: Int = 54,
    val improvedScore: Int = 96,
    val keyImprovements: List<String> = emptyList(),
    val structure: PromptStructure = PromptStructure()
)

data class PromptTemplate(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val iconKey: String,
    val defaultTopic: String,
    val role: String,
    val audience: String = "Professional",
    val tone: String = "Professional",
    val outputFormat: String = "Markdown"
)

enum class GenerationStep(val title: String, val subtitle: String) {
    UNDERSTANDING("Understanding your topic...", "Parsing intent and core semantic vectors"),
    ANALYZING_INTENT("Analyzing intent", "Mapping conceptual dependencies & target goal"),
    BUILDING_ROLE("Building AI role", "Synthesizing persona & domain authority"),
    STRUCTURING_INSTRUCTIONS("Structuring instructions", "Formulating precise step-by-step directives"),
    OPTIMIZING_OUTPUT("Optimizing output format", "Applying output constraints & validation checks"),
    PROMPT_READY("Prompt ready", "Finalizing polished, ready-to-run prompt")
}
