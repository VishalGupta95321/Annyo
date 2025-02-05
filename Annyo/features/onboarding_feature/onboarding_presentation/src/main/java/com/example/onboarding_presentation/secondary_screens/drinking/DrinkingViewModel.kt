package com.example.onboarding_presentation.secondary_screens.drinking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Drinking
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DrinkingViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val drinkingState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DrinkingUiState()
    )

    private val _drinkingUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val drinkingUpdateState: StateFlow<ProfileUpdateState> = _drinkingUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateDrinking(drinking: Drinking) {

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateDrinking(drinking, false)
                .also { _drinkingUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _drinkingUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _drinkingUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<DrinkingUiState> {
        return profileUseCases.getProfile()
            .onStart { DrinkingUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        DrinkingUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            DrinkingUiState(it.drinking)
                        }?: DrinkingUiState()
                    }
                }
            }
    }
}
