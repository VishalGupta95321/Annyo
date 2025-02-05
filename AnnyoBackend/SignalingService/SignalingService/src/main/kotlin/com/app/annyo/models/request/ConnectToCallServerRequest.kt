package com.app.annyo.models.request

import kotlinx.serialization.Serializable

@Serializable
data class ConnectToCallServerRequest(
    val sessionId: String
)