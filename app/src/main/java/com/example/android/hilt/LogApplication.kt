package com.example.android.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * @HiltAndroidApp triggers Hilt's code generation, including a base class for your application that
 * can use dependency injection.
 */
@HiltAndroidApp
class LogApplication : Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
    }
}
