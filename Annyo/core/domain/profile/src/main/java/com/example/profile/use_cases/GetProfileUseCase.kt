package com.example.profile.use_cases

import com.example.profile.model.Profile
import com.example.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
     operator fun invoke():Flow<Profile>{
        return repository.getProfile()
    }
}