package com.alorma.splndora.ui.simulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.splndora.data.Character
import com.alorma.splndora.data.CharacterDao
import com.alorma.splndora.engine.WizardActivationEngine
import kotlinx.coroutines.flow.*
import java.time.LocalDate

data class SimulatorState(
    val characters: List<Character> = emptyList(),
    val selectedCharacter: Character? = null,
    val targetDate: LocalDate = LocalDate.of(1729, 1, 1),
    val isActivated: Boolean = false,
    val ageOnDate: Int = -1
)

class SimulatorViewModel(
    private val characterDao: CharacterDao,
    private val engine: WizardActivationEngine
) : ViewModel() {

    private val _state = MutableStateFlow(SimulatorState())
    val state: StateFlow<SimulatorState> = _state.asStateFlow()

    init {
        characterDao.getAllCharacters()
            .onEach { characters ->
                _state.update { it.copy(characters = characters) }
            }
            .launchIn(viewModelScope)
    }

    fun onCharacterSelected(character: Character) {
        _state.update { 
            it.copy(
                selectedCharacter = character,
                isActivated = engine.isActivated(character, it.targetDate),
                ageOnDate = engine.calculateAge(character.birthDate, it.targetDate)
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        _state.update { state ->
            state.copy(
                targetDate = date,
                isActivated = state.selectedCharacter?.let { engine.isActivated(it, date) } ?: false,
                ageOnDate = state.selectedCharacter?.let { engine.calculateAge(it.birthDate, date) } ?: -1
            )
        }
    }
}
