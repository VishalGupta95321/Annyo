package com.annyo.firebase_service.di

import com.annyo.firebase_service.domain.use_case.GetIdTokenUseCase
import com.annyo.firebase_service.domain.use_case.GetUidUseCase
import com.annyo.firebase_service.domain.use_case.SignOutUseCase
import com.annyo.firebase_service.domain.use_case.SignedUserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebase(): Firebase
    = Firebase

    @Provides
    @Singleton
    fun provideFirebaseStorage(firebase:Firebase):StorageReference
    = firebase.storage.reference

    @Singleton
    @Provides
    fun provideFirebaseAuth(firebase: Firebase): FirebaseAuth
    = firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseMessaging(firebase: Firebase): FirebaseMessaging
    = firebase.messaging

    @Singleton
    @Provides
    fun provideSignedUserUseCases(
        firebase: Firebase
    ):SignedUserUseCases
    = SignedUserUseCases(
        signedOut = SignOutUseCase(firebase),
        getIdToken = GetIdTokenUseCase(firebase),
        getUid = GetUidUseCase(firebase)
    )

}