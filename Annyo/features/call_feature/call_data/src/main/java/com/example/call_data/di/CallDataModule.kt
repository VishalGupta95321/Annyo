package com.example.call_data.di

import android.app.Application
import com.example.call_data.repository.RtcConnectionRepositoryImpl
import com.example.call_data.repository.RtcSignalingRepositoryImpl
import com.example.call_data.util.RtcConstants.RETRY_INTERVAL
import com.example.call_data.util.RtcMediaCaptureClientImpl
import com.example.call_data.web_socket.SignalingService
import com.example.call_data.web_socket.custom_scarlet_adapters.CustomKotlinSerializationMessageAdapter
import com.example.call_data.web_socket.custom_scarlet_adapters.FlowStreamAdapter
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.repository.RtcSignalingRepository
import com.example.call_domain.util.RtcMediaCaptureClient
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface CallDataModule {

    @Binds
    @Singleton
    fun bindRtcConnectionRepository(repo: RtcConnectionRepositoryImpl): RtcConnectionRepository

    @Binds
    @Singleton
    fun bindRtcSignallingRepository(repo: RtcSignalingRepositoryImpl): RtcSignalingRepository

    @Binds
    @Singleton
    fun bindRtcMediaCaptureClient(client: RtcMediaCaptureClientImpl): RtcMediaCaptureClient

    companion object {
        /**
         * Scarlet websocket
         * */
        @Provides
        @Singleton
        fun provideScarletInstance(
            application: Application,
            okHttpClient: OkHttpClient,
            kotlinSerializer: Json
        ) = Scarlet.Builder()
            .backoffStrategy(LinearBackoffStrategy(RETRY_INTERVAL))
             //.lifecycle(AndroidLifecycle.ofApplicationForeground(application))  /// commented so the connection will be alive while in service when in  thw background
            .webSocketFactory(okHttpClient.newWebSocketFactory("http://192.168.29.109:8085/signalling"))    /// todo Change URL to Yours
            .addMessageAdapterFactory(
                CustomKotlinSerializationMessageAdapter.Factory(
                kotlinSerializer))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .build()


        @Provides
        @Singleton
        fun provideSignalingService(
            scarletInstance: Scarlet
        ) = scarletInstance.create<SignalingService>()


    }
}