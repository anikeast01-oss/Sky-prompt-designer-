package com.example.data.repository

import com.example.data.local.PromptDao
import com.example.data.local.PromptEntity
import com.example.model.PromptResult
import com.example.model.PromptScore
import com.example.model.PromptStructure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PromptRepository(private val promptDao: PromptDao) {

    val allPrompts: Flow<List<PromptResult>> = promptDao.getAllPrompts().map { entities ->
        entities.map { it.toPromptResult() }
    }

    val favoritePrompts: Flow<List<PromptResult>> = promptDao.getFavoritePrompts().map { entities ->
        entities.map { it.toPromptResult() }
    }

    suspend fun savePrompt(prompt: PromptResult) {
        promptDao.insertPrompt(prompt.toEntity())
    }

    suspend fun toggleFavorite(id: String, isFav: Boolean) {
        promptDao.updateFavorite(id, isFav)
    }

    suspend fun deletePrompt(id: String) {
        promptDao.deletePromptById(id)
    }

    private fun PromptEntity.toPromptResult(): PromptResult {
        val instructionsList = if (instructionsJson.isBlank()) emptyList() else instructionsJson.split("|||")
        val constraintsList = if (constraintsJson.isBlank()) emptyList() else constraintsJson.split("|||")
        return PromptResult(
            id = id,
            topic = topic,
            fullPrompt = fullPrompt,
            structure = PromptStructure(
                topic = topic,
                role = role,
                context = context,
                objective = objective,
                instructions = instructionsList,
                constraints = constraintsList,
                outputFormat = outputFormat,
                audience = audience,
                tone = tone
            ),
            score = PromptScore(
                overall = overallScore,
                clarity = clarityScore,
                context = contextScore,
                specificity = specificityScore,
                structure = structureScore,
                outputDefinition = outputScore,
                recommendation = recommendation
            ),
            engineName = engineName,
            timestamp = timestamp,
            isFavorite = isFavorite
        )
    }

    private fun PromptResult.toEntity(): PromptEntity {
        return PromptEntity(
            id = id,
            topic = topic,
            fullPrompt = fullPrompt,
            role = structure.role,
            context = structure.context,
            objective = structure.objective,
            instructionsJson = structure.instructions.joinToString("|||"),
            constraintsJson = structure.constraints.joinToString("|||"),
            outputFormat = structure.outputFormat,
            audience = structure.audience,
            tone = structure.tone,
            overallScore = score.overall,
            clarityScore = score.clarity,
            contextScore = score.context,
            specificityScore = score.specificity,
            structureScore = score.structure,
            outputScore = score.outputDefinition,
            recommendation = score.recommendation,
            engineName = engineName,
            timestamp = timestamp,
            isFavorite = isFavorite
        )
    }
}
