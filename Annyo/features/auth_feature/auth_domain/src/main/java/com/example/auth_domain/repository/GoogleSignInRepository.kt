package com.example.auth_domain.repository

import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface GoogleSignInRepository{
    suspend fun signInUsingFirebase(
        credential: AuthCredential
    ): GetBackBasic
}

