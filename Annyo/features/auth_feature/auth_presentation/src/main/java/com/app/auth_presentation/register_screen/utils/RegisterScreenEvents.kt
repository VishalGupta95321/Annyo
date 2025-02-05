package com.app.auth_presentation.register_screen.utils

import com.google.firebase.auth.AuthCredential

sealed class RegisterScreenEvents {

    data class LoginUsingGoogle(
        val  credential: AuthCredential
    ): RegisterScreenEvents()

}