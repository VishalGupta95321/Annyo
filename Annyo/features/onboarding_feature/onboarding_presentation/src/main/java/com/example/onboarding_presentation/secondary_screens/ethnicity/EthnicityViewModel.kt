package com.example.onboarding_presentation.secondary_screens.ethnicity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Ethnicity
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EthnicityViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val ethnicityState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = EthnicityUiState()
    )

    private val _ethnicityUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val ethnicityUpdateState: StateFlow<ProfileUpdateState> = _ethnicityUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateEthnicity(ethnicity: Ethnicity) {

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateEthnicity(ethnicity, false)
                .also { _ethnicityUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _ethnicityUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _ethnicityUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<EthnicityUiState> {
        return profileUseCases.getProfile()
            .onStart { EthnicityUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        EthnicityUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            EthnicityUiState(it.ethnicity)
                        }?: EthnicityUiState()
                    }
                }
            }
    }
}
