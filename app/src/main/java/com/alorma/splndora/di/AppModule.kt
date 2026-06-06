package com.alorma.splndora.di

import androidx.room.Room
import com.alorma.splndora.clock.HistoricalClock
import com.alorma.splndora.clock.SplendoraClock
import com.alorma.splndora.data.AppDatabase
import com.alorma.splndora.engine.WizardActivationEngine
import com.alorma.splndora.ui.edades.EdadesViewModel
import com.alorma.splndora.ui.simulator.SimulatorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.bind

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "splndora_db"
        ).build()
    }

    single { get<AppDatabase>().characterDao() }

    singleOf(::HistoricalClock) bind SplendoraClock::class

    singleOf(::WizardActivationEngine)

    viewModelOf(::EdadesViewModel)
    viewModelOf(::SimulatorViewModel)
}
