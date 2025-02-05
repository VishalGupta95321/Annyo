package com.example.auth_data.repository

import com.example.auth_domain.repository.GoogleSignInRepository
import com.example.utils.ErrorMessages
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GoogleSignInRepositoryImpl @Inject constructor(
    private val auth:FirebaseAuth
): GoogleSignInRepository {

    override suspend fun signInUsingFirebase(credential: AuthCredential):GetBackBasic{
        return try {
            auth.signInWithCredential(credential)
                .await()
            GetBack.Success()

        }catch (e:FirebaseAuthInvalidUserException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.INVALID_USER)
        }catch (e:FirebaseAuthInvalidCredentialsException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.INVALID_CREDENTIALS)
        }catch (e:IOException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.SERVER_NOT_REACHED)
        }catch (e:FirebaseAuthUserCollisionException){
            e.printStackTrace()
            GetBack.Error(ErrorMessages.ACCOUNT_ALREADY_EXIST)
        }
    }
}