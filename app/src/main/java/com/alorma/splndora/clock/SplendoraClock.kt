package com.alorma.splndora.clock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

interface SplendoraClock {
    val currentDate: StateFlow<LocalDate>
    fun setDate(date: LocalDate)
}

class HistoricalClock(initialDate: LocalDate = LocalDate.of(1729, 12, 31)) : SplendoraClock {
    private val _currentDate = MutableStateFlow(initialDate)
    override val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    override fun setDate(date: LocalDate) {
        _currentDate.value = date
    }
}
