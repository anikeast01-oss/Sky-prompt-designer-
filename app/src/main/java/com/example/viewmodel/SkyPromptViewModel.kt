package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TemplatesData
import com.example.data.local.AppDatabase
import com.example.data.remote.GeminiService
import com.example.data.repository.PromptRepository
import com.example.model.GenerationStep
import com.example.model.PromptImprovementResult
import com.example.model.PromptResult
import com.example.model.PromptTemplate
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class NavTab(val title: String, val badge: String = "") {
    DESIGNER("Designer"),
    IMPROVE("Improve"),
    TEMPLATES("Templates"),
    CAPABILITIES("Magic"),
    PRICING("Pro"),
    LIBRARY("Library")
}

class SkyPromptViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PromptRepository(AppDatabase.getInstance(application).promptDao())

    val rotatingExamples = listOf(
        "Create a marketing strategy for my new fitness app",
        "Create a YouTube video about space exploration",
        "Explain quantum physics to a beginner",
        "Build a seed-stage business plan for a B2B SaaS startup",
        "Write a Python learning roadmap with practical projects",
        "Create a high-converting landing page copy for an AI tool"
    )

    // UI Navigation & Mode
    private val _activeTab = MutableStateFlow(NavTab.DESIGNER)
    val activeTab: StateFlow<NavTab> = _activeTab.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _showProModal = MutableStateFlow(false)
    val showProModal: StateFlow<Boolean> = _showProModal.asStateFlow()

    private val _isProUser = MutableStateFlow(false)
    val isProUser: StateFlow<Boolean> = _isProUser.asStateFlow()

    private val _isYearlyPricing = MutableStateFlow(false)
    val isYearlyPricing: StateFlow<Boolean> = _isYearlyPricing.asStateFlow()

    // Prompt Designer Inputs
    private val _topicInput = MutableStateFlow("")
    val topicInput: StateFlow<String> = _topicInput.asStateFlow()

    private val _isAdvancedExpanded = MutableStateFlow(false)
    val isAdvancedExpanded: StateFlow<Boolean> = _isAdvancedExpanded.asStateFlow()

    private val _customRole = MutableStateFlow("")
    val customRole: StateFlow<String> = _customRole.asStateFlow()

    private val _selectedAudience = MutableStateFlow("General")
    val selectedAudience: StateFlow<String> = _selectedAudience.asStateFlow()

    private val _selectedTone = MutableStateFlow("Professional")
    val selectedTone: StateFlow<String> = _selectedTone.asStateFlow()

    private val _selectedOutput = MutableStateFlow("Markdown")
    val selectedOutput: StateFlow<String> = _selectedOutput.asStateFlow()

    private val _selectedLength = MutableStateFlow("Detailed")
    val selectedLength: StateFlow<String> = _selectedLength.asStateFlow()

    private val _customConstraints = MutableStateFlow("")
    val customConstraints: StateFlow<String> = _customConstraints.asStateFlow()

    // Generation State
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _currentStep = MutableStateFlow(GenerationStep.UNDERSTANDING)
    val currentStep: StateFlow<GenerationStep> = _currentStep.asStateFlow()

    private val _generatedPromptResult = MutableStateFlow<PromptResult?>(null)
    val generatedPromptResult: StateFlow<PromptResult?> = _generatedPromptResult.asStateFlow()

    private val _displayedPromptText = MutableStateFlow("")
    val displayedPromptText: StateFlow<String> = _displayedPromptText.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    // Improve Prompt State
    private val _improveInputText = MutableStateFlow("")
    val improveInputText: StateFlow<String> = _improveInputText.asStateFlow()

    private val _isImproving = MutableStateFlow(false)
    val isImproving: StateFlow<Boolean> = _isImproving.asStateFlow()

    private val _improveStepText = MutableStateFlow("")
    val improveStepText: StateFlow<String> = _improveStepText.asStateFlow()

    private val _improvementResult = MutableStateFlow<PromptImprovementResult?>(null)
    val improvementResult: StateFlow<PromptImprovementResult?> = _improvementResult.asStateFlow()

    // Library & Filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterFavoritesOnly = MutableStateFlow(false)
    val filterFavoritesOnly: StateFlow<Boolean> = _filterFavoritesOnly.asStateFlow()

    val libraryPrompts: StateFlow<List<PromptResult>> = combine(
        repository.allPrompts,
        _searchQuery,
        _filterFavoritesOnly
    ) { list, query, favOnly ->
        list.filter { item ->
            (!favOnly || item.isFavorite) &&
            (query.isBlank() || item.topic.contains(query, ignoreCase = true) || item.structure.role.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Feedback
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private var typingJob: Job? = null
    private var rotateJob: Job? = null

    init {
        // Rotating placeholder examples loop
        startRotatingExamples()
    }

    private fun startRotatingExamples() {
        rotateJob?.cancel()
        rotateJob = viewModelScope.launch {
            var index = 0
            while (true) {
                delay(4000)
                if (_topicInput.value.isBlank() && !_isGenerating.value) {
                    index = (index + 1) % rotatingExamples.size
                }
            }
        }
    }

    fun setActiveTab(tab: NavTab) {
        _activeTab.value = tab
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setShowProModal(show: Boolean) {
        _showProModal.value = show
    }

    fun toggleYearlyPricing(yearly: Boolean) {
        _isYearlyPricing.value = yearly
    }

    fun upgradeToPro() {
        _isProUser.value = true
        _showProModal.value = false
        showToast("Welcome to Sky Prompt Pro! Unlimited capabilities unlocked.")
    }

    fun setTopicInput(topic: String) {
        _topicInput.value = topic
    }

    fun toggleAdvancedOptions() {
        _isAdvancedExpanded.value = !_isAdvancedExpanded.value
    }

    fun setCustomRole(role: String) { _customRole.value = role }
    fun setAudience(audience: String) { _selectedAudience.value = audience }
    fun setTone(tone: String) { _selectedTone.value = tone }
    fun setOutput(output: String) { _selectedOutput.value = output }
    fun setLength(length: String) { _selectedLength.value = length }
    fun setConstraints(constraints: String) { _customConstraints.value = constraints }

    fun selectTemplate(template: PromptTemplate) {
        _topicInput.value = template.defaultTopic
        _customRole.value = template.role
        _selectedAudience.value = template.audience
        _selectedTone.value = template.tone
        _selectedOutput.value = template.outputFormat
        _activeTab.value = NavTab.DESIGNER
        showToast("Template '${template.title}' loaded into Designer")
    }

    fun designPrompt() {
        val topic = _topicInput.value.trim().ifBlank {
            rotatingExamples.first()
        }
        _topicInput.value = topic

        viewModelScope.launch {
            _isGenerating.value = true
            _generatedPromptResult.value = null
            _displayedPromptText.value = ""

            // Step 1: Understanding topic
            _currentStep.value = GenerationStep.UNDERSTANDING
            delay(550)

            // Step 2: Analyzing intent
            _currentStep.value = GenerationStep.ANALYZING_INTENT
            delay(650)

            // Step 3: Building AI role
            _currentStep.value = GenerationStep.BUILDING_ROLE
            delay(600)

            // Step 4: Structuring instructions
            _currentStep.value = GenerationStep.STRUCTURING_INSTRUCTIONS
            delay(600)

            // Step 5: Optimizing output format
            _currentStep.value = GenerationStep.OPTIMIZING_OUTPUT
            delay(500)

            // Step 6: Final generation & ready
            _currentStep.value = GenerationStep.PROMPT_READY
            
            val result = GeminiService.generatePrompt(
                topic = topic,
                customRole = _customRole.value,
                audience = _selectedAudience.value,
                tone = _selectedTone.value,
                outputFormat = _selectedOutput.value,
                length = _selectedLength.value,
                constraintsText = _customConstraints.value
            )

            delay(400)
            _generatedPromptResult.value = result
            _isGenerating.value = false

            // Automatically persist to local Room DB
            repository.savePrompt(result)

            // Start typing animation
            startTypingAnimation(result.fullPrompt)
        }
    }

    private fun startTypingAnimation(fullText: String) {
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            _isTyping.value = true
            val chunkSize = 6
            var currentLength = 0
            while (currentLength < fullText.length) {
                currentLength = (currentLength + chunkSize).coerceAtMost(fullText.length)
                _displayedPromptText.value = fullText.substring(0, currentLength)
                delay(12)
            }
            _displayedPromptText.value = fullText
            _isTyping.value = false
        }
    }

    fun completeTypingNow() {
        typingJob?.cancel()
        _generatedPromptResult.value?.let {
            _displayedPromptText.value = it.fullPrompt
            _isTyping.value = false
        }
    }

    fun toggleFavorite(result: PromptResult) {
        viewModelScope.launch {
            val newFav = !result.isFavorite
            repository.toggleFavorite(result.id, newFav)
            _generatedPromptResult.value?.let {
                if (it.id == result.id) {
                    _generatedPromptResult.value = it.copy(isFavorite = newFav)
                }
            }
            showToast(if (newFav) "Saved to Favorites" else "Removed from Favorites")
        }
    }

    fun deletePrompt(id: String) {
        viewModelScope.launch {
            repository.deletePrompt(id)
            if (_generatedPromptResult.value?.id == id) {
                _generatedPromptResult.value = null
            }
            showToast("Prompt deleted from history")
        }
    }

    // Improve Existing Prompt Flow
    fun setImproveInput(text: String) {
        _improveInputText.value = text
    }

    fun improvePrompt() {
        val input = _improveInputText.value.trim()
        if (input.isBlank()) {
            showToast("Please enter or paste a prompt to improve")
            return
        }

        viewModelScope.launch {
            _isImproving.value = true
            _improvementResult.value = null

            val steps = listOf(
                "Scanning prompt syntax & structure...",
                "Detecting weak instructions & ambiguous terms...",
                "Finding missing context & persona alignment...",
                "Improving hierarchy & adding step-by-step logic...",
                "Optimizing wording & negative constraints...",
                "Finalizing world-class prompt..."
            )

            for (s in steps) {
                _improveStepText.value = s
                delay(400)
            }

            val improved = GeminiService.improvePrompt(input)
            _improvementResult.value = improved
            _isImproving.value = false
            showToast("Prompt successfully optimized! Score boosted.")
        }
    }

    fun loadImprovedIntoDesigner(improvedPrompt: PromptImprovementResult) {
        _topicInput.value = improvedPrompt.structure.topic
        _customRole.value = improvedPrompt.structure.role
        _activeTab.value = NavTab.DESIGNER
        _generatedPromptResult.value = PromptResult(
            id = java.util.UUID.randomUUID().toString(),
            topic = improvedPrompt.structure.topic,
            fullPrompt = improvedPrompt.improvedPrompt,
            structure = improvedPrompt.structure,
            score = com.example.model.PromptScore(
                overall = improvedPrompt.improvedScore,
                clarity = 96,
                context = 94,
                specificity = 95,
                structure = 98,
                outputDefinition = 94,
                recommendation = "High-precision architecture optimized for modern LLMs."
            ),
            engineName = "Sky Optimizer"
        )
        _displayedPromptText.value = improvedPrompt.improvedPrompt
        _isTyping.value = false
    }

    fun setSearchQuery(q: String) {
        _searchQuery.value = q
    }

    fun toggleFavoritesFilter() {
        _filterFavoritesOnly.value = !_filterFavoritesOnly.value
    }

    fun showToast(msg: String) {
        viewModelScope.launch {
            _toastMessage.value = msg
            delay(2800)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
