package com.app.auth_presentation.otp_login.otp_login_screen.utils

import com.app.auth_presentation.otp_login.country_code_screen.utils.CountryCode
import com.google.firebase.auth.PhoneAuthOptions

sealed class OtpLoginScreenEvents {

    data class OnVerificationCodeEntered(
       val index : Int,
       val value : String
    ): OtpLoginScreenEvents()

    data class OnCountryCodeEntered(
        val value : CountryCode
    ): OtpLoginScreenEvents()

    data class OnPhoneNumberEntered(
        val value : String
    ): OtpLoginScreenEvents()

    data class SendOtp(
        val options: PhoneAuthOptions
    ): OtpLoginScreenEvents()

    data class ResendIOtp(
        val options: PhoneAuthOptions
    ): OtpLoginScreenEvents()

    object VerifyOtp : OtpLoginScreenEvents()

}