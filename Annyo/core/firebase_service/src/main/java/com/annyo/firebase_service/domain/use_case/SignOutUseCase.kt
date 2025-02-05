package com.annyo.firebase_service.domain.use_case

import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val firebase : Firebase
) {
    operator fun invoke():GetBackBasic{
        firebase.auth.signOut()
        return GetBack.Success()
    }
}