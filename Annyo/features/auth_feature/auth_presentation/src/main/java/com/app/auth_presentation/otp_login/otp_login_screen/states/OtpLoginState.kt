package com.app.auth_presentation.otp_login.otp_login_screen.states

sealed interface OtpLoginState {
    object None: OtpLoginState
    object Loading : OtpLoginState
    object CodeSent: OtpLoginState
    object CodeRetrieved: OtpLoginState
    object Success : OtpLoginState
    data class Error(
        val message: String? = null
    ) : OtpLoginState
}