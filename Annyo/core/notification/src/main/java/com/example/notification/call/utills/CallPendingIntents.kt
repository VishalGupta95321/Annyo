package com.example.notification.call.utills

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.notification.call.utills.broadcast.CallBroadcastActions

private const val ANSWER_PENDING_INTENT_REQUEST_CODE = 0
private const val DECLINE_PENDING_INTENT_REQUEST_CODE = 1
private const val HANGUP_PENDING_INTENT_REQUEST_CODE = 3
private const val CALLBACK_PENDING_INTENT_REQUEST_CODE = 4
private const val MESSAGE_PENDING_INTENT_REQUEST_CODE = 5

fun Context.createAnswerPendingIntent(): PendingIntent {
    return PendingIntent.getBroadcast(
        this,
        ANSWER_PENDING_INTENT_REQUEST_CODE,
        Intent(CallBroadcastActions.ACTION_CALL_ANSWER.action),
        PendingIntent.FLAG_IMMUTABLE
    )
}

fun Context.createDeclinePendingIntent(): PendingIntent {
    return PendingIntent.getBroadcast(
        this,
        DECLINE_PENDING_INTENT_REQUEST_CODE,
        Intent(CallBroadcastActions.ACTION_CALL_DECLINE.action),
        PendingIntent.FLAG_IMMUTABLE
    )
}
fun Context.createHangUpPendingIntent(): PendingIntent {
    return PendingIntent.getBroadcast(
        this,
        HANGUP_PENDING_INTENT_REQUEST_CODE,
        Intent(CallBroadcastActions.ACTION_CALL_HANGUP.action),
        PendingIntent.FLAG_IMMUTABLE
    )
}
fun Context.createCallbackPendingIntent(): PendingIntent {
    return PendingIntent.getBroadcast(
        this,
        CALLBACK_PENDING_INTENT_REQUEST_CODE,
        Intent(CallBroadcastActions.ACTION_CALLBACK.action),
        PendingIntent.FLAG_IMMUTABLE
    )
}
fun Context.createMessagePendingIntent(): PendingIntent {
    return PendingIntent.getBroadcast(
        this,
        MESSAGE_PENDING_INTENT_REQUEST_CODE,
        Intent(CallBroadcastActions.ACTION_MESSAGE.action),
        PendingIntent.FLAG_IMMUTABLE
    )
}

