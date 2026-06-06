package com.alorma.splndora.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute : NavKey {
    @Serializable
    data object Edades : NavRoute

    @Serializable
    data object Formulas : NavRoute
}
