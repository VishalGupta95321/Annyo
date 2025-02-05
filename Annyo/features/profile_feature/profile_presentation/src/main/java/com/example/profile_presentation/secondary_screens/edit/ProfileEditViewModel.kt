package com.example.profile_presentation.secondary_screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profile.model.*
import com.example.profile.use_cases.ProfileUseCases
import com.example.profile.util.ProfileModel
import com.example.profile_presentation.secondary_screens.edit.states.ProfileEditScreenState
import com.example.profile_presentation.secondary_screens.edit.states.ProfileState
import com.example.profile_presentation.secondary_screens.edit.states.ProfileUpdateState
import com.example.utils.GetBack
import com.example.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    val profileState: StateFlow<ProfileState> = getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileState.Loading
        )

    private var _profileUpdateState: MutableStateFlow<ProfileUpdateState> =
        MutableStateFlow(ProfileUpdateState.NotUpdating)
    val profileUpdateState: StateFlow<ProfileUpdateState> = _profileUpdateState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUpdateState.NotUpdating
        )

    private var _editScreensState: MutableStateFlow<ProfileEditScreenState> =
        MutableStateFlow(ProfileEditScreenState.NotEditing)
    val editScreensState: StateFlow<ProfileEditScreenState> = _editScreensState


    private var _aboutMeTextFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val aboutMeTextFieldState: StateFlow<String> = _aboutMeTextFieldState

    private var _collegeTextFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val collegeTextFieldState: StateFlow<String> = _collegeTextFieldState

    private var _jobTextFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val jobTextFieldState: StateFlow<String> = _jobTextFieldState


    private var _langSearchTextFieldState = MutableStateFlow("")
    val langSearchQuery : StateFlow<String> = _langSearchTextFieldState

    private var _addInterestTextFieldState = MutableStateFlow("")
    val addInterestTextFieldState : StateFlow<String> = _addInterestTextFieldState


    fun onLangSearchQueryChange(query:String){
        _langSearchTextFieldState.value = query
    }

    fun onAddInterestTextChange(interest:String){
        _addInterestTextFieldState.value = interest
    }

    fun onScreenStateChange(screen:ProfileEditScreenState){
        _editScreensState.value = screen
    }


    private var job : kotlinx.coroutines.Job? = null

    fun updateProfile(profileModel: ProfileModel) {

        job?.cancel()

        job  =   viewModelScope.launch {

            _profileUpdateState.value = ProfileUpdateState.Updating

            when (profileModel) {

                is AboutMe -> {
                    _aboutMeTextFieldState.value = profileModel.aboutYou
                    profileUseCases.updateProfile.updateAboutMe(profileModel,false)
                }    /////// TODO set true when the server is live

                is Gender -> profileUseCases.updateProfile.updateGender(profileModel,false)

                is Smoking -> profileUseCases.updateProfile.updateSmoking(profileModel,false)

                is Drinking -> profileUseCases.updateProfile.updateDrinking(profileModel,false)

                is Languages -> profileUseCases.updateProfile.updateLanguages(profileModel,false)

                is Interests -> profileUseCases.updateProfile.updateInterests(profileModel,false)

                is Pets -> profileUseCases.updateProfile.updatePets(profileModel,false)

                is Name -> profileUseCases.updateProfile.updateName(profileModel,false)

                is Location -> profileUseCases.updateProfile.updateLocation(profileModel,false)

                is Religion -> profileUseCases.updateProfile.updateReligion(profileModel,false)

                is Sexuality -> profileUseCases.updateProfile.updateSexuality(profileModel,false)

                is Workout -> profileUseCases.updateProfile.updateWorkout(profileModel,false)

                is ZodiacSign -> profileUseCases.updateProfile.updateZodiac(profileModel,false)

                is Photo -> profileUseCases.updateProfile.updatePhoto(profileModel,false)

                is DateOfBirth -> profileUseCases.updateProfile.updateDateOfBirth(profileModel,false)

                is Children -> profileUseCases.updateProfile.updateChildren(profileModel,false)

                is Job -> {
                    _jobTextFieldState.value = profileModel.jobTitle
                    profileUseCases.updateProfile.updateJob(profileModel,false)
                }

                is Height -> profileUseCases.updateProfile.updateHeight(profileModel,false)

                is Ethnicity -> profileUseCases.updateProfile.updateEthnicity(profileModel,false)

                is College -> {
                    _collegeTextFieldState.value = profileModel.collegeName
                    profileUseCases.updateProfile.updateCollege(profileModel,false)
                }

                else -> GetBack.Error()

            }.also { result ->
                when (result) {
                    is GetBack.Error -> _profileUpdateState.value =
                        ProfileUpdateState.UpdateFailed(result.message)
                    is GetBack.Success -> _profileUpdateState.value = ProfileUpdateState.Updated
                }
            }
        }
    }


    private fun getProfile(): Flow<ProfileState> {
        return profileUseCases.getProfile()
            .asResult()
            .map { result ->
                when (result) {
                    is GetBack.Error -> ProfileState.Error(result.message)
                    is GetBack.Success -> result.data?.let {
                        ProfileState.Success(it)
                    } ?: ProfileState.Error()
                }
            }
    }
}

//todo = set like if there is a limit of updating dob , or you can update name etc.