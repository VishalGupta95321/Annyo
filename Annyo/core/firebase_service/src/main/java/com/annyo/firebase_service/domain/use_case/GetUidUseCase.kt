package com.annyo.firebase_service.domain.use_case

import com.example.utils.GetBack
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GetUidUseCase @Inject constructor(
    private val firebase : Firebase
) {
    operator fun invoke():String?{
//       firebase.auth.currentUser?.let {       /// handle errors
//           return GetBack.Success(it.uid)
//       }?: return GetBack.Error()
        return firebase.auth.currentUser?.uid
    }
}