package com.example.onboarding_presentation.secondary_screens.height

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeightViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val heightState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HeightUiState()
    )

    private val _heightUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val heightUpdateState: StateFlow<ProfileUpdateState> = _heightUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateHeight( height: com.example.profile.model.Height) {
        job?.cancel()
        viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateHeight(height, false)
                .also { _heightUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _heightUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _heightUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<HeightUiState> {
        return profileUseCases.getProfile()
            .onStart { HeightUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        HeightUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            HeightUiState(it.height)
                        }?: HeightUiState()
                    }
                }
            }
    }
}