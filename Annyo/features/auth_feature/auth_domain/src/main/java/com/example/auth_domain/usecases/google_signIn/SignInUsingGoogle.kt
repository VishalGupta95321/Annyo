package com.example.auth_domain.usecases.google_signIn

import com.example.auth_domain.repository.GoogleSignInRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUsingGoogle @Inject constructor(
    private val repository: GoogleSignInRepository
) {
    suspend operator fun invoke(credential: AuthCredential):GetBackBasic{
        return repository.signInUsingFirebase(credential)
    }
}