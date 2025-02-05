package com.annyo.firebase_service.util

import android.app.Activity
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

fun Activity.getPhoneAuthOptions(
    otpGenerationType: OtpGenerationType,
    forceResendingToken: ForceResendingToken? = null,
    phoneNumber:String,
    callback: OnVerificationStateChangedCallbacks
): PhoneAuthOptions? {

    val phoneAuthOptions = PhoneAuthOptions.newBuilder(Firebase.auth)
        .setPhoneNumber(phoneNumber)
        .setTimeout(30L,TimeUnit.SECONDS)
        .setActivity(this)
        .setCallbacks(callback)

    return when(otpGenerationType){
        OtpGenerationType.GenerateNewOtp -> {
          phoneNumber.isNotBlank().let {
                phoneAuthOptions.build()
            }
        }
        OtpGenerationType.ResendOtp -> {
            phoneNumber.isNotBlank().let {
                forceResendingToken?.let {
                    phoneAuthOptions
                        .setForceResendingToken(
                            forceResendingToken
                        )
                        .build()
                }
            }
        }
    }
}

enum class OtpGenerationType {
    GenerateNewOtp,
    ResendOtp
}