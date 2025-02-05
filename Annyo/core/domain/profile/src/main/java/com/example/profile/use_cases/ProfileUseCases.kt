package com.example.profile.use_cases

import com.example.profile.use_cases.update_profile.UpdateProfileUseCases

data class ProfileUseCases(
    val createProfile : CreateProfileUseCase,
    val updateProfile : UpdateProfileUseCases,
    val getProfile : GetProfileUseCase,
    val uploadPhotoToCloud : UploadPhotoToCloudUseCase,
    val syncProfileWithServerUseCase: SyncProfileWithServerUseCase,
    val validateDateUseCase: ValidateDateUseCase,
    val birthDateToAgeUseCase: BirthDateToAgeUseCase,
    val testGet: TestGet,
    val coordinatesToAddressUseCase: CoordinatesToAddressUseCase,
    val getProfileCompletedPercUseCase: GetProfileCompletedPercUseCase,
)