package com.alorma.splndora

import android.app.Application
import com.alorma.splndora.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SplendoraApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SplendoraApp)
            modules(appModule)
        }
    }
}
