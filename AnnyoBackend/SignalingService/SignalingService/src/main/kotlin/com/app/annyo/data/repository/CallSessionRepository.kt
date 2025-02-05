package com.app.annyo.data.repository

import com.app.annyo.data.entity.CallSession
import com.app.annyo.data.model.request.UpdateCallSessionRequest
import com.example.utils.GetBack
import com.example.utils.GetBackBasic

interface CallSessionRepository {
    suspend fun createCallSession(
        callSession: CallSession
    ):GetBackBasic

    suspend fun deleteCallSession(callSessionId: String): GetBackBasic /// what if callSession not get deleted

    suspend fun updateCallSession(
        callSessionId: String,
        request: UpdateCallSessionRequest
    ):GetBackBasic

    suspend fun getActiveCallSession(
        callSessionId: String
    ):GetBack<CallSession>

    suspend fun getDisconnectedCallSession(
        callSessionId: String
    ):GetBack<CallSession>

    suspend fun checkIfGlareInCallSessions(
        callSession: CallSession
    ):Boolean
}