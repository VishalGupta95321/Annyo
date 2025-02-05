package com.example.auth_data.repository

import com.example.auth_domain.repository.PhoneSignInRepository
import com.example.utils.ErrorMessages
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class PhoneSIgnInRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : PhoneSignInRepository {

    override suspend  fun signInUsingFirebase(credential: PhoneAuthCredential) : GetBackBasic {
        return try {

            auth.signInWithCredential(credential)
                .await()

            GetBack.Success()

        }catch (e: FirebaseAuthInvalidUserException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.INVALID_USER)
        }catch (e: FirebaseAuthInvalidCredentialsException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.INVALID_CREDENTIALS)
        }catch (e: IOException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.SERVER_NOT_REACHED)
        }
    }
}