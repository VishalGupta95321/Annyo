package com.app.ggg

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class test2  @Inject constructor(): AndroidViewModel(Application()) {
//    val options = FirebaseOptions.Builder()
//        .setApiKey("AIzaSyBoK_aB0mUlJVSFrLJBKQaSAdUhC1ZXXGM")
//        .setApplicationId("1:1059376879610:android:ce3c645a925a311780b643")
//        .setProjectId("annyo-3a3b0")
//        .build()

    fun storage(uri:Uri){
        GlobalScope.launch {
            Firebase.storage.reference.child("\"Blind/Users.jpg\"")
                .putFile(uri) //com.app.auth_presentation/res/drawable/jon_ly_uipjy2xrojq_unsplash"))
                .await()
        }
    }


     fun phoneAuthOptions(activity: Activity): PhoneAuthOptions? {
         return PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber("+919532169104")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    println("hello")
                    viewModelScope.launch {
                    }
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                  //  credential = phoneAuthCredential
                    viewModelScope.launch{
                        println("hello")
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    viewModelScope.launch {
                        println("hello")
                    }
                }
            })
            .build()


    }

    fun auth(acyivity:Activity){
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions(acyivity)!!)
    }
//    init {
//
//    }
}