package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @Binds methods must have the scoping annotations if the type is scoped, so that's why the
 * functions above are annotated with @Singleton and @ActivityScoped. If @Binds or @Provides
 * are used as a binding for a type, the scoping annotations in the type are not used anymore,
 * so you can go ahead and remove them from the different implementation classes.
 *
 * Using qualifiers
 * - A qualifier is an annotation used to identify a binding.
 * - To tell Hilt how to provide different implementations (multiple bindings) of the same type, you
 *   can use qualifiers.
 *
 *   We need to define a qualifier per implementation since each qualifier will be used to identify
 *   a binding. When injecting the type in an Android class or having that type as a dependency of
 *   other classes, the qualifier annotation needs to be used to avoid ambiguity.
 */

@Qualifier
annotation class DatabaseLogger

@Qualifier
annotation class InMemoryLogger

/**
 * Now, these qualifiers must annotate the @Binds (or @Provides in case we need it) functions that
 * provide each implementation. See the full code and notice the qualifiers usage in the @Binds methods.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {

    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource) : LoggerDataSource
}

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingInMemoryModule {

    @InMemoryLogger
    @Singleton
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource) : LoggerDataSource
}