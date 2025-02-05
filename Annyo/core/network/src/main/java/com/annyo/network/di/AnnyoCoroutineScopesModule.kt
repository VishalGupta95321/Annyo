package com.annyo.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnnyoCoroutineScopesModule {

    @Singleton
    @Provides
    @SupervisorDefaultScope
    fun providesSupervisorDefaultCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Singleton
    @Provides
    @SupervisorIoScope
    fun providesSupervisorIoScopeCoroutineScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    @Singleton
    @Provides
    @SupervisorMainImmediateScope
    fun providesSupervisorMainImmediateScopeCoroutineScope(
        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainImmediateDispatcher)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupervisorDefaultScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupervisorIoScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupervisorMainImmediateScope