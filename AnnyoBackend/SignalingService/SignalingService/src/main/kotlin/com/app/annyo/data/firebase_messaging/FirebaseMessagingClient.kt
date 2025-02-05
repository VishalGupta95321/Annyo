package com.app.annyo.data.firebase_messaging

import com.app.annyo.data.utils.MessageDataType
import com.example.utils.GetBackBasic

/// Todo create a new Notificaiton micro Service  to send message
interface FirebaseMessagingClient {
    suspend fun sendDataMessage(
        registrationToken:String,
        data: MessageDataType
    ):GetBackBasic
}