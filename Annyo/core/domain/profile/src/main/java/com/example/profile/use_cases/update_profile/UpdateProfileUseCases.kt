package com.example.profile.use_cases.update_profile

data class UpdateProfileUseCases(
    val updateName : UpdateNameUseCase,
    val updateGender : UpdateGenderUseCase,
    val updateSexuality: UpdateSexualityUseCase,
    val updateAboutMe: UpdateAboutMeUseCase,
    val updateCollege: UpdateCollegeUseCase,
    val updatePets: UpdatePetsUseCase,
    val updateDrinking: UpdateDrinkingUseCase,
    val updateSmoking: UpdateSmokingUseCase,
    val updateZodiac: UpdateZodiacSign,
    val updateHeight: UpdateHeightUseCase,
    val updateChildren: UpdateChildrenUseCase,
    val updateReligion: UpdateReligionUseCase,
    val updateEthnicity: UpdateEthnicityUseCase,
    val updateInterests: UpdateInterestsUseCase,
    val updateWorkout: UpdateWorkoutUseCase,
    val updateJob: UpdateJobUseCase,
    val updatePhoto: UpdatePhotoUseCase,
    val updateLanguages: UpdateLanguageUseCase,
    val updateDateOfBirth: UpdateDateOfBirthUseCase,
    val updateLocation: UpdateLocationUseCase
)
