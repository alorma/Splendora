package com.alorma.splndora.engine

import com.alorma.splndora.clock.SplendoraClock
import com.alorma.splndora.data.Character
import java.time.LocalDate
import java.time.Period

class WizardActivationEngine(private val clock: SplendoraClock) {
    companion object {
        const val ACTIVATION_AGE = 13
    }

    /**
     * Calculates the age of a character relative to a reference date.
     * Defaults to the clock's current date.
     */
    fun calculateAge(birthDate: LocalDate, referenceDate: LocalDate = clock.currentDate.value): Int {
        if (birthDate.isAfter(referenceDate)) return -1
        return Period.between(birthDate, referenceDate).years
    }

    /**
     * Determines if a character is "activated" at a reference date.
     * Defaults to the clock's current date.
     */
    fun isActivated(character: Character, referenceDate: LocalDate = clock.currentDate.value): Boolean {
        if (character.birthDate.isAfter(referenceDate)) return false

        return if (character.isException) {
            character.activationDate?.let { !referenceDate.isBefore(it) } ?: false
        } else {
            val age = calculateAge(character.birthDate, referenceDate)
            age >= ACTIVATION_AGE
        }
    }
}
