package com.app.annyo.data.repository

import com.app.annyo.data.entity.SignallingEvents
import com.app.annyo.data.model.request.ResetSignallingEventsRequests
import com.app.annyo.data.model.request.UpdateSignallingEventsRequest
import com.app.annyo.data.utils.DatabaseConstants
import com.app.annyo.data.utils.DatabaseConstants.SIGNALLING_EVENTS_COLLECTION
import com.example.utils.GetBackBasic
import com.mongodb.kotlin.client.coroutine.MongoDatabase

class SignallingEventsRepositoryImpl(
    private val database: MongoDatabase
):SignallingEventsRepository {
    private val signallingEvents = database.getCollection<SignallingEvents>(SIGNALLING_EVENTS_COLLECTION)

    override suspend fun addSignallingEvents(signallingEvent: SignallingEvents): GetBackBasic {
        TODO("Not yet implemented")
    }

    override suspend fun getSignallingEvents(userId: String): GetBackBasic {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSignallingEvents(userId: String): GetBackBasic {
        TODO("Not yet implemented")
    }

    override suspend fun updateSignallingEvents(request: UpdateSignallingEventsRequest): GetBackBasic {
        TODO("Not yet implemented")
    }

    override suspend fun resetSignallingEvents(request: ResetSignallingEventsRequests): GetBackBasic {
        TODO("Not yet implemented")
    }

} // about to finish this but started kafka
// and in the app left updating serializer and all other like making a database to store call history in the core data