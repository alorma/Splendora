package com.alorma.splndora.ui.edades

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.splndora.data.Character
import com.alorma.splndora.ui.theme.SplendoraTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    characters: List<CharacterUiModel>,
    onCharacterClick: (Character) -> Unit,
    onAddCharacter: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edades (1729)") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCharacter) {
                Icon(Icons.Default.Add, contentDescription = "Add Character")
            }
        }
    ) { padding ->
        if (characters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No characters found in 1729.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(characters) { uiModel ->
                    CharacterItem(uiModel, onClick = { onCharacterClick(uiModel.character) })
                }
            }
        }
    }
}

@Composable
fun CharacterItem(uiModel: CharacterUiModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (uiModel.isActivated) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = uiModel.character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Age: ${uiModel.age} years",
                    style = MaterialTheme.typography.bodySmall
                )
                if (uiModel.isActivated) {
                    Text(
                        text = "ACTIVATED",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListPreview() {
    SplendoraTheme {
        CharacterListScreen(
            characters = listOf(
                CharacterUiModel(
                    character = Character(name = "Albus", birthDate = LocalDate.of(1710, 1, 1)),
                    age = 19,
                    isActivated = true
                ),
                CharacterUiModel(
                    character = Character(name = "Scorpius", birthDate = LocalDate.of(1720, 5, 20)),
                    age = 9,
                    isActivated = false
                )
            ),
            onCharacterClick = {},
            onAddCharacter = {}
        )
    }
}
