package com.example.auth_data.di

import com.example.auth_data.repository.GoogleSignInRepositoryImpl
import com.example.auth_data.repository.PhoneSIgnInRepositoryImpl
import com.example.auth_domain.repository.GoogleSignInRepository
import com.example.auth_domain.repository.PhoneSignInRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthDataModule {

    @Binds
    @Singleton
    fun bindGoogleSignInRepository(repo:GoogleSignInRepositoryImpl) : GoogleSignInRepository

    @Binds
    @Singleton
    fun bindPhoneSignInRepository(repo:PhoneSIgnInRepositoryImpl) : PhoneSignInRepository

}