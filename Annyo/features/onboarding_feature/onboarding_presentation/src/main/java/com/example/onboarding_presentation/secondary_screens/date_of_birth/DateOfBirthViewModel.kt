package com.example.onboarding_presentation.secondary_screens.date_of_birth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.DateOfBirth
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DateOfBirthViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    private var _dateOfBirthState = MutableStateFlow(DateOfBirthUiState())
    val dateOfBirthState: StateFlow<DateOfBirthUiState> = _dateOfBirthState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DateOfBirthUiState()
    )

    private val _dateOfBirthUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val dateOfBirthUpdateState: StateFlow<ProfileUpdateState> = _dateOfBirthUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )


    fun onDateOfBirthChange(
        dateOfBirth: DateOfBirth
    ){
        _dateOfBirthState.value = _dateOfBirthState.value
            .copy(dateOfBirth = dateOfBirth)

        updateDateOfBirth(_dateOfBirthState.value.dateOfBirth)
    }

    private fun updateDateOfBirth(dateOfBirth: DateOfBirth) {
        viewModelScope.launch {
            profileUseCases
                .updateProfile
                .updateDateOfBirth(dateOfBirth, false)
                .also { _dateOfBirthUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _dateOfBirthUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _dateOfBirthUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileUseCases
                .getProfile()
                .onStart {
                    _dateOfBirthState
                        .value = _dateOfBirthState.value
                        .copy(isLoading = true)
                }.first()
                .also {
                    _dateOfBirthState
                        .value = _dateOfBirthState.value
                        .copy(
                            dateOfBirth = it.dateOfBirth,
                            isLoading = false
                        )
                }
        }
    }
    init { getProfile() }
}