package com.example.notification.call.utills.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class CallNotificationEventsBroadcastMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : CallNotificationEventsMonitor {

    override suspend fun getIncomingCallNotificationEvents(): IncomingCallNotificationEvents? {

        val intentFilter = IntentFilter().apply {
            addAction(CallBroadcastActions.ACTION_CALL_ANSWER.action)
            addAction(CallBroadcastActions.ACTION_CALL_DECLINE.action)
        }

        val callBroadcastAction = receiveCallBroadcast(context, intentFilter)

        if (callBroadcastAction.action == CallBroadcastActions.ACTION_CALL_ANSWER.action)
            return IncomingCallNotificationEvents.OnAnswer

        if (callBroadcastAction.action == CallBroadcastActions.ACTION_CALL_DECLINE.action)
            return IncomingCallNotificationEvents.OnDecline

        return null
    }

    override suspend fun getOngoingCallNotificationEvents(): OngoingCallNotificationEvents? {

        val callBroadcastAction = receiveCallBroadcast(
            context, IntentFilter(CallBroadcastActions.ACTION_CALL_HANGUP.action)
        )

        if (callBroadcastAction.action == CallBroadcastActions.ACTION_CALL_HANGUP.action)
            return OngoingCallNotificationEvents.OnHangup

        return null
    }

    override suspend fun getMissedCallNotificationEvents(): MissedCallNotificationEvents? {

        val intentFilter = IntentFilter().apply {
            addAction(CallBroadcastActions.ACTION_MESSAGE.action)
            addAction(CallBroadcastActions.ACTION_CALLBACK.action)
        }
        val callBroadcastAction = receiveCallBroadcast(context, intentFilter)

        if (callBroadcastAction.action == CallBroadcastActions.ACTION_MESSAGE.action)
            return MissedCallNotificationEvents.OnMessage

        if (callBroadcastAction.action == CallBroadcastActions.ACTION_CALLBACK.action)
            return MissedCallNotificationEvents.OnCallBack

        return null
    }

    private suspend fun receiveCallBroadcast(
        context: Context,
        intentFilter: IntentFilter
    ): CallBroadcastActions = suspendCancellableCoroutine { continuation ->

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {

                val action = intent?.action ?: return

                if (intentFilter.hasAction(action))
                    CallBroadcastActions.entries.find { it.action == action }?.let {
                        continuation.resume(it)
                        context.unregisterReceiver(this)
                    }
            }
        }
        /// FIXME  if any problem occurs then use context.registerReceiver()
        ContextCompat.registerReceiver(
            context, receiver, intentFilter, ContextCompat.RECEIVER_EXPORTED
        )
    }
}