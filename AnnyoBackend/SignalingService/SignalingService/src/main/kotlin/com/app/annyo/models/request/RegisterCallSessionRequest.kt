package com.app.annyo.models.request

import com.app.annyo.data.entity.Callee
import com.app.annyo.data.entity.Caller
import com.app.annyo.data.entity.CallSession
import kotlinx.serialization.Serializable

@Serializable
data class RegisterCallSessionRequest(
    val sessionId:String,
    val callerId:String,
    val calleeId:String
)


fun RegisterCallSessionRequest.toCallSession(): CallSession = CallSession(
    callSessionId = sessionId,
    caller = Caller(callerId),
    callee = Callee(calleeId)
)
