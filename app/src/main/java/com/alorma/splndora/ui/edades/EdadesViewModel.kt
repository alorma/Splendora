package com.alorma.splndora.ui.edades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.splndora.clock.SplendoraClock
import com.alorma.splndora.data.Character
import com.alorma.splndora.data.CharacterDao
import com.alorma.splndora.engine.WizardActivationEngine
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

data class CharacterUiModel(
    val character: Character,
    val age: Int,
    val isActivated: Boolean
)

data class EdadesUiState(
    val characters: List<CharacterUiModel> = emptyList(),
    val currentTime: LocalDate = LocalDate.of(1729, 12, 31)
)

class EdadesViewModel(
    private val characterDao: CharacterDao,
    private val engine: WizardActivationEngine,
    private val clock: SplendoraClock
) : ViewModel() {

    val uiState: StateFlow<EdadesUiState> = combine(
        characterDao.getAllCharacters(),
        clock.currentDate
    ) { list, currentDate ->
        EdadesUiState(
            characters = list.map { character ->
                CharacterUiModel(
                    character = character,
                    age = engine.calculateAge(character.birthDate, currentDate),
                    isActivated = engine.isActivated(character, currentDate)
                )
            },
            currentTime = currentDate
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EdadesUiState()
    )

    fun updateCurrentTime(date: LocalDate) {
        clock.setDate(date)
    }

    fun saveCharacter(
        id: Int = 0,
        name: String,
        birthDate: LocalDate,
        isException: Boolean,
        activationAge: Int = 13
    ) {
        viewModelScope.launch {
            val character = Character(
                id = id,
                name = name,
                birthDate = birthDate,
                isException = isException,
                activationAge = activationAge
            )
            if (id == 0) {
                characterDao.insertCharacter(character)
            } else {
                characterDao.updateCharacter(character)
            }
        }
    }

    fun deleteCharacter(character: Character) {
        viewModelScope.launch {
            characterDao.deleteCharacter(character)
        }
    }
}
