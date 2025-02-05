package com.example.profile.use_cases

import com.example.profile.model.Profile
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestGet @Inject constructor(
    private val repository: ProfileRepository
) {
    operator  fun invoke(): Flow<GetBack<Flow<Profile>>> {
        return repository.testProfile()
    }

}