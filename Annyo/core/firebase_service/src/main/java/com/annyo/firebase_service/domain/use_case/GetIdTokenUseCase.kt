package com.annyo.firebase_service.domain.use_case

import com.example.utils.GetBack
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetIdTokenUseCase @Inject constructor(      ///// todo check if we can improve all of the firebase useCases
    private val firebase : Firebase
) {
    suspend operator fun invoke(): GetBack<String> {
        firebase.auth.currentUser?.let {
            val idToken  = it.getIdToken(false)   // todo check what is force refresh
                .await()
            return GetBack.Success(idToken.token)
        }?: return GetBack.Error()
    }

}