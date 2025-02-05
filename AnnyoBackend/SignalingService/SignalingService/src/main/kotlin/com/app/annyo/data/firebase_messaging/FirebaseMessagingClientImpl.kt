package com.app.annyo.data.firebase_messaging

import com.app.annyo.data.utils.MessageDataType
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message


/// Fixme create a new Notificaiton Service to send message, as of now its just temporary

class FirebaseMessagingClientImpl(
    private val firebaseMessaging: FirebaseMessaging,
) : FirebaseMessagingClient {

    override suspend fun sendDataMessage(
        registrationToken: String,
        data: MessageDataType,
    ):GetBackBasic{
        try {
            when (data) {
                is MessageDataType.Call -> {
                    firebaseMessaging.send(buildMessage(registrationToken, data))
                }

                is MessageDataType.HangUp -> {
                    firebaseMessaging.send(buildMessage(registrationToken, data))
                }

                is MessageDataType.CallFailed -> { /// Call failed is for server reasons or like if unable to send notificaition to the callee
                    firebaseMessaging.send(buildMessage(registrationToken, data))
                }
            }
        }catch (e: FirebaseMessagingException){
            e.printStackTrace()
            return GetBack.Error()
        }
        return GetBack.Success()
    }

    private fun buildMessage(
        registrationToken: String,
        data: MessageDataType,
    ): Message {
        Message.builder()
            .setAndroidConfig(            // You can also specify for IOS using setAPNsConfig()
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setTtl(2419200) /// lifespan of the message (28 days)
                    .build()
            )
            .setToken(registrationToken)
            .also {
                return when (data) {
                    is MessageDataType.Call -> {
                        it.putData(MessageDataType.TYPE_CALL, data.callInfo)
                            .build()
                    }

                    is MessageDataType.CallFailed -> {
                        it.putData(MessageDataType.TYPE_CALL_FAILED, data.callSessionId)
                            .build()
                    }

                    is MessageDataType.HangUp -> {
                        it.putData(MessageDataType.TYPE_HANGUP, data.callSessionId)
                            .build()
                    }
                }
            }
    }
}