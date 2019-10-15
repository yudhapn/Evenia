package com.pertamina.spbucare.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class EveniaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EveniaApplication)
            modules(appComponent)
        }
    }
}