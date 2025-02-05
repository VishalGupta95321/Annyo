package com.example.onboarding_presentation.secondary_screens.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    private var _jobState = MutableStateFlow(JobUiState())
    val jobState: StateFlow<JobUiState> = _jobState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = JobUiState()
    )

    private val _jobUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val jobUpdateState: StateFlow<ProfileUpdateState> = _jobUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateJob( Job: com.example.profile.model.Job) {

        _jobState.value = _jobState.value
            .copy(job = Job)

        this.job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateJob(Job, false)
                .also { _jobUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _jobUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _jobUpdateState
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
                    _jobState
                        .value = _jobState.value
                        .copy(isLoading = true)
                }.first()
                .also {
                    _jobState
                        .value = _jobState.value
                        .copy(
                            job = it.job,
                            isLoading = false
                        )
                }
        }
    }
    init { getProfile() }
}