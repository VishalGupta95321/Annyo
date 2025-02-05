package com.example.notification.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.notification.call.CallNotifier
import com.example.notification.call.CallNotifierImpl
import com.example.notification.call.utills.broadcast.CallNotificationEventsBroadcastMonitor
import com.example.notification.call.utills.broadcast.CallNotificationEventsMonitor
import com.example.notification.utils.NotificationChannelManager
import com.example.notification.utils.NotificationChannelManagerImpl
import com.example.notification.utils.NotificationSoundManager
import com.example.notification.utils.NotificationSoundManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CallDataModule {

    @Singleton
    @Binds
    internal abstract fun bindCallNotificationEventsMonitor(
        monitor : CallNotificationEventsBroadcastMonitor
    ): CallNotificationEventsMonitor

    @Singleton
    @Binds
    internal abstract fun bindCallNotifier(notifier : CallNotifierImpl):CallNotifier

    @Singleton
    @Binds
    internal abstract fun bindNotificationChannelManager(
        manager: NotificationChannelManagerImpl
    ):NotificationChannelManager

    @Singleton
    @Binds
    internal abstract fun bindNotificationSoundManager(
        manager: NotificationSoundManagerImpl
    ):NotificationSoundManager

    companion object{
        @Provides
        @Singleton
        fun provideNotificationManager(
            @ApplicationContext context: Context
        ) = NotificationManagerCompat.from(context)  /// todo provide notificationManager not compat one maybe
    }
}
