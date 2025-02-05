package com.annyo.firebase_service.domain.use_case

data class SignedUserUseCases(
    val signedOut : SignOutUseCase,
    val getUid :  GetUidUseCase,
    val getIdToken : GetIdTokenUseCase
)