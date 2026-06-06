package com.alorma.splndora.di

import androidx.room.Room
import com.alorma.splndora.data.AppDatabase
import com.alorma.splndora.engine.WizardActivationEngine
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "splndora_db"
        ).build()
    }

    single { get<AppDatabase>().characterDao() }

    single { WizardActivationEngine() }
}
