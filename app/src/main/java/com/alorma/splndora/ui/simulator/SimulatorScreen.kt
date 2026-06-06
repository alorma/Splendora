package com.alorma.splndora.ui.simulator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alorma.splndora.data.Character
import com.alorma.splndora.ui.edades.HistoricalDatePickerDialog
import org.koin.compose.viewmodel.koinViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(
    viewModel: SimulatorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showCharacterPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Temporal Simulator") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Verify activation status for any date in the 1700s.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Character Selection
            OutlinedCard(
                onClick = { showCharacterPicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.HistoryEdu, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Character", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = state.selectedCharacter?.name ?: "Select a Character",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            // Date Selection
            OutlinedCard(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Target Date", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = state.targetDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            // Results Area
            if (state.selectedCharacter != null) {
                ResultCard(state)
            }
        }
    }

    if (showDatePicker) {
        HistoricalDatePickerDialog(
            initialDate = state.targetDate,
            onDateSelected = {
                viewModel.onDateSelected(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showCharacterPicker) {
        CharacterPickerDialog(
            characters = state.characters,
            onCharacterSelected = {
                viewModel.onCharacterSelected(it)
                showCharacterPicker = false
            },
            onDismiss = { showCharacterPicker = false }
        )
    }
}

@Composable
fun ResultCard(state: SimulatorState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (state.isActivated) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (state.isActivated) "ACTIVATED" else "NOT ACTIVATED",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${state.selectedCharacter?.name} is ${state.ageOnDate} years old on this date.",
                style = MaterialTheme.typography.bodyLarge,
                color = if (state.isActivated) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onErrorContainer
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterPickerDialog(
    characters: List<Character>,
    onCharacterSelected: (Character) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Character") },
        text = {
            if (characters.isEmpty()) {
                Text("No characters found. Create one in the Edades section.")
            } else {
                Box(modifier = Modifier.heightIn(max = 300.dp)) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        characters.forEach { character ->
                            ListItem(
                                headlineContent = { Text(character.name) },
                                modifier = Modifier.fillMaxWidth().clickable { onCharacterSelected(character) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
