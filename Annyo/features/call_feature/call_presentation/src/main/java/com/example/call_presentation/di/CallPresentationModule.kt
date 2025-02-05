package com.example.call_presentation.di

import com.example.call_domain.util.CallHandlerService
import com.example.call_presentation.util.CallHandlerServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CallPresentationModule {
    @Binds
    @Singleton
    fun bindCallHandlerService(service: CallHandlerServiceImpl): CallHandlerService
} /// todo change it it with provides if wont work

//@Module
//@InstallIn(SingletonComponent::class)
//object CallPresentationModule {
//
//    @Provides
//    @Singleton
//    fun provideSomeDependency(
//        rtcUseCases: RtcUseCases,
//        @SupervisorMainImmediateScope
//        scope: CoroutineScope
//    ): CallHandlerService {
//        return CallHandlerServiceImpl()
//    }
//}