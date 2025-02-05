package com.example.onboarding_presentation.secondary_screens.languages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Languages
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguagesViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel(){

    val languagesState: StateFlow<LanguagesUiState> = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LanguagesUiState.Loading
        )

    private val _languagesUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val languagesUpdateState: StateFlow<ProfileUpdateState> = _languagesUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var _query = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> = _query
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    private var job: Job? = null

    fun updateLanguages(languages : Languages) {

        job?.cancel()
        job  = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateLanguages(languages,false)
                .also { _languagesUpdateState
                    .value = ProfileUpdateState.Updating
                    when (it) {
                        is GetBack.Error -> {
                            _languagesUpdateState
                                .value = ProfileUpdateState.UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _languagesUpdateState
                                .value = ProfileUpdateState.Updated
                        }
                    }
                }
        }
    }

    fun onQueryChange(query: String?){
        _query.value = query
    }

    private fun getProfile(): Flow<LanguagesUiState> {
        return profileUseCases.getProfile()
            .asResult()
            .mapLatest {
                when (it) {
                    is GetBack.Error -> {
                        LanguagesUiState.Error
                    }
                    is GetBack.Success -> {
                        it.data?.let { languages ->
                            LanguagesUiState.Success(languages.languages)
                        }?: LanguagesUiState.Error
                    }
                }
            }
    }
}
