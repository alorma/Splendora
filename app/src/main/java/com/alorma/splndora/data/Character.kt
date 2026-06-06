package com.alorma.splndora.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthDate: LocalDate,
    val isException: Boolean = false,
    val activationAge: Int = 13
)
