package com.app.auth_presentation.otp_login.otp_login_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.annyo.firebase_service.util.OtpGenerationType
import com.annyo.firebase_service.util.getPhoneAuthOptions
import com.app.auth_presentation.otp_login.country_code_screen.CountryCodesScreen
import com.app.auth_presentation.otp_login.country_code_screen.utils.CountryCode
import com.app.auth_presentation.otp_login.otp_login_screen.animations.ContentSlideAnimation
import com.app.auth_presentation.otp_login.otp_login_screen.animations.animateOtpTextField
import com.app.auth_presentation.otp_login.otp_login_screen.states.OtpLoginState
import com.app.auth_presentation.otp_login.otp_login_screen.states.SmsCodeState
import com.app.auth_presentation.otp_login.otp_login_screen.utils.OtpLoginScreenEvents

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun OtpLogInScreen(
    viewModel: OtpLoginViewModel = hiltViewModel(),
    navigateToOnboarding: () ->  Unit,
    navigateUp: () -> Unit
) {

    val loginState = viewModel.loginState
    val smsCode = viewModel.smsCodeState
    val phoneNumber = viewModel.phoneNumberState
    val activity = LocalContext.current as Activity
    val isResendEnabled = viewModel.isResendEnabled
    var isCountryCodeScreenVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = loginState, block = {
        when(loginState){
            is OtpLoginState.CodeRetrieved -> println("code retrieved")
            is OtpLoginState.CodeSent -> println("code sent")
            is OtpLoginState.Error -> println(loginState.message)
            is OtpLoginState.Loading ->println("loading")
            is OtpLoginState.None -> println("none")
            is OtpLoginState.Success -> { navigateToOnboarding()
                println("success") }
        }
    }) // todo don't know why i require launched state here

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
        ) {
            /** Label1  */
            ContentSlideAnimation(
                startAnimation = loginState == OtpLoginState.CodeSent,
                duration = 300,
                content1 = {
                    /**  For Sending Otp  Ex-> Enter Phone Number  */
                    /**  For Sending Otp  Ex-> Enter Phone Number  */
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 30.dp, bottom = 2.dp),
                        text = "Whats Your Phone Number ?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                content2 = {
                    /**  Ex-> For Receiving  Otp  Ex-> Enter Verification Code */
                    /**  Ex-> For Receiving  Otp  Ex-> Enter Verification Code */
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 30.dp, bottom = 2.dp),
                        text = "Enter your verification code",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
            /** Label2 */
            ContentSlideAnimation(
                startAnimation = loginState == OtpLoginState.CodeSent,
                duration = 600,
                content1 = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 50.dp),
                        text = "We will send you an Otp to verify your phone number.Message and Data charges may be applied.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                content2 = {
                    Row {
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .padding(bottom = 66.dp),
                            text = "sent to " + phoneNumber.number+" ● ",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 66.dp),
                            text = "Edit number",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyMedium,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                }
            )
            /** TextField */
            ContentSlideAnimation(
                startAnimation = loginState == OtpLoginState.CodeSent,
                duration = 900,
                content1 = {    /** Enter Phone Number */
                    /** Enter Phone Number */
                    PhoneNumberTextField(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 30.dp),
                        text = phoneNumber.number,
                        countryCode = phoneNumber.countryCode,
                        placeHolder = phoneNumber.placeHolder,
                        onCountryCodeClick = {
                            isCountryCodeScreenVisible = !isCountryCodeScreenVisible
                        }
                    ) {
                        viewModel.onEvent(
                            OtpLoginScreenEvents.OnPhoneNumberEntered(it)
                        )
                    }
                },
                content2 = {   /** Enter Verification code*/
                    /** Enter Verification code*/
                    OtpTextField(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 30.dp),
                        smsCode = smsCode,
                        loginState = loginState,
                        isResendEnabled = isResendEnabled== SmsCodeState.ResendButton.ENABLE,
                        onResendClick = {
                            val options = activity.getPhoneAuthOptions(
                                otpGenerationType = OtpGenerationType.GenerateNewOtp,
                                forceResendingToken = viewModel.forceResendingToken,
                                phoneNumber = phoneNumber.toString(),
                                callback = viewModel.verificationStateChangedCallbacks
                            )
                            if (options != null)
                                viewModel.onEvent(
                                    OtpLoginScreenEvents.SendOtp(
                                        options
                                    )
                                )
                        }
                    ) { index, value ->
                        viewModel.onEvent(
                            OtpLoginScreenEvents.OnVerificationCodeEntered(
                                index, value
                            )
                        )
                    }
                }
            )
            /** Button */
            ContentSlideAnimation(
                startAnimation = loginState == OtpLoginState.CodeSent,
                duration = 1000,
                modifier = Modifier.align(CenterHorizontally),
                content1 = { /** Send Verification code*/
                    /** Send Verification code*/
                    ButtonOtpScreen(
                        text = "Continue",
                    ) {
                        val options = activity.getPhoneAuthOptions(
                            otpGenerationType = OtpGenerationType.GenerateNewOtp,
                            phoneNumber = phoneNumber.toString(),
                            callback = viewModel.verificationStateChangedCallbacks
                        )
                        if (options != null)
                            viewModel.onEvent(
                                OtpLoginScreenEvents.SendOtp(
                                    options
                                )
                            )
                    }
                },
                content2 = {
                    /** verify Verification code*/
                    /** verify Verification code*/
                    ButtonOtpScreen(
                        text = "Verify",
                    ) {
                        viewModel.onEvent(OtpLoginScreenEvents.VerifyOtp)
                    }
                }
            )
        }

        AnimatedVisibility(
            enter = scaleIn(tween(300)) + fadeIn(),
            exit = scaleOut(tween(300)) + fadeOut(),
            visible = isCountryCodeScreenVisible
        ) {
            CountryCodesScreen {
                isCountryCodeScreenVisible = !isCountryCodeScreenVisible
                viewModel.onEvent(
                    OtpLoginScreenEvents.OnCountryCodeEntered(it)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    countryCode: CountryCode,
    placeHolder: String,
    onCountryCodeClick: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Box(
            Modifier
                .padding(end = 5.dp)
                .weight(4f)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp),
                )
                .border(
                    width = 3.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primary
                ),
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.5.dp)
                    .clickable(
                        enabled = enabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onCountryCodeClick() }
                    ),
                style = MaterialTheme.typography.headlineMedium,
                text = countryCode.countryFlag +" "+ countryCode.countryCode,
                textAlign = TextAlign.Center
            )

        }

        TextField(
            modifier = Modifier
                .weight(6f)
                .padding(start = 5.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.secondary,
            ),
            singleLine = true,
            enabled = enabled,
            value = text,
            placeholder = { Text(placeHolder) },
            onValueChange = onValueChanged
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    loginState: OtpLoginState,
    isResendEnabled: Boolean = false,
    smsCode: SmsCodeState.SmsCode,
    onResendClick: () -> Unit,
    onValueChanged: (Int, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(6) { index ->

                val code = when(index){
                    0 -> smsCode.value0
                    1 -> smsCode.value1
                    2 -> smsCode.value2
                    3 -> smsCode.value3
                    4 -> smsCode.value4
                    5 -> smsCode.value5
                    else -> ""
                }

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .animateOtpTextField(
                            startAnimation = loginState == OtpLoginState.CodeRetrieved,
                            textFieldCount = index
                        )
                        .padding(horizontal = 5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    value = code,
                    onValueChange = { value ->
                        onValueChanged(index, value)
                    }
                )
            }
        }
        Row(
            Modifier
                .align(Alignment.End)
                .padding(5.dp)
        ) {
            Text(
                text = "00.00"// resendTimer.toString()
            )
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = isResendEnabled
                    ) {
                        onResendClick()
                    },

                text = " ● Resend",
                color = if (isResendEnabled)
                    MaterialTheme.colorScheme.secondary
                else
                    Color.Gray
                ,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun ButtonOtpScreen(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = Modifier
            .clip(RoundedCornerShape(35.dp))
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}


/*
* todo -> focus in text fields, list of country codes, timer, loading screen, // navigation_done
* */
// todo refactor the whole module use flows,replace with standard textFields,buttons etc.