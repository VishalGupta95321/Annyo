package com.example.onboarding_presentation.secondary_screens.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Pets
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val petsState: StateFlow<PetsUiState> = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PetsUiState()
        )

    private val _petsUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val petsUpdateState: StateFlow<ProfileUpdateState> = _petsUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updatePets(pets: Pets) {
        job?.cancel()
        job  = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updatePets(pets, false)
                .also { _petsUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _petsUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _petsUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<PetsUiState> {
        return profileUseCases.getProfile()
            .onStart { PetsUiState(isLoading = true) }
            .asResult()
            .mapLatest {
                when (it) {
                    is GetBack.Error -> {
                        PetsUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        it.data?.let { pet ->
                            PetsUiState(pet.pets)
                        }?:PetsUiState()
                    }
                }
            }
    }
}
