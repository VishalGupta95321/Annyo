package com.example.profile_presentation.secondary_screens.edit.states

sealed interface ProfileEditScreenState {         //// todo change the name  to something better
    data object NotEditing: ProfileEditScreenState
    data object EditQUiz : ProfileEditScreenState
    data object EditName : ProfileEditScreenState
    data object EditGender : ProfileEditScreenState
    data object EditSexuality : ProfileEditScreenState
    data object EditDOB : ProfileEditScreenState
    data object EditHeight : ProfileEditScreenState
    data object EditEthnicity : ProfileEditScreenState
    data object EditReligion : ProfileEditScreenState
    data object EditZodiacSign: ProfileEditScreenState
    data object EditCollege : ProfileEditScreenState
    data object EditJob : ProfileEditScreenState
    data object EditLocation : ProfileEditScreenState
    data object EditChildren : ProfileEditScreenState
    data object EditSmoking : ProfileEditScreenState
    data object EditDrinking : ProfileEditScreenState
    data object EditWorkout : ProfileEditScreenState
    data object EditPets : ProfileEditScreenState
    data object EditInterests : ProfileEditScreenState
    data object EditLanguages : ProfileEditScreenState
    data object EditAboutMe : ProfileEditScreenState
}