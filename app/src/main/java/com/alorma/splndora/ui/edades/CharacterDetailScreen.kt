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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.splndora.data.Character
import com.alorma.splndora.ui.theme.SplendoraTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    character: Character?,
    onSave: (String, LocalDate, Boolean, LocalDate?) -> Unit,
    onDelete: (Character) -> Unit,
    onBack: () -> Unit
) {
    var name by remember(character) { mutableStateOf(character?.name ?: "") }
    var birthDate by remember(character) { mutableStateOf(character?.birthDate ?: LocalDate.of(1700, 1, 1)) }
    var isException by remember(character) { mutableStateOf(character?.isException ?: false) }
    var activationDate by remember(character) { mutableStateOf(character?.activationDate ?: birthDate.plusYears(13)) }
    
    var showBirthDatePicker by remember { mutableStateOf(false) }
    var showActivationDatePicker by remember { mutableStateOf(false) }

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

            // Birth Date Picker
            DatePickerField(
                label = "Birth Date",
                date = birthDate,
                onClick = { showBirthDatePicker = true }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Exception Character", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Uses a specific activation date",
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
                // Activation Date Picker for Exceptions
                DatePickerField(
                    label = "Activation Date",
                    date = activationDate,
                    onClick = { showActivationDatePicker = true },
                    isError = !activationDate.isAfter(birthDate),
                    supportingText = if (!activationDate.isAfter(birthDate)) "Must be after birth date" else null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isActivationDateValid = !isException || activationDate.isAfter(birthDate)

            Button(
                onClick = { 
                    onSave(name, birthDate, isException, if (isException) activationDate else null) 
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && isActivationDateValid
            ) {
                Text("Save Character")
            }
        }
    }

    if (showBirthDatePicker) {
        HistoricalDatePickerDialog(
            initialDate = birthDate,
            onDateSelected = {
                birthDate = it
                // If activation date is before birth date, reset it to birth date + 13 years
                if (isException && !activationDate.isAfter(it)) {
                    activationDate = it.plusYears(13)
                }
                showBirthDatePicker = false
            },
            onDismiss = { showBirthDatePicker = false }
        )
    }

    if (showActivationDatePicker) {
        HistoricalDatePickerDialog(
            initialDate = activationDate,
            onDateSelected = {
                activationDate = it
                showActivationDatePicker = false
            },
            onDismiss = { showActivationDatePicker = false }
        )
    }
}

@Composable
fun DatePickerField(
    label: String,
    date: LocalDate,
    onClick: () -> Unit,
    isError: Boolean = false,
    supportingText: String? = null
) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = if (isError) CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)) else CardDefaults.outlinedCardColors(),
        border = if (isError) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error)) else CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(label, style = MaterialTheme.typography.labelMedium, color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text("Change", color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
            }
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
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
        yearRange = 1650..1800
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
