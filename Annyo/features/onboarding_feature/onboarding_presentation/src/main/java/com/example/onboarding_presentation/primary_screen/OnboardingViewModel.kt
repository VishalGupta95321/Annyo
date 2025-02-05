package com.example.onboarding_presentation.primary_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Profile
import com.example.profile.model.isCompleted
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private var _onBoardingUiState = MutableStateFlow<OnboardingUiState>(
        OnboardingUiState.OnboardingName
    )
    val onboardingUiState: StateFlow<OnboardingUiState> = _onBoardingUiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            OnboardingUiState.OnboardingName
        )

    private var _profileUpdateState = MutableStateFlow<ProfileUpdateState>(
        ProfileUpdateState.NotUpdating
    )
    val profileUploadState: StateFlow<ProfileUpdateState> = _profileUpdateState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProfileUpdateState.NotUpdating
        )

    fun onOnboardingUiStateChange(
        onboardingUiState: OnboardingUiState
    ) {
        _onBoardingUiState.value = onboardingUiState
    }

    private fun setOnboardingScreenWhereUserLeft() {
        viewModelScope.launch {
            getProfile()?.let { profile ->
                profile.isCompleted(
                    name = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingGender
                    },
                    gender = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingSexuality
                    },
                    sexuality = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingDOB
                    },
                    dateOfBirth = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingHeight
                    },
                    height = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingEthnicity
                    },
                    ethnicity = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingReligion
                    },
                    religion = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingZodiacSign
                    },
                    zodiacSign = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingCollege
                    },
                    college = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingJob
                    },
                    job = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingLocation
                    },
                    location = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingChildren
                    },
                    children = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingSmoking
                    },
                    smoking = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingDrinking
                    },
                    drinking = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingWorkout
                    },
                    workout = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingPets
                    },
                    pets = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingInterests
                    },
                    interests = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingLanguages
                    },
                    languages = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingAboutMe
                    },
                    aboutMe = {
                        if (it) _onBoardingUiState.value = OnboardingUiState.OnboardingPhotos
                    },
                )
            }
        }
    }

    private suspend fun getProfile(): Profile? {
        profileUseCases.getProfile()
            .asResult()
            .first()
            .also {
                return when (it) {
                    is GetBack.Error -> {
                        null
                    }
                    is GetBack.Success -> {
                        it.data
                    }
                }
            }
    }

    fun createProfile() {
        viewModelScope.launch {
            _profileUpdateState.value = ProfileUpdateState.Updating
            getProfile()?.let { profile ->
                profileUseCases.createProfile(profile)
                    .also {
                        when (it) {
                            is GetBack.Error -> {
                                _profileUpdateState.value = ProfileUpdateState
                                    .UpdateFailed(it.message)
                            }
                            is GetBack.Success -> {
                                _profileUpdateState.value = ProfileUpdateState
                                    .Updated
                            }
                        }
                    }
            } ?: run { _profileUpdateState.value = ProfileUpdateState.UpdateFailed() }
        }
    }

    init {
        setOnboardingScreenWhereUserLeft()
    }
}
/// check if we have to show the onboarding