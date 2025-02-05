package com.app.auth_presentation.otp_login.otp_login_screen.states

import com.app.auth_presentation.otp_login.country_code_screen.utils.CountryCode

data class PhoneNumberState(
    val countryCode : CountryCode = CountryCode.AMERICA,  /// todo set acc. to the user country . Maybe use telephonyManager or that telephony manager alternative forgot the name
    val number : String = "",
    val placeHolder : String = "Phone Number"
){
    override fun toString(): String {
        return countryCode.countryCode+number
    }
}