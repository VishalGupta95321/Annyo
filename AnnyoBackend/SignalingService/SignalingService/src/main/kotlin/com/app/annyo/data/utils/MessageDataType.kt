package com.app.annyo.data.utils

import com.app.annyo.models.responce.CallInfo

sealed class MessageDataType(
    val type : String
){
    data class Call(
        val callInfo: String,
    ): MessageDataType(TYPE_CALL)

    data class HangUp(
        //val callInfo: String,
        val callSessionId: String
    ): MessageDataType(TYPE_HANGUP)

    data class CallFailed(
        //val callInfo: String,
        val callSessionId: String
    ): MessageDataType(TYPE_CALL_FAILED)

    companion object{
        const val TYPE_CALL = "Call"
        const val TYPE_HANGUP = "HangUp"
        const val TYPE_CALL_FAILED = "CallFailed"
    }
}

// todo why do i even need to add Call cancelled cuz