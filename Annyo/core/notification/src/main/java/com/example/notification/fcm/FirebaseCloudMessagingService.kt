package com.example.notification.fcm

import android.content.ComponentName
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
        println("message priority = ${message.priority==RemoteMessage.PRIORITY_NORMAL}")
        println("got message ${message.data}")
        println("message priority = ${message.priority==RemoteMessage.PRIORITY_HIGH}")
        if (message.priority == RemoteMessage.PRIORITY_HIGH){
            ContextCompat.startForegroundService(
                this ,Intent().apply {
                    putExtra("CALL_TYPE","OUTGOING_CALL")
                    putExtra("CALL_MODE","VIDEO_CALL")
                    component = ComponentName(packageName,"com.example.call_presentation.util.CallHandlerServiceImpl")
                }
            )
        }

//        println("from fcm receiver")
//        // Fixme:  just temporary  , make sure to update this by checking the message types and also make a new microservice to send a notificaiton.
//        if (message.data.isNotEmpty()){
//            val intent = Intent(FcmBroadcastActions.ACTION_INCOMING_CALL).apply {
//                putExtra(message.data.keys.first(),message.data.values.first())
//            }
//            sendBroadcast(intent)
//        }
    }

    override fun onNewToken(token: String) {
        println("token = $token")
        /// todo update the token to the server that is all
        // todo also check the other overridable functions like on delete etc.
    }
}
