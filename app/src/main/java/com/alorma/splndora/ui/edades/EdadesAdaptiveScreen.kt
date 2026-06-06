package com.alorma.splndora.ui.edades

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import com.alorma.splndora.data.Character
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun EdadesAdaptiveScreen(
    viewModel: EdadesViewModel = koinViewModel()
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Character>()
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()

    var selectedCharacter by remember { mutableStateOf<Character?>(null) }
    var isAddingNew by remember { mutableStateOf(false) }

    val detailCharacter = if (isAddingNew) null else selectedCharacter

    BackHandler(navigator.canNavigateBack()) {
        scope.launch { navigator.navigateBack() }
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            CharacterListScreen(
                state = state,
                onCharacterClick = { character ->
                    selectedCharacter = character
                    isAddingNew = false
                    scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, character) }
                },
                onAddCharacter = {
                    isAddingNew = true
                    selectedCharacter = null
                    scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail) }
                },
                onUpdateTime = { viewModel.updateCurrentTime(it) }
            )
        },
        detailPane = {
            if (isAddingNew || selectedCharacter != null) {
                CharacterDetailScreen(
                    character = detailCharacter,
                    onSave = { name, birthDate, isException, activationDate ->
                        viewModel.saveCharacter(
                            id = detailCharacter?.id ?: 0,
                            name = name,
                            birthDate = birthDate,
                            isException = isException,
                            activationDate = activationDate
                        )
                        scope.launch {
                            if (navigator.canNavigateBack()) {
                                navigator.navigateBack()
                            } else {
                                isAddingNew = false
                                selectedCharacter = null
                            }
                        }
                    },
                    onDelete = { character ->
                        viewModel.deleteCharacter(character)
                        scope.launch {
                            if (navigator.canNavigateBack()) {
                                navigator.navigateBack()
                            } else {
                                selectedCharacter = null
                            }
                        }
                    },
                    onBack = {
                        scope.launch {
                            if (navigator.canNavigateBack()) {
                                navigator.navigateBack()
                            } else {
                                isAddingNew = false
                                selectedCharacter = null
                            }
                        }
                    }
                )
            }
        }
    )
}
