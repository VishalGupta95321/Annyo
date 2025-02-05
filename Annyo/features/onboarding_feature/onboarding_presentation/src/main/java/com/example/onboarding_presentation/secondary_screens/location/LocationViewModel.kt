package com.example.onboarding_presentation.secondary_screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Location
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val locationState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LocationUiState()
    )

    private val _locationUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val locationUpdateState: StateFlow<ProfileUpdateState> = _locationUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    fun updateLocation(location: Location) {
        viewModelScope.launch {
            profileUseCases
                .updateProfile
                .updateLocation(location, false)
                .also { _locationUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _locationUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _locationUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<LocationUiState> {
        return profileUseCases.getProfile()
            .onStart { LocationUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        LocationUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            LocationUiState(it.location)
                        }?: LocationUiState()
                    }
                }
            }
    }
}