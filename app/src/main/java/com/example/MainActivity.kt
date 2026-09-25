package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.GeminiService
import com.example.ui.components.AboutSectionView
import com.example.ui.components.AdvancedControlsCard
import com.example.ui.components.AiMagicSectionView
import com.example.ui.components.AppFooterView
import com.example.ui.components.CapabilitiesSectionView
import com.example.ui.components.FaqSectionView
import com.example.ui.components.FinalCtaSectionView
import com.example.ui.components.GeneratedPromptCard
import com.example.ui.components.GenerationSequenceView
import com.example.ui.components.HeroDesignerSection
import com.example.ui.components.ImprovePromptView
import com.example.ui.components.LibraryView
import com.example.ui.components.PricingModal
import com.example.ui.components.PromptScoreCard
import com.example.ui.components.SkyAtmosphereCanvas
import com.example.ui.components.SkyBottomNavigationBar
import com.example.ui.components.SkyTopBar
import com.example.ui.components.StructureVisualizer
import com.example.ui.components.TemplatesSectionView
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyCardDark
import com.example.ui.theme.SkyCyan
import com.example.ui.theme.SkyPromptTheme
import com.example.viewmodel.NavTab
import com.example.viewmodel.SkyPromptViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: SkyPromptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            SkyPromptTheme(darkTheme = isDarkTheme) {
                SkyPromptApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SkyPromptApp(viewModel: SkyPromptViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val activeTab by viewModel.activeTab.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val isProUser by viewModel.isProUser.collectAsState()
    val showProModal by viewModel.showProModal.collectAsState()
    val isYearly by viewModel.isYearlyPricing.collectAsState()

    // Designer state
    val topicInput by viewModel.topicInput.collectAsState()
    val isAdvancedExpanded by viewModel.isAdvancedExpanded.collectAsState()
    val customRole by viewModel.customRole.collectAsState()
    val selectedAudience by viewModel.selectedAudience.collectAsState()
    val selectedTone by viewModel.selectedTone.collectAsState()
    val selectedOutput by viewModel.selectedOutput.collectAsState()
    val selectedLength by viewModel.selectedLength.collectAsState()
    val customConstraints by viewModel.customConstraints.collectAsState()

    val isGenerating by viewModel.isGenerating.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val generatedPromptResult by viewModel.generatedPromptResult.collectAsState()
    val displayedPromptText by viewModel.displayedPromptText.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()

    // Improve state
    val improveInputText by viewModel.improveInputText.collectAsState()
    val isImproving by viewModel.isImproving.collectAsState()
    val improveStepText by viewModel.improveStepText.collectAsState()
    val improvementResult by viewModel.improvementResult.collectAsState()

    // Library state
    val libraryPrompts by viewModel.libraryPrompts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterFavoritesOnly by viewModel.filterFavoritesOnly.collectAsState()

    val toastMessage by viewModel.toastMessage.collectAsState()

    // Show toast if emitted
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Celestial animated atmosphere background canvas
        SkyAtmosphereCanvas(isDarkTheme = isDarkTheme)

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                SkyTopBar(
                    isDarkTheme = isDarkTheme,
                    isProUser = isProUser,
                    isApiKeyConfigured = GeminiService.isApiKeyConfigured(),
                    onToggleTheme = { viewModel.toggleTheme() },
                    onOpenProModal = { viewModel.setShowProModal(true) }
                )
            },
            bottomBar = {
                SkyBottomNavigationBar(
                    currentTab = activeTab,
                    onTabSelected = { tab ->
                        viewModel.setActiveTab(tab)
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                when (activeTab) {
                    NavTab.DESIGNER -> {
                        // Hero Section with Interactive Input Card
                        item {
                            HeroDesignerSection(
                                topicInput = topicInput,
                                onTopicInputChange = { viewModel.setTopicInput(it) },
                                rotatingExamples = viewModel.rotatingExamples,
                                isGenerating = isGenerating,
                                onDesignPrompt = { viewModel.designPrompt() },
                                onExploreHowItWorks = {
                                    viewModel.setActiveTab(NavTab.CAPABILITIES)
                                }
                            )
                        }

                        // Generation sequence animation (during AI thinking)
                        if (isGenerating) {
                            item {
                                GenerationSequenceView(
                                    currentStep = currentStep,
                                    inferredRole = customRole.ifBlank { "Domain Specialist" }
                                )
                            }
                        }

                        // Advanced Options toggle
                        item {
                            AdvancedControlsCard(
                                isExpanded = isAdvancedExpanded,
                                onToggleExpand = { viewModel.toggleAdvancedOptions() },
                                customRole = customRole,
                                onRoleChange = { viewModel.setCustomRole(it) },
                                selectedAudience = selectedAudience,
                                onAudienceSelect = { viewModel.setAudience(it) },
                                selectedTone = selectedTone,
                                onToneSelect = { viewModel.setTone(it) },
                                selectedOutput = selectedOutput,
                                onOutputSelect = { viewModel.setOutput(it) },
                                selectedLength = selectedLength,
                                onLengthSelect = { viewModel.setLength(it) },
                                constraints = customConstraints,
                                onConstraintsChange = { viewModel.setConstraints(it) }
                            )
                        }

                        // Generated Prompt Card (when available)
                        generatedPromptResult?.let { result ->
                            item {
                                GeneratedPromptCard(
                                    result = result,
                                    displayedText = displayedPromptText,
                                    isTyping = isTyping,
                                    onSkipTyping = { viewModel.completeTypingNow() },
                                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                                    onShowToast = { viewModel.showToast(it) }
                                )
                            }

                            // Structure Visualizer
                            item {
                                StructureVisualizer(structure = result.structure)
                            }

                            // Prompt Quality Score
                            item {
                                PromptScoreCard(score = result.score)
                            }
                        }

                        // Quick Templates Teaser
                        item {
                            TemplatesSectionView(
                                onSelectTemplate = { template ->
                                    viewModel.selectTemplate(template)
                                }
                            )
                        }

                        // AI Transformation Magic
                        item {
                            AiMagicSectionView()
                        }

                        // Final Call to Action
                        item {
                            FinalCtaSectionView(
                                onDesignFirstPrompt = {
                                    coroutineScope.launch { listState.animateScrollToItem(0) }
                                },
                                onOpenPricing = { viewModel.setShowProModal(true) }
                            )
                        }

                        // Footer
                        item {
                            AppFooterView()
                        }
                    }

                    NavTab.IMPROVE -> {
                        item {
                            ImprovePromptView(
                                inputText = improveInputText,
                                onInputChange = { viewModel.setImproveInput(it) },
                                isImproving = isImproving,
                                improveStepText = improveStepText,
                                result = improvementResult,
                                onImproveClick = { viewModel.improvePrompt() },
                                onLoadIntoDesigner = { res ->
                                    viewModel.loadImprovedIntoDesigner(res)
                                },
                                onShowToast = { viewModel.showToast(it) }
                            )
                        }

                        item {
                            AppFooterView()
                        }
                    }

                    NavTab.TEMPLATES -> {
                        item {
                            TemplatesSectionView(
                                onSelectTemplate = { template ->
                                    viewModel.selectTemplate(template)
                                }
                            )
                        }

                        item {
                            AppFooterView()
                        }
                    }

                    NavTab.CAPABILITIES -> {
                        item {
                            CapabilitiesSectionView(
                                onSelectOutcome = { outcomeTopic ->
                                    viewModel.setTopicInput(outcomeTopic)
                                    viewModel.setActiveTab(NavTab.DESIGNER)
                                    viewModel.designPrompt()
                                }
                            )
                        }

                        item {
                            AiMagicSectionView()
                        }

                        item {
                            AboutSectionView()
                        }

                        item {
                            FaqSectionView()
                        }

                        item {
                            AppFooterView()
                        }
                    }

                    NavTab.PRICING -> {
                        item {
                            FinalCtaSectionView(
                                onDesignFirstPrompt = { viewModel.setActiveTab(NavTab.DESIGNER) },
                                onOpenPricing = { viewModel.setShowProModal(true) }
                            )
                        }

                        item {
                            AboutSectionView()
                        }

                        item {
                            FaqSectionView()
                        }

                        item {
                            AppFooterView()
                        }
                    }

                    NavTab.LIBRARY -> {
                        item {
                            LibraryView(
                                prompts = libraryPrompts,
                                searchQuery = searchQuery,
                                onSearchChange = { viewModel.setSearchQuery(it) },
                                filterFavorites = filterFavoritesOnly,
                                onToggleFavoritesFilter = { viewModel.toggleFavoritesFilter() },
                                onToggleFavorite = { viewModel.toggleFavorite(it) },
                                onDeletePrompt = { viewModel.deletePrompt(it) },
                                onSelectPrompt = { prompt ->
                                    viewModel.setTopicInput(prompt.topic)
                                    viewModel.selectTemplate(
                                        com.example.model.PromptTemplate(
                                            id = prompt.id,
                                            title = prompt.topic,
                                            subtitle = prompt.structure.role,
                                            category = "Saved",
                                            iconKey = "code",
                                            defaultTopic = prompt.topic,
                                            role = prompt.structure.role,
                                            audience = prompt.structure.audience,
                                            tone = prompt.structure.tone,
                                            outputFormat = prompt.structure.outputFormat
                                        )
                                    )
                                },
                                onShowToast = { viewModel.showToast(it) }
                            )
                        }

                        item {
                            AppFooterView()
                        }
                    }
                }
            }
        }

        // Pro Pricing Modal Dialog
        if (showProModal) {
            PricingModal(
                onDismiss = { viewModel.setShowProModal(false) },
                onUpgradeToPro = { viewModel.upgradeToPro() },
                isProUser = isProUser,
                isYearly = isYearly,
                onToggleYearly = { viewModel.toggleYearlyPricing(it) }
            )
        }
    }
}
