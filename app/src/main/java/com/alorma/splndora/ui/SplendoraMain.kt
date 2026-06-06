package com.alorma.splndora.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.NavKey
import com.alorma.splndora.ui.edades.EdadesAdaptiveScreen
import com.alorma.splndora.ui.formulas.FormulasScreen
import com.alorma.splndora.ui.navigation.NavRoute
import com.alorma.splndora.ui.theme.SplendoraTheme

@Composable
fun SplendoraMain() {
    val backStack = rememberNavBackStack(NavRoute.Edades)

    SplendoraTheme {
        Scaffold(
            bottomBar = {
                val currentRoute = backStack.lastOrNull()
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == NavRoute.Edades,
                        onClick = {
                            if (currentRoute != NavRoute.Edades) {
                                backStack.clear()
                                backStack.add(NavRoute.Edades)
                            }
                        },
                        icon = { Icon(Icons.Default.HistoryEdu, contentDescription = null) },
                        label = { Text("Edades") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == NavRoute.Formulas,
                        onClick = {
                            if (currentRoute != NavRoute.Formulas) {
                                backStack.clear()
                                backStack.add(NavRoute.Formulas)
                            }
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Formulas") }
                    )
                }
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                NavDisplay(
                    backStack = backStack,
                    onBack = { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) },
                    entryProvider = { key: NavKey ->
                        when (key) {
                            is NavRoute.Edades -> NavEntry<NavKey>(key) {
                                EdadesAdaptiveScreen()
                            }
                            is NavRoute.Formulas -> NavEntry<NavKey>(key) {
                                FormulasScreen()
                            }
                            else -> NavEntry<NavKey>(key) {
                                Text("Unknown Route")
                            }
                        }
                    }
                )
            }
        }
    }
}
