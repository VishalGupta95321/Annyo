package com.example.auth_domain.usecases.phone_signIn

import com.example.auth_domain.repository.PhoneSignInRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUsingPhone @Inject constructor(
    private val repository: PhoneSignInRepository
) {
    suspend  operator fun invoke(credential: PhoneAuthCredential): GetBackBasic {
        return repository.signInUsingFirebase(credential)
    }
}