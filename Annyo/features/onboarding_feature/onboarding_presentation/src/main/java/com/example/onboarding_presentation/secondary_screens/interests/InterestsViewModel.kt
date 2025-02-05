package com.example.onboarding_presentation.secondary_screens.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Interests
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    val interestsState: StateFlow<InterestsUiState> = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InterestsUiState()
        )

    private val _interestsUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val interestsUpdateState: StateFlow<ProfileUpdateState> = _interestsUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private val _addTextFieldState = MutableStateFlow("")
    val addTextFieldState: StateFlow<String> = _addTextFieldState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    fun onAddTextChange(text: String) {
        _addTextFieldState.value = text
    }

    fun updateNewInterests(interest: String) {
        if (addTextFieldState.value.isNotEmpty()){
            updateInterests(
                Interests(
                    interestsState.value
                        .interests.interests + interest
                )
            ) }

        _addTextFieldState.value = ""
    }

    private var job: Job? = null

    fun updateInterests(interests: Interests) {
        job?.cancel()
        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateInterests(interests, false)
                .also {
                    _interestsUpdateState
                        .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _interestsUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _interestsUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<InterestsUiState> {
        return profileUseCases.getProfile()
            .onStart { InterestsUiState(isLoading = true) }
            .asResult()
            .mapLatest {
                when (it) {
                    is GetBack.Error -> {
                        InterestsUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        it.data?.let { pet ->
                            InterestsUiState(pet.interests)
                        } ?: InterestsUiState()
                    }
                }
            }
    }
}
