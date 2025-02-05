package com.example.onboarding_presentation.secondary_screens.children

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Children
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChildrenViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val childrenState = getProfile()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChildrenUiState()
    )

    private val _childrenUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val childrenUpdateState: StateFlow<ProfileUpdateState> = _childrenUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var job: Job? = null

    fun updateChildren(children: Children) {

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateChildren(children, false)
                .also { _childrenUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _childrenUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _childrenUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    private fun getProfile(): Flow<ChildrenUiState> {
        return profileUseCases.getProfile()
            .onStart { ChildrenUiState(isLoading = true) }
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is GetBack.Error -> {
                        ChildrenUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        result.data?.let {
                            ChildrenUiState(it.children)
                        }?: ChildrenUiState()
                    }
                }
            }
    }
}