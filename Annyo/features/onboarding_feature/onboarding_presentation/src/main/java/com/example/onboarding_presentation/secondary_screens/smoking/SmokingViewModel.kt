package com.example.onboarding_presentation.secondary_screens.smoking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Smoking
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmokingViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val smokingState = getProfile().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SmokingUiState()
    )

    private val _smokingUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val smokingUpdateState: StateFlow<ProfileUpdateState> = _smokingUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateSmoking(smoking: Smoking) {
        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateSmoking(smoking, false)
                .also { _smokingUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _smokingUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _smokingUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<SmokingUiState> {
        return profileUseCases.getProfile()
            .onStart { SmokingUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        SmokingUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            SmokingUiState(it.smoking)
                        }?: SmokingUiState()
                    }
                }
            }
    }
}
