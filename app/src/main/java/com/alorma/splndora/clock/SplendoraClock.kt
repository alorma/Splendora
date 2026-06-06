package com.alorma.splndora.clock

import java.time.LocalDate

interface SplendoraClock {
    fun currentDate(): LocalDate
}

class HistoricalClock(private val date: LocalDate = LocalDate.of(1729, 12, 31)) : SplendoraClock {
    override fun currentDate(): LocalDate = date
}
