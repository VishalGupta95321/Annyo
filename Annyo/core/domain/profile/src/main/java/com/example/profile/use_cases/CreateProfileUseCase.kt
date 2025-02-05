package com.example.profile.use_cases

import com.example.profile.model.Profile
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBackBasic
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend  operator fun invoke(
        request: Profile
    ):GetBackBasic{
//        if(request.name.name.isBlank())
//            return GetBack.Error(NAME_CANNOT_NOT_BE_EMPTY)    /// we don't need this actually because user cannot go through this if he has not entered his name
        return repository.createProfile(request)
    }
}