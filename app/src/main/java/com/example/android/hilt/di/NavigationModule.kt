package com.example.android.hilt.di

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    /**
     * AppNavigator. It's an abstract function that returns the interface we're informing Hilt about
     * and the parameter is the implementation of that interface (i.e. AppNavigatorImpl).
     *
     * We now tell Hilt how to provide instances of AppNavigatorImpl. Since this class can be
     * constructor injected, we can just annotate its constructor with @Inject.
     */
    @Binds
    abstract fun bindNavigation(impl: AppNavigatorImpl) : AppNavigator
}