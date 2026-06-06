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
     * Calculates the age of a character relative to the current date from the clock.
     */
    fun calculateAge(birthDate: LocalDate, referenceDate: LocalDate = clock.currentDate()): Int {
        if (birthDate.isAfter(referenceDate)) return -1
        return Period.between(birthDate, referenceDate).years
    }

    /**
     * Determines if a character is "activated".
     * Standard: 13 years old or more.
     * Exception: Logic override (e.g., activates at 10 years old).
     */
    fun isActivated(character: Character, referenceDate: LocalDate = clock.currentDate()): Boolean {
        val age = calculateAge(character.birthDate, referenceDate)
        if (age < 0) return false

        return if (character.isException) {
            // Logic override for the exception character: activates at 10 instead of 13
            age >= 10
        } else {
            age >= ACTIVATION_AGE
        }
    }
}
