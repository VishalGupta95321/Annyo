package com.app.annyo.data.repository

import com.app.annyo.data.entity.CallSession
import com.app.annyo.data.model.request.UpdateCallSessionRequest
import com.app.annyo.data.utils.DatabaseConstants
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull

class CallSessionRepositoryImpl(
    database: MongoDatabase
): CallSessionRepository {

    private val activeCallSessions = database.getCollection<CallSession>(DatabaseConstants.CALL_SESSION_COLLECTION)
    private val disconnectedCallSessions = database.getCollection<CallSession>(DatabaseConstants.DISCONNECTED_CALL_SESSION)

    override suspend fun createCallSession(callSession: CallSession):GetBackBasic {
        return try {
            activeCallSessions.insertOne(callSession).wasAcknowledged().let { wasAcknowledged ->
                if (wasAcknowledged)
                    GetBack.Success()
                else
                    GetBack.Error()
            }
        }catch (e:Exception){
            e.printStackTrace()
            GetBack.Error()
        }
    }

    override suspend fun deleteCallSession(callSessionId: String):GetBackBasic {
        val filter = Filters.eq(CallSession::callSessionId.name, callSessionId)
        return try {
            activeCallSessions.deleteOne(filter).wasAcknowledged().let { wasAcknowledged ->
                if (wasAcknowledged)
                    GetBack.Success()
                else
                    GetBack.Error()
            }
        }catch (e:Exception){
            e.printStackTrace()
            GetBack.Error()
        }
    }

    override suspend fun updateCallSession(
        callSessionId: String, request: UpdateCallSessionRequest
    ): GetBackBasic {

        val filter = Filters.eq(CallSession::callSessionId.name, callSessionId)

        val update = when (request) {
            is UpdateCallSessionRequest.UpdateIsAudioToVideoSwitchRequested ->
                Updates.set(CallSession::isAudioToVideoSwitchRequested.name, true)

            is UpdateCallSessionRequest.UpdateIsCallPickedUp ->
                Updates.set(CallSession::isCallPickedUp.name, true)

            is UpdateCallSessionRequest.UpdateIsConnectionEstablished ->
                Updates.set(CallSession::isConnectionEstablished.name, true)

            is UpdateCallSessionRequest.UpdateIsHangUpRequested ->
                Updates.set(CallSession::isHangUpRequested.name, true)

            is UpdateCallSessionRequest.UpdateDisconnectedAt -> {
                Updates.set(CallSession::disconnectedAt.name,request.date)
            }
        }

        return try {
            activeCallSessions.updateOne(filter, update).wasAcknowledged().let { wasAcknowledged ->
                if (wasAcknowledged)
                    GetBack.Success()
                else
                    GetBack.Error()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GetBack.Error()
        }
    }

    override suspend fun getActiveCallSession(callSessionId: String): GetBack<CallSession> {
        val filter = Filters.eq(CallSession::callSessionId.name,callSessionId)

        return activeCallSessions.find(filter).firstOrNull().let { _callSession->
            if (_callSession==null)
                GetBack.Error()
            else
                GetBack.Success(_callSession)
        }
    }

    override suspend fun getDisconnectedCallSession(callSessionId: String): GetBack<CallSession> {
        val filter = Filters.eq(CallSession::callSessionId.name,callSessionId)

        return disconnectedCallSessions.find(filter).firstOrNull().let { _disconnectedCallSession->
            if (_disconnectedCallSession==null)
                GetBack.Error()
            else
                GetBack.Success(_disconnectedCallSession)
        }
    }

    override suspend fun checkIfGlareInCallSessions(callSession: CallSession): Boolean {
        val filter = Filters.and(
            Filters.eq(CallSession::callerId.name,callSession.calleeId),
            Filters.eq(CallSession::calleeId.name,callSession.callerId)
        )
        return activeCallSessions.find(filter).firstOrNull() != null
    }
}

// no upsert in update call session
// todo() created ttl index or any index as a part of backend deployment