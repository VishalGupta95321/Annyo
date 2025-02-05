package com.example.onboarding_presentation.secondary_screens.photos

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Photo
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    val photoState: StateFlow<PhotoUiState> = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PhotoUiState()
        )

    private val _photoUpdateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.NotUpdating)
    val photoUpdateState: StateFlow<ProfileUpdateState> = _photoUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    fun updatePhoto(photo: Photo) {

        viewModelScope.launch {

            _photoUpdateState.value = ProfileUpdateState.Updating

            updatePhotoToCloud(photo)?.let { uri ->

                profileUseCases.updateProfile
                    .updatePhoto(
                        photo.copy(
                            photoUrl = uri.toString()
                        ),
                        false
                    ).also {
                        when (it) {
                            is GetBack.Error -> {
                                _photoUpdateState
                                    .value = ProfileUpdateState
                                    .UpdateFailed()
                            }
                            is GetBack.Success -> {
                                _photoUpdateState
                                    .value = ProfileUpdateState
                                    .Updated
                            }
                        }
                    }
            }
        }
    }

    private suspend fun updatePhotoToCloud(photo: Photo): Uri? {
        profileUseCases.uploadPhotoToCloud(photo)
            .also {
                return when (it) {
                    is GetBack.Error -> {
                        _photoUpdateState.value = ProfileUpdateState
                            .UpdateFailed(it.message)
                        null
                    }
                    is GetBack.Success -> {
                        it.data
                    }
                }
            }
    }

    private fun getProfile(): Flow<PhotoUiState> {
        return profileUseCases.getProfile()
            .onStart { PhotoUiState(isLoading = true) }
            .asResult()
            .map {
                when (it) {
                    is GetBack.Error -> {
                        PhotoUiState(isLoading = false)
                    }
                    is GetBack.Success -> {
                        PhotoUiState(
                            photo1 = it.data?.photo1,
                            photo2 = it.data?.photo2,
                            photo3 = it.data?.photo3,
                            photo4 = it.data?.photo4,
                            photo5 = it.data?.photo5,
                            photo6 = it.data?.photo6,
                            isLoading = false
                        )
                    }
                }
            }
    }
}