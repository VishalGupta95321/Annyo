package com.app.annyo.data.repository

import com.app.annyo.data.entity.SignallingEvents
import com.app.annyo.data.model.request.ResetSignallingEventsRequests
import com.app.annyo.data.model.request.UpdateSignallingEventsRequest
import com.example.utils.GetBackBasic

interface SignallingEventsRepository {
    suspend fun addSignallingEvents(signallingEvent: SignallingEvents):GetBackBasic
    suspend fun getSignallingEvents(userId: String):GetBackBasic
    suspend fun deleteSignallingEvents(userId: String):GetBackBasic
    suspend fun updateSignallingEvents(request: UpdateSignallingEventsRequest):GetBackBasic
    suspend fun resetSignallingEvents(request: ResetSignallingEventsRequests):GetBackBasic
}