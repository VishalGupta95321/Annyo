package com.example.onboarding_presentation.secondary_screens.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Gender
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenderViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val genderState = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GenderUiState()
        )

    private val _genderUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val genderUpdateState: StateFlow<ProfileUpdateState> = _genderUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateGender(gender: Gender) {

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateGender(gender, false)
                .also { _genderUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _genderUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _genderUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<GenderUiState> {
        return profileUseCases.getProfile()
            .onStart { GenderUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        GenderUiState()
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            GenderUiState(it.gender)
                        }?: GenderUiState()
                    }
                }
            }
    }
}
