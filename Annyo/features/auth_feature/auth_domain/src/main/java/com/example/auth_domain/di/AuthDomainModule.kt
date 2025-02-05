package com.example.auth_domain.di

import com.example.auth_domain.repository.GoogleSignInRepository
import com.example.auth_domain.repository.PhoneSignInRepository
import com.example.auth_domain.usecases.google_signIn.GoogleSignInUseCases
import com.example.auth_domain.usecases.google_signIn.SignInUsingGoogle
import com.example.auth_domain.usecases.phone_signIn.PhoneSignInUseCases
import com.example.auth_domain.usecases.phone_signIn.SignInUsingPhone
import com.example.auth_domain.usecases.phone_signIn.ValidatePhoneNumber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    @Singleton
    fun provideGoogleSignInUseCases(
        repository: GoogleSignInRepository
    ):GoogleSignInUseCases{
        return GoogleSignInUseCases(
            signInUsingGoogle = SignInUsingGoogle(repository)
        )
    }

    @Provides
    @Singleton
    fun providePhoneSignInUseCases(
        repository: PhoneSignInRepository
    ): PhoneSignInUseCases {
        return PhoneSignInUseCases(
            signInUsingPhone = SignInUsingPhone(repository),
            validatePhoneNumber = ValidatePhoneNumber()
        )
    }

}
