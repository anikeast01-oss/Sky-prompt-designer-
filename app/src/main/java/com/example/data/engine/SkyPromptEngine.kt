package com.example.data.engine

import com.example.model.PromptImprovementResult
import com.example.model.PromptResult
import com.example.model.PromptScore
import com.example.model.PromptStructure
import java.util.UUID

object SkyPromptEngine {

    fun generatePrompt(
        topic: String,
        customRole: String = "",
        audience: String = "General",
        tone: String = "Professional",
        outputFormat: String = "Markdown",
        length: String = "Detailed",
        constraintsText: String = "",
        engineBadge: String = "Sky Neural Intelligence"
    ): PromptResult {
        val cleanTopic = topic.trim().ifBlank { "Create an impactful strategy" }
        val role = if (customRole.isNotBlank()) customRole else inferRole(cleanTopic)
        val objective = inferObjective(cleanTopic, audience)
        val context = inferContext(cleanTopic, audience)
        val instructions = inferInstructions(cleanTopic, audience, tone, length)
        val baseConstraints = inferConstraints(cleanTopic, tone)
        val allConstraints = if (constraintsText.isNotBlank()) {
            baseConstraints + constraintsText.split("\n").filter { it.isNotBlank() }
        } else {
            baseConstraints
        }

        val structure = PromptStructure(
            topic = cleanTopic,
            role = role,
            context = context,
            objective = objective,
            instructions = instructions,
            constraints = allConstraints,
            outputFormat = outputFormat,
            audience = audience,
            tone = tone
        )

        val fullPrompt = buildFullPromptText(structure, length)
        val score = evaluatePrompt(structure)

        return PromptResult(
            id = UUID.randomUUID().toString(),
            topic = cleanTopic,
            fullPrompt = fullPrompt,
            structure = structure,
            score = score,
            engineName = engineBadge
        )
    }

    fun improvePrompt(existingPrompt: String): PromptImprovementResult {
        val clean = existingPrompt.trim()
        val originalScoreVal = calculateRawPromptScore(clean)
        
        // Extract or synthesize improved components
        val detectedTopic = extractTopicFromPrompt(clean)
        val improvedRole = inferRole(detectedTopic)
        val improvedObjective = "Execute the core requirements of: '$clean' with rigorous depth, structural clarity, and high-impact precision."
        val improvedContext = "The request requires authoritative domain insight, eliminating superficial generalities and providing actionable, real-world utility."
        
        val improvedInstructions = listOf(
            "Begin with an executive summary outlining the core thesis and strategic value.",
            "Deconstruct the solution into logically sequenced, implementable phases or modules.",
            "Provide concrete examples, formulas, or tactical workflows rather than abstract concepts.",
            "Anticipate potential friction points, failure modes, and mitigation strategies.",
            "Conclude with an action checklist or validation criteria to ensure flawless execution."
        )

        val improvedConstraints = listOf(
            "Do not include generic fluff, filler sentences, or repetitive preambles.",
            "Ensure every recommendation is directly actionable and backed by industry best practices.",
            "Maintain an authoritative, precise $improvedRole perspective throughout.",
            "Format with clear visual hierarchy, bold headings, and bulleted sub-points."
        )

        val structure = PromptStructure(
            topic = detectedTopic,
            role = improvedRole,
            context = improvedContext,
            objective = improvedObjective,
            instructions = improvedInstructions,
            constraints = improvedConstraints,
            outputFormat = "Structured Markdown with Executive Summary & Action Steps",
            audience = "Professional",
            tone = "Strategic & Authoritative"
        )

        val improvedFull = buildFullPromptText(structure, "Detailed")
        val improvedScoreVal = (originalScoreVal + 38).coerceIn(92, 98)

        val keyImprovements = listOf(
            "Added authoritative Persona & Domain Role framing",
            "Transformed vague requests into 5 sequenced execution directives",
            "Introduced negative constraints to eliminate AI fluff & hallucinations",
            "Defined explicit output schema with executive summary and checklist",
            "Established professional tone and targeted reader persona"
        )

        return PromptImprovementResult(
            originalPrompt = clean,
            improvedPrompt = improvedFull,
            originalScore = originalScoreVal,
            improvedScore = improvedScoreVal,
            keyImprovements = keyImprovements,
            structure = structure
        )
    }

    private fun inferRole(topic: String): String {
        val lower = topic.lowercase()
        return when {
            lower.contains("youtube") || lower.contains("video") || lower.contains("script") ->
                "Senior Creative Director & Viral YouTube Script Architect"
            lower.contains("marketing") || lower.contains("growth") || lower.contains("fitness app") || lower.contains("campaign") ->
                "Chief Marketing Officer & Omnichannel Growth Strategist"
            lower.contains("python") || lower.contains("code") || lower.contains("software") || lower.contains("program") || lower.contains("developer") ->
                "Staff Software Engineer & Principal Computer Science Educator"
            lower.contains("quantum") || lower.contains("physics") || lower.contains("science") || lower.contains("math") ->
                "Distinguished STEM Researcher & Master Conceptual Explainer"
            lower.contains("startup") || lower.contains("business") || lower.contains("plan") || lower.contains("pitch") ->
                "Venture Capital Partner & Tier-1 Startup Operator"
            lower.contains("blog") || lower.contains("seo") || lower.contains("article") || lower.contains("content") ->
                "Lead Content Strategist & High-Conversion Copywriting Specialist"
            lower.contains("image") || lower.contains("midjourney") || lower.contains("art") || lower.contains("design") ->
                "Master Prompt Engineer & Visual Generation Director"
            lower.contains("study") || lower.contains("tutor") || lower.contains("learn") ->
                "Cognitive Learning Specialist & Master Academic Mentor"
            lower.contains("health") || lower.contains("diet") || lower.contains("workout") ->
                "Elite Sports Performance Coach & Certified Physiologist"
            else -> {
                val cleanWord = topic.split(" ").take(3).joinToString(" ").replace(Regex("[^a-zA-Z0-9 ]"), "")
                "Principal Domain Authority & Strategic $cleanWord Advisor"
            }
        }
    }

    private fun inferObjective(topic: String, audience: String): String {
        return "Deliver a world-class, rigorously researched, and directly actionable output tailored specifically for $audience audience regarding: \"$topic\"."
    }

    private fun inferContext(topic: String, audience: String): String {
        return "The user seeks an exceptional, high-signal result that stands out from standard generic responses. The target audience is $audience, requiring the ideal balance of intellectual depth, practical utility, and structured engagement."
    }

    private fun inferInstructions(topic: String, audience: String, tone: String, length: String): List<String> {
        return listOf(
            "Analyze the fundamental core mechanisms and primary drivers behind \"$topic\".",
            "Synthesize your response using a $tone perspective with unambiguous, evidence-backed arguments.",
            "Break down complex concepts into intuitive frameworks, actionable steps, or clear visual mental models.",
            "Incorporate high-value real-world examples, tactical edge-cases, and practical benchmarks.",
            "Organize the delivery with strong semantic hierarchy, avoiding unbroken blocks of text.",
            if (length == "Short") "Keep explanations dense and punchy without sacrificing foundational nuance."
            else "Explore secondary implications, proactive risk considerations, and long-term optimization strategies."
        )
    }

    private fun inferConstraints(topic: String, tone: String): List<String> {
        return listOf(
            "Never use generic AI platitudes (e.g., 'In conclusion', 'Delve into', 'Fast-paced world').",
            "Avoid superficial overviews; every claim must provide concrete utility or specific rationale.",
            "Maintain an authentic $tone voice from start to finish without breaking persona.",
            "Strictly adhere to the specified output schema and markdown conventions."
        )
    }

    fun buildFullPromptText(s: PromptStructure, length: String = "Detailed"): String {
        return buildString {
            appendLine("### ROLE & PERSONA")
            appendLine("You are an elite ${s.role}. You possess comprehensive mastery of this domain, combining strategic vision with hands-on technical execution.")
            appendLine()
            appendLine("### OBJECTIVE")
            appendLine(s.objective)
            appendLine()
            appendLine("### CONTEXT & AUDIENCE")
            appendLine("• Context: ${s.context}")
            appendLine("• Target Audience: ${s.audience}")
            appendLine("• Communication Tone: ${s.tone}")
            appendLine("• Detail Depth: $length")
            appendLine()
            appendLine("### STEP-BY-STEP INSTRUCTIONS")
            s.instructions.forEachIndexed { i, instr ->
                appendLine("${i + 1}. $instr")
            }
            appendLine()
            appendLine("### CONSTRAINTS & QUALITY STANDARDS")
            s.constraints.forEach { c ->
                appendLine("• $c")
            }
            appendLine()
            appendLine("### EXPECTED OUTPUT FORMAT")
            appendLine("Deliver the final output formatted in clean ${s.outputFormat} utilizing:")
            appendLine("- Clear # H1, ## H2, ### H3 thematic headers")
            appendLine("- Bulleted summaries and structured tables where data is compared")
            appendLine("- Key takeaway callouts formatted with blockquotes ('>')")
            appendLine("- A standalone 'Next Steps / Action Checklist' at the end")
        }
    }

    private fun evaluatePrompt(s: PromptStructure): PromptScore {
        val wordCount = s.topic.split(" ").size
        val clarity = (94 + (wordCount % 5)).coerceIn(90, 99)
        val context = 93
        val specificity = (92 + (wordCount % 7)).coerceIn(91, 98)
        val structure = 98
        val outputDef = 92
        val overall = ((clarity * 0.25) + (context * 0.2) + (specificity * 0.2) + (structure * 0.2) + (outputDef * 0.15)).toInt()

        val recommendation = when {
            s.audience == "General" -> "Your prompt is strong. Adding a specific target audience (e.g., 'Senior Engineers' or 'Beginners') could make the output more consistent."
            s.constraints.size < 3 -> "Adding 1-2 negative constraints (what the AI must NOT do) will significantly eliminate fluff."
            else -> "Excellent architecture! The role framing and step-by-step instructions ensure high-precision generation across modern LLMs."
        }

        return PromptScore(
            overall = overall.coerceIn(90, 99),
            clarity = clarity,
            context = context,
            specificity = specificity,
            structure = structure,
            outputDefinition = outputDef,
            recommendation = recommendation
        )
    }

    private fun calculateRawPromptScore(prompt: String): Int {
        if (prompt.isBlank()) return 30
        var score = 45
        if (prompt.length > 50) score += 10
        if (prompt.contains("?")) score += 5
        if (prompt.contains("step", ignoreCase = true) || prompt.contains("format", ignoreCase = true)) score += 10
        if (prompt.contains("you are", ignoreCase = true) || prompt.contains("act as", ignoreCase = true)) score += 15
        return score.coerceIn(38, 68)
    }

    private fun extractTopicFromPrompt(prompt: String): String {
        val trimmed = prompt.replace(Regex("^(act as|you are|please|can you|write a|create a|give me)\\s+", RegexOption.IGNORE_CASE), "").trim()
        val firstSentence = trimmed.split(".", "\n", "?").firstOrNull()?.trim() ?: trimmed
        return if (firstSentence.length > 80) firstSentence.take(80) + "..." else firstSentence
    }
}
