package com.example.onboarding_presentation.secondary_screens.college

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.College
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CollegeViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
):ViewModel(){

    private var _collegeState = MutableStateFlow(CollegeUiState())
    val collegeState: StateFlow<CollegeUiState> = _collegeState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CollegeUiState()
    )

    private val _collegeUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val collegeUpdateState: StateFlow<ProfileUpdateState> = _collegeUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateCollege(college: College) {

        _collegeState.value = _collegeState.value
            .copy(college = college)

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateCollege(college, false)
                .also { _collegeUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _collegeUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _collegeUpdateState
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
                    _collegeState
                        .value = _collegeState.value
                        .copy(isLoading = true)
                }.first()
                .also {
                    _collegeState
                        .value = _collegeState.value
                        .copy(
                            college = it.college,
                            isLoading = false
                        )
                }
        }
    }
    init { getProfile() }
}