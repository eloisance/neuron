package com.neuron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.neuron.ui.composable.ChallengeScreen
import com.neuron.ui.composable.DashboardScreen
import com.neuron.ui.composable.ResultScreen
import com.neuron.ui.model.ChallengeRoute
import com.neuron.ui.model.DashboardRoute
import com.neuron.ui.model.ResultRoute
import com.neuron.ui.model.Route
import com.neuron.ui.theme.NeuronTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = remember { mutableStateListOf<Route>(DashboardRoute) }
            NeuronTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = { key ->
                            getNavEntry(
                                backStack = backStack,
                                innerPadding = innerPadding,
                                key = key,
                            )
                        }
                    )
                }
            }
        }
    }

    private fun getNavEntry(
        backStack: MutableList<Route>,
        innerPadding: PaddingValues,
        key: Route,
    ): NavEntry<Route> {
        return when (key) {
            is DashboardRoute -> NavEntry(key) {
                DashboardScreen(
                    onNavigateToChallenge = {
                        backStack.add(ChallengeRoute)
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }

            is ChallengeRoute -> NavEntry(key) {
                ChallengeScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                    onNavigateToResult = {
                        backStack.removeLastOrNull()
                        backStack.add(ResultRoute)
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }

            is ResultRoute -> NavEntry(key) {
                ResultScreen(
                    onRestart = {
                        backStack[backStack.lastIndex] = ChallengeRoute
                    },
                    onGoHome = {
                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}