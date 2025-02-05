package com.app.auth_presentation.register_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.auth_presentation.register_screen.utils.RegisterScreenEvents
import com.example.auth_domain.usecases.google_signIn.GoogleSignInUseCases
import com.example.utils.GetBack
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCases
) : ViewModel() {

     var  registerLoginState by mutableStateOf<LoginState>(
         LoginState.None
     )
         private set


    fun onEvent(event: RegisterScreenEvents) {
        when (event) {
            is RegisterScreenEvents.LoginUsingGoogle -> {
                viewModelScope.launch {
                    logInUsingGoogle(event.credential)
                }
            }
        }
    }


    private fun logInUsingGoogle(
        credential: AuthCredential
    ) {
        viewModelScope.launch {
            registerLoginState = LoginState.Loading
            registerLoginState = when(
                val result = googleSignInUseCase
                    .signInUsingGoogle(credential)
            ){
                is GetBack.Error -> {
                    LoginState.Error(result.message.toString())
                }

                is GetBack.Success -> {
                    LoginState.Success
                }
            }
        }
    }
}

