package com.alorma.splndora.ui.edades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alorma.splndora.data.Character
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.splndora.ui.theme.SplendoraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    character: Character?,
    onSave: (String, LocalDate, Boolean, Int) -> Unit,
    onDelete: (Character) -> Unit,
    onBack: () -> Unit
) {
    var name by remember(character) { mutableStateOf(character?.name ?: "") }
    var birthDate by remember(character) { mutableStateOf(character?.birthDate ?: LocalDate.of(1700, 1, 1)) }
    var isException by remember(character) { mutableStateOf(character?.isException ?: false) }
    var activationAge by remember(character) { mutableStateOf(character?.activationAge?.toString() ?: "13") }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (character == null) "New Character" else "Edit Character") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (character != null) {
                        IconButton(onClick = { onDelete(character) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedCard(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Birth Date", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = birthDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text("Change", color = MaterialTheme.colorScheme.primary)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Exception Character", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Uses custom activation logic (e.g. 10 years)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = isException,
                    onCheckedChange = { isException = it }
                )
            }

            if (isException) {
                OutlinedTextField(
                    value = activationAge,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            activationAge = newValue
                        }
                    },
                    label = { Text("Activation Age") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (activationAge.toIntOrNull() == null || activationAge.toInt() <= 0) {
                            Text("Must be a positive number", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    isError = activationAge.toIntOrNull() == null || activationAge.toInt() <= 0
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isValidActivationAge = activationAge.toIntOrNull()?.let { it > 0 } ?: false

            Button(
                onClick = { onSave(name, birthDate, isException, activationAge.toIntOrNull() ?: 13) },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && (!isException || isValidActivationAge)
            ) {
                Text("Save Character")
            }
        }
    }

    if (showDatePicker) {
        HistoricalDatePickerDialog(
            initialDate = birthDate,
            onDateSelected = {
                birthDate = it
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailPreview() {
    SplendoraTheme {
        CharacterDetailScreen(
            character = Character(name = "Newt", birthDate = LocalDate.of(1705, 3, 15)),
            onSave = { _, _, _, _ -> },
            onDelete = {},
            onBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricalDatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        yearRange = 1700..1799
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
