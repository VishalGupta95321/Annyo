package com.app.auth_presentation.register_screen.utils

sealed class LayoutIdRegisterScreen(
    val id: String
) {
    object Container : LayoutIdRegisterScreen("box")
    object BackgroundImage : LayoutIdRegisterScreen("background_image")
    object Logo : LayoutIdRegisterScreen("logo")
    object GoogleLoginButton : LayoutIdRegisterScreen("google_login_button")
    object PhoneLoginButton : LayoutIdRegisterScreen("phone_login_button")
}