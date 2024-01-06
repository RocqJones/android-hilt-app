package com.example.android.hilt

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import com.example.android.hilt.util.DateFormatter

class ServiceLocator(applicationContext: Context) {
    /**
     * The ServiceLocator creates and stores dependencies that are obtained on demand by the classes
     * that need them. You can think of it as a container of dependencies that is attached to the
     * app's lifecycle, which means it will be destroyed when the app process is destroyed.
     *
     * A container is a class which is in charge of providing dependencies in your codebase and knows
     * how to create instances of other types in your app. It manages the graph of dependencies
     * required to provide those instances by creating them and managing their lifecycle.
     */

    private val logsDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "logging.db"
    ).build()

    val loggerLocalDataSource = LoggerLocalDataSource(logsDatabase.logDao())

    fun provideDateFormatter() = DateFormatter()

    fun provideNavigator(activity: FragmentActivity): AppNavigator {
        return AppNavigatorImpl(activity)
    }
}
