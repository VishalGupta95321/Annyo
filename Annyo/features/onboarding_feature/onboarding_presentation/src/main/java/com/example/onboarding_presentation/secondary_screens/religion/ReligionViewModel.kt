package com.example.onboarding_presentation.secondary_screens.religion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Religion
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReligionViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val religionState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReligionUiState()
    )

    private val _religionUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val religionUpdateState: StateFlow<ProfileUpdateState> = _religionUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateReligion(religion: Religion) {
        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateReligion(religion, false)
                .also { _religionUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _religionUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _religionUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<ReligionUiState> {
        return profileUseCases.getProfile()
            .onStart { ReligionUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        ReligionUiState()
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            ReligionUiState(it.religion)
                        }?: ReligionUiState()
                    }
                }
            }
    }
}
