package com.example.auth_domain.repository

import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow


interface PhoneSignInRepository{
    suspend fun signInUsingFirebase(
        credential: PhoneAuthCredential
    ): GetBackBasic
}
