package com.app.auth_presentation.register_screen

sealed interface LoginState {
    object None: LoginState
    object Loading : LoginState
    object Success : LoginState
    data class Error(
        val message: String? = null
    ) : LoginState
}
