package com.app.auth_presentation.otp_login.otp_login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.auth_presentation.otp_login.otp_login_screen.states.OtpLoginState
import com.app.auth_presentation.otp_login.otp_login_screen.states.PhoneNumberState
import com.app.auth_presentation.otp_login.otp_login_screen.states.SmsCodeState
import com.app.auth_presentation.otp_login.otp_login_screen.states.SmsCodeState.SmsCode.Companion.updateSmsCode
import com.app.auth_presentation.otp_login.otp_login_screen.utils.OtpLoginScreenEvents
import com.example.auth_domain.usecases.phone_signIn.PhoneSignInUseCases
import com.example.utils.ErrorMessages
import com.example.utils.GetBack
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpLoginViewModel @Inject constructor(
    private val phoneSignInUseCase: PhoneSignInUseCases
) : ViewModel() {

    private var verificationId: String = ""

    val forceResendingToken: ForceResendingToken? = null

    var loginState by mutableStateOf<OtpLoginState>(
        OtpLoginState.None
    )
        private set

    var smsCodeState by mutableStateOf(SmsCodeState.SmsCode())
        private set

    var isResendEnabled by mutableStateOf(
        SmsCodeState.ResendButton.DISABLE
    )
        private set

    var phoneNumberState by mutableStateOf(PhoneNumberState())
        private set


    val verificationStateChangedCallbacks: OnVerificationStateChangedCallbacks =
        // todo see if you can remove callback from here to a individual class
        object : OnVerificationStateChangedCallbacks() {

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                isResendEnabled = SmsCodeState.ResendButton.ENABLE
            }

            override fun onCodeSent(
                verification_Id: String,
                forceResendingToken: ForceResendingToken
            ) {
                verificationId = verification_Id
                isResendEnabled = SmsCodeState.ResendButton.DISABLE
                loginState = OtpLoginState.CodeSent
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                loginState = OtpLoginState.CodeRetrieved

                phoneAuthCredential.smsCode?.let { smsCode ->
                    smsCode.forEachIndexed { index, value ->
                        smsCodeState = updateSmsCode(
                            smsCodeState, index, value.toString()
                        )
                    }
                    viewModelScope.launch { delay(500L) }
                    signInUsingPhone(phoneAuthCredential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                loginState = when (e) {
                    is FirebaseTooManyRequestsException -> {
                        OtpLoginState.Error(ErrorMessages.TRY_AGAIN_LATER)
                    }
                    else -> OtpLoginState.Error(ErrorMessages.SOMETHING_WENT_WRONG)
                }
                println(e.localizedMessage)
            }
        }

    fun onEvent(event: OtpLoginScreenEvents) {       /// todo this is against SRP instead create individual function for it
        when (event) {
            is OtpLoginScreenEvents.ResendIOtp -> {
                if (forceResendingToken != null) {
                    loginState = OtpLoginState.Loading
                    PhoneAuthProvider.verifyPhoneNumber(event.options)
                }
            }

            is OtpLoginScreenEvents.SendOtp -> {
                loginState = OtpLoginState.Loading
                PhoneAuthProvider.verifyPhoneNumber(event.options)
            }

            is OtpLoginScreenEvents.VerifyOtp -> {
                if (verificationId.isNotBlank())
                    signInUsingPhone(
                        PhoneAuthProvider.getCredential(
                            verificationId, smsCodeState.toString()
                        )
                    )
            }

            is OtpLoginScreenEvents.OnVerificationCodeEntered -> {
                println(event.index)
                smsCodeState = updateSmsCode(
                    smsCodeState, event.index, event.value
                )
            }

            is OtpLoginScreenEvents.OnCountryCodeEntered -> {
                phoneNumberState = phoneNumberState.copy(
                    countryCode = event.value
                )
            }

            is OtpLoginScreenEvents.OnPhoneNumberEntered -> {
                phoneNumberState = phoneNumberState.copy(
                    number = event.value
                )
            }
        }
    }

    private fun signInUsingPhone(
        credential: PhoneAuthCredential
    ) {
        viewModelScope.launch {
            loginState = OtpLoginState.Loading
            loginState = when (
                val result = phoneSignInUseCase
                    .signInUsingPhone(credential)
            ) {
                is GetBack.Error -> {
                    OtpLoginState.Error(
                        result.message.toString()
                    )
                }
                is GetBack.Success -> {
                    OtpLoginState.Success
                }
            }
        }
    }
}




