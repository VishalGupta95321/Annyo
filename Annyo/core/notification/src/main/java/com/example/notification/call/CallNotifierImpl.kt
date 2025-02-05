package com.example.notification.call

import android.Manifest.permission
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.drawable.Icon
import android.os.Build
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.annyo.core.notification.R
import com.example.notification.call.model.Individual
import com.example.notification.call.utills.OngoingCallNotificationState
import com.example.notification.call.utills.createAnswerPendingIntent
import com.example.notification.call.utills.createCallbackPendingIntent
import com.example.notification.call.utills.createDeclinePendingIntent
import com.example.notification.call.utills.createHangUpPendingIntent
import com.example.notification.call.utills.createMessagePendingIntent
import com.example.notification.utils.NotificationChannelIds
import com.example.notification.utils.NotificationChannelManager
import com.example.notification.utils.NotificationSoundManager
import com.example.notification.utils.createBaseNotification
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


internal class CallNotifierImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val channelHandler: NotificationChannelManager,
    private val soundManager: NotificationSoundManager,
    private val notificationManagerCompat: NotificationManagerCompat
) : CallNotifier {

    override fun createIncomingCallNotification(
        caller: Individual,
        isVideoCall: Boolean,
    ): Notification {
        with(context) {
            return createCallNotification {
                setIncomingCallNotificationStyle(
                    this@with, caller, createAnswerPendingIntent(), createDeclinePendingIntent()
                )
            }
        }
    }

    override fun createOngoingCallNotification(
        callee: Individual,
        isVideoCall: Boolean,
        state: OngoingCallNotificationState,
    ): Notification {
        with(context) {
            return createCallNotification {
                setOngoingCallNotificationStyle(
                    this@with, callee, state, createHangUpPendingIntent()
                )
            }
        }
    }

    override fun postMissedCallNotification(
        caller: Individual,
        missedCallCount: Int,
        isVideoCall: Boolean,
    ) {
        with(context) {
            if (checkSelfPermission(this, permission.POST_NOTIFICATIONS) != PERMISSION_GRANTED)
                return

            val notification = createMissedCallNotification {
                setMissedCallNotificationStyle(
                    this@with,
                    caller,
                    createCallbackPendingIntent(),
                    createMessagePendingIntent(),
                    missedCallCount,
                    isVideoCall
                )
            }
            //  notification.flags = Notification.FLAG_SHOW_LIGHTS
            notificationManagerCompat.notify(
                MISSED_CALL_NOTIFICATION_ID,
                notification
            )
        }
    }


    private fun createCallNotification(
        block: NotificationCompat.Builder.() -> Unit
    ): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channelHandler.createCallNotificationChannel()

        return context.createBaseNotification(
            channelId = NotificationChannelIds.CallChannel,
            sound = soundManager.getDefaultDeviceCallRingtone(),
            category = NotificationCompat.CATEGORY_CALL,
            priority = NotificationCompat.PRIORITY_MAX,
        ).apply {
            block()
        }.build()
    }

    private fun createMissedCallNotification(
        block: NotificationCompat.Builder.() -> Unit
    ): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channelHandler.createUnCategorizedNotificationChannel()

        return context.createBaseNotification(
            channelId = NotificationChannelIds.UncategorizedChannel,
            sound = soundManager.getDefaultDeviceNotification(),
            category = NotificationCompat.CATEGORY_MISSED_CALL, /// fixme Using NotificationCompat instead of Notification
            priority = NotificationCompat.PRIORITY_MAX,
        ).apply {
            block()
        }.build()
    }

    companion object {
        private const val CALL_NOTIFICATION_ID = 0
        private const val MISSED_CALL_NOTIFICATION_ID = 1
    }
}

fun NotificationCompat.Builder.setIncomingCallNotificationStyle(
    context: Context,
    individual: Individual,
    answerIntent: PendingIntent,
    declineIntent: PendingIntent,
    isVideoCall: Boolean = false
): NotificationCompat.Builder = with(context) {

    val answerAction = Notification.Action.Builder(
        Icon.createWithResource(this, R.drawable.answer_call),
        getString(R.string.call_notification_answer_button_text),
        answerIntent
    ).build()

    val declineAction = Notification.Action.Builder(
        Icon.createWithResource(this, R.drawable.end_call),
        getString(R.string.call_notification_decline_button_text),
        declineIntent
    ).build()

    val incomingCaller = Person.Builder()
        .setName(individual.name)
        .setIcon(individual.pic)
        .setImportant(individual.isImportant)
        .build()

    setStyle(
        NotificationCompat.CallStyle.forIncomingCall(
            incomingCaller, declineIntent, answerIntent
        )
    )

    setSmallIcon(R.drawable.call_received)

    return this@setIncomingCallNotificationStyle
}


fun NotificationCompat.Builder.setOngoingCallNotificationStyle(
    context: Context,
    individual: Individual,
    state: OngoingCallNotificationState,
    hangUpIntent: PendingIntent,
    isVideoCall: Boolean = false
): NotificationCompat.Builder = with(context) {

    val hangUpAction = Notification.Action.Builder(
        Icon.createWithResource(
            this,
            R.drawable.end_call
        ),
        getString(R.string.call_notification_decline_button_text),
        hangUpIntent
    ).build()

    val contentText = when (state) {
        OngoingCallNotificationState.CallOngoing -> {
            if (isVideoCall)
                getString(R.string.call_notification_ongoing_video_call_desc)
            else
                getString(R.string.call_notification_ongoing_voice_call_desc)
        }

        OngoingCallNotificationState.CallReconnecting ->
            getString(R.string.call_notification_call_reconnecting_desc)

        OngoingCallNotificationState.CallBusy ->
            getString(R.string.call_notification_call_busy_desc)

        OngoingCallNotificationState.CallConnecting ->
            getString(R.string.call_notification_call_connecting_desc)

        OngoingCallNotificationState.CallRinging ->
            getString(R.string.call_notification_call_ringing_desc)
    }

    val person = Person.Builder()
        .setName(individual.name)
        .setIcon(individual.pic)
        .setImportant(individual.isImportant)
        .build()

    setStyle(
        NotificationCompat.CallStyle.forOngoingCall(
            person, hangUpIntent
        )
    )

    //setOngoing() // todo check purpose and  use
    // todo also add timer in the notification later
    setSmallIcon(R.drawable.call_made)

    return this@setOngoingCallNotificationStyle
}


fun NotificationCompat.Builder.setMissedCallNotificationStyle(
    context: Context,
    individual: Individual,
    callBackIntent: PendingIntent,
    messageIntent: PendingIntent,
    missedCallCount: Int,
    isVideoCall: Boolean
): NotificationCompat.Builder = with(context) {
    val callBackAction = NotificationCompat.Action.Builder(
        IconCompat.createWithResource(this, R.drawable.answer_call),
        getString(R.string.call_notification_callback_button_text),
        callBackIntent
    ).build()

    val messageAction = NotificationCompat.Action.Builder(
        IconCompat.createWithResource(this, R.drawable.message),
        getString(R.string.call_notification_message_button_text),
        messageIntent
    ).build()

    addAction(callBackAction)
    addAction(messageAction)
    setContentTitle(individual.name)
    setContentText(
        if (isVideoCall)
            missedCallCount.toString() + getString(R.string.call_notification_missed_video_call_desc)
        else
            missedCallCount.toString() + getString(R.string.call_notification_missed_voice_call_desc)
    )
    setLargeIcon(individual.pic.toIcon(this))
    setSmallIcon(R.drawable.call_missed)

    return this@setMissedCallNotificationStyle
}