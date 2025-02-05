package com.example.auth_domain.usecases.phone_signIn

data class PhoneSignInUseCases(
    val validatePhoneNumber : ValidatePhoneNumber,
    val signInUsingPhone : SignInUsingPhone
)