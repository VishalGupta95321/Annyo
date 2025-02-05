package com.example.onboarding_presentation.primary_screen

sealed class OnboardingUiState(val screenOrder: Int) {
    object OnboardingName : OnboardingUiState(1)
    object OnboardingGender : OnboardingUiState(2)
    object OnboardingSexuality : OnboardingUiState(3)
    object OnboardingDOB : OnboardingUiState(4)
    object OnboardingHeight : OnboardingUiState(5)
    object OnboardingEthnicity : OnboardingUiState(6)
    object OnboardingReligion : OnboardingUiState(7)
    object OnboardingZodiacSign : OnboardingUiState(8)
    object OnboardingCollege : OnboardingUiState(9)
    object OnboardingJob : OnboardingUiState(10)
    object OnboardingLocation : OnboardingUiState(11)
    object OnboardingChildren : OnboardingUiState(12)
    object OnboardingSmoking : OnboardingUiState(13)
    object OnboardingDrinking : OnboardingUiState(14)
    object OnboardingWorkout : OnboardingUiState(15)
    object OnboardingPets : OnboardingUiState(16)
    object OnboardingInterests : OnboardingUiState(17)
    object OnboardingLanguages : OnboardingUiState(18)
    object OnboardingAboutMe : OnboardingUiState(19)
    object OnboardingPhotos : OnboardingUiState(20)
}