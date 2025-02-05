package com.app.annyo.data.model.request

sealed interface UpdateUserRequest {
    data class UpdateUserCurrentSessionId(
        val uid: String,
        val currentCallSessionId: String
    ):UpdateUserRequest
}