package com.example.onboarding_presentation.secondary_screens.zodiac_sign


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.ZodiacSign
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZodiacSignViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val zodiacSignState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ZodiacSignUiState()
    )

    private val _zodiacSignUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val zodiacSignUpdateState: StateFlow<ProfileUpdateState> = _zodiacSignUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateZodiacSign(zodiacSign: ZodiacSign) {
        job?.cancel()
        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateZodiac(zodiacSign, false)
                .also { _zodiacSignUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _zodiacSignUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _zodiacSignUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<ZodiacSignUiState> {
        return profileUseCases.getProfile()
            .onStart { ZodiacSignUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        ZodiacSignUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            ZodiacSignUiState(it.zodiacSign)
                        }?: ZodiacSignUiState()
                    }
                }
            }
    }
}
