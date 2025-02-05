package com.example.onboarding_presentation.secondary_screens.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Name
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    private var _nameState = MutableStateFlow(NameUiState())
    val nameState: StateFlow<NameUiState> = _nameState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NameUiState()
    )

    private val _nameUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val nameUpdateState: StateFlow<ProfileUpdateState> = _nameUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateName(name: Name) {

        _nameState.value = _nameState.value
            .copy(name = name)

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateName(name, false)
                .also { _nameUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _nameUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _nameUpdateState
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
                    _nameState
                        .value = _nameState.value
                        .copy(isLoading = true)
                }.first()
                .also {
                    _nameState
                        .value = _nameState.value
                        .copy(
                            name = it.name,
                            isLoading = false
                        )
                }
        }
    }
    init { getProfile() }
}