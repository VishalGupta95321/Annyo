package com.example.onboarding_presentation.secondary_screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Workout
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val workoutState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WorkoutUiState()
    )

    private val _workoutUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val workoutUpdateState: StateFlow<ProfileUpdateState> = _workoutUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateWorkout(workout: Workout) {
        job?.cancel()
        job = viewModelScope.launch {
            profileUseCases
                .updateProfile
                .updateWorkout(workout, false)
                .also { _workoutUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _workoutUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _workoutUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<WorkoutUiState> {
        return profileUseCases.getProfile()
            .onStart { WorkoutUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        WorkoutUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            WorkoutUiState(it.workout)
                        }?: WorkoutUiState()
                    }
                }
            }
    }
}
