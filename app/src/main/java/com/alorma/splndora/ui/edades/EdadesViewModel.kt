package com.alorma.splndora.ui.edades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.splndora.data.Character
import com.alorma.splndora.data.CharacterDao
import com.alorma.splndora.engine.WizardActivationEngine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class CharacterUiModel(
    val character: Character,
    val age: Int,
    val isActivated: Boolean
)

class EdadesViewModel(
    private val characterDao: CharacterDao,
    private val engine: WizardActivationEngine
) : ViewModel() {

    val characters: StateFlow<List<CharacterUiModel>> = characterDao.getAllCharacters()
        .map { list ->
            list.map { character ->
                CharacterUiModel(
                    character = character,
                    age = engine.calculateAge(character.birthDate),
                    isActivated = engine.isActivated(character)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveCharacter(
        id: Int = 0,
        name: String,
        birthDate: LocalDate,
        isException: Boolean
    ) {
        viewModelScope.launch {
            val character = Character(
                id = id,
                name = name,
                birthDate = birthDate,
                isException = isException
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
