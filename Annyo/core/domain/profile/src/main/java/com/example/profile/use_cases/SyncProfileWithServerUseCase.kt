package com.example.profile.use_cases

import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBackBasic
import javax.inject.Inject

class SyncProfileWithServerUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke():GetBackBasic {
        return repository.syncProfileWithServer()
    }
}
