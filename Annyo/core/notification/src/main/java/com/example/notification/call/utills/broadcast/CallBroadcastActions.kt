package com.example.notification.call.utills.broadcast

enum class CallBroadcastActions(val action: String) {
    ACTION_CALL_ANSWER("ACTION_CALL_ANSWER"),
    ACTION_CALL_DECLINE("ACTION_CALL_DECLINE"),
    ACTION_CALL_HANGUP("ACTION_CALL_HANGUP"),
    ACTION_CALLBACK("ACTION_CALLBACK"),
    ACTION_MESSAGE("ACTION_MESSAGE")
}
