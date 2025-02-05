package com.app.annyo.models.responce

import kotlinx.serialization.Serializable

@Serializable    /// still working though
data class CallInfo(
    val callSessionId: String,
    val callerId: String,
    val calleeId: String
)


