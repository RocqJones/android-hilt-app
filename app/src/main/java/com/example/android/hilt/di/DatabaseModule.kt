package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Module tells Hilt that this is a module and
 * @InstallIn tells Hilt the containers where the bindings are available by specifying a Hilt component.
 *
 * Modules that only contain @Provides functions can be object classes. This way, providers get optimized
 * and almost in-lined in generated code.
 *
 * Providing instances with @Provides
 * - @Provides in Hilt modules to tell Hilt how to provide types that cannot be constructor injected.
 * - @Provides will be executed every time Hilt needs to provide an instance of that type.
 * - @Provides-annotated function tells Hilt the binding type, the type that the function provides
 *   instances of.. The function parameters are the dependencies of that type.
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideLogDao(database: AppDatabase) : LogDao {
        return database.logDao()
    }

    /**
     * Since we have AppDatabase as a transitive dependency, we also need to tell Hilt how to provide
     * instances of that type.
     *
     * Each Hilt container comes with a set of default bindings that can be injected as dependencies
     * into your custom bindings. This is the case with applicationContext. To access it, you need to
     * annotate the field with @ApplicationContext.
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }
}