package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.data.local.CodeAtlasDatabase
import com.example.data.repository.CodeAtlasRepository
import com.example.ui.screens.ConceptDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LanguagesScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SearchResultsScreen
import com.example.ui.theme.AtlasBorder
import com.example.ui.theme.AtlasCyan
import com.example.ui.theme.AtlasSurface
import com.example.ui.theme.AtlasTextMuted
import com.example.ui.theme.AtlasTextPrimary
import com.example.ui.theme.AtlasTextSecondary
import com.example.ui.theme.AtlasViolet
import com.example.ui.theme.CodeAtlasTheme
import com.example.ui.viewmodel.CodeAtlasViewModel

import com.example.ui.screens.AiSolverScreen
import androidx.compose.material.icons.filled.AutoAwesome

enum class AtlasScreen(val title: String) {
    HOME("Home"),
    SEARCH("Search"),
    AI("AI Solver"),
    LANGUAGES("Languages"),
    SAVED("Saved"),
    DETAIL("Detail")
}

class MainActivity : ComponentActivity() {

    private val viewModel: CodeAtlasViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = CodeAtlasDatabase.getDatabase(applicationContext, lifecycleScope)
                val repository = CodeAtlasRepository(
                    languageDao = db.languageDao(),
                    entryDao = db.knowledgeEntryDao(),
                    bookmarkDao = db.bookmarkDao(),
                    searchHistoryDao = db.searchHistoryDao()
                )
                @Suppress("UNCHECKED_CAST")
                return CodeAtlasViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            CodeAtlasTheme(darkTheme = isDarkTheme) {
                CodeAtlasApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CodeAtlasApp(viewModel: CodeAtlasViewModel) {
    var currentScreen by remember { mutableStateOf(AtlasScreen.HOME) }
    var previousScreen by remember { mutableStateOf(AtlasScreen.HOME) }

    fun navigateTo(screen: AtlasScreen) {
        if (currentScreen != AtlasScreen.DETAIL) {
            previousScreen = currentScreen
        }
        currentScreen = screen
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (currentScreen != AtlasScreen.DETAIL) {
                NavigationBar(
                    containerColor = AtlasSurface,
                    contentColor = AtlasTextPrimary,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = currentScreen == AtlasScreen.HOME,
                        onClick = { navigateTo(AtlasScreen.HOME) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen == AtlasScreen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AtlasCyan,
                            selectedTextColor = AtlasCyan,
                            unselectedIconColor = AtlasTextMuted,
                            unselectedTextColor = AtlasTextMuted,
                            indicatorColor = AtlasCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_home")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AtlasScreen.SEARCH,
                        onClick = { navigateTo(AtlasScreen.SEARCH) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen == AtlasScreen.SEARCH) Icons.Filled.Search else Icons.Outlined.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AtlasCyan,
                            selectedTextColor = AtlasCyan,
                            unselectedIconColor = AtlasTextMuted,
                            unselectedTextColor = AtlasTextMuted,
                            indicatorColor = AtlasCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_search")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AtlasScreen.AI,
                        onClick = { navigateTo(AtlasScreen.AI) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = "AI"
                            )
                        },
                        label = { Text("AI Solver", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AtlasViolet,
                            selectedTextColor = AtlasViolet,
                            unselectedIconColor = AtlasTextMuted,
                            unselectedTextColor = AtlasTextMuted,
                            indicatorColor = AtlasViolet.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_ai")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AtlasScreen.LANGUAGES,
                        onClick = { navigateTo(AtlasScreen.LANGUAGES) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen == AtlasScreen.LANGUAGES) Icons.Filled.Code else Icons.Outlined.Code,
                                contentDescription = "Languages"
                            )
                        },
                        label = { Text("Languages", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AtlasCyan,
                            selectedTextColor = AtlasCyan,
                            unselectedIconColor = AtlasTextMuted,
                            unselectedTextColor = AtlasTextMuted,
                            indicatorColor = AtlasCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_languages")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AtlasScreen.SAVED,
                        onClick = { navigateTo(AtlasScreen.SAVED) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen == AtlasScreen.SAVED) Icons.Filled.Bookmarks else Icons.Outlined.Bookmarks,
                                contentDescription = "Saved"
                            )
                        },
                        label = { Text("Saved", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AtlasCyan,
                            selectedTextColor = AtlasCyan,
                            unselectedIconColor = AtlasTextMuted,
                            unselectedTextColor = AtlasTextMuted,
                            indicatorColor = AtlasCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_saved")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    AtlasScreen.HOME -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToSearch = { navigateTo(AtlasScreen.SEARCH) },
                        onNavigateToDetail = { entryId ->
                            viewModel.selectEntry(entryId)
                            navigateTo(AtlasScreen.DETAIL)
                        },
                        onNavigateToLanguages = { navigateTo(AtlasScreen.LANGUAGES) },
                        onNavigateToAi = { navigateTo(AtlasScreen.AI) }
                    )

                    AtlasScreen.SEARCH -> SearchResultsScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { entryId ->
                            viewModel.selectEntry(entryId)
                            navigateTo(AtlasScreen.DETAIL)
                        },
                        onNavigateToAi = { query ->
                            if (query.isNotBlank()) viewModel.updateAiQuery(query)
                            navigateTo(AtlasScreen.AI)
                        }
                    )

                    AtlasScreen.AI -> AiSolverScreen(
                        viewModel = viewModel
                    )

                    AtlasScreen.LANGUAGES -> LanguagesScreen(
                        viewModel = viewModel,
                        onLanguageSelected = { slug ->
                            navigateTo(AtlasScreen.SEARCH)
                        }
                    )

                    AtlasScreen.SAVED -> SavedScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { entryId ->
                            viewModel.selectEntry(entryId)
                            navigateTo(AtlasScreen.DETAIL)
                        },
                        onSearchQuery = { query ->
                            navigateTo(AtlasScreen.SEARCH)
                        }
                    )

                    AtlasScreen.DETAIL -> ConceptDetailScreen(
                        viewModel = viewModel,
                        onBack = {
                            viewModel.clearSelectedEntry()
                            currentScreen = previousScreen
                        },
                        onNavigateToSearchWithQuery = { relatedQuery ->
                            viewModel.performSearch(relatedQuery)
                            navigateTo(AtlasScreen.SEARCH)
                        }
                    )
                }
            }
        }
    }
}
