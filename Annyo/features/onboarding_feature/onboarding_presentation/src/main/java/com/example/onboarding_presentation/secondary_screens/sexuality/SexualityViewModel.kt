package com.example.onboarding_presentation.secondary_screens.sexuality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Sexuality
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SexualityViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val sexualityState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SexualityUiState()
    )

    private val _sexualityUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val sexualityUpdateState: StateFlow<ProfileUpdateState> = _sexualityUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateSexuality(sexuality: Sexuality) {

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateSexuality(sexuality, false)
                .also { _sexualityUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _sexualityUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _sexualityUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<SexualityUiState> {
        return profileUseCases.getProfile()
            .onStart { SexualityUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        SexualityUiState()
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            SexualityUiState(it.sexuality)
                        }?: SexualityUiState()
                    }
                }
            }
    }
}
