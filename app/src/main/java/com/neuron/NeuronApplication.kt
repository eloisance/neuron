package com.neuron

import android.app.Application
import com.neuron.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NeuronApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.WARNING)
            androidContext(androidContext = this@NeuronApplication)
            modules(modules = appModule)
        }
    }
}
