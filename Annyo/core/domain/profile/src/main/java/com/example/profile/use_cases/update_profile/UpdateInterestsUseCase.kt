package com.example.profile.use_cases.update_profile

import com.example.profile.model.Interests
import com.example.profile.repository.ProfileRepository
import com.example.utils.ErrorMessages
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateInterestsUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        interests: Interests,
        updateToServer : Boolean = true
    ):GetBackBasic{

        if (interests.interests.size > 5)
            return GetBack.Error(ErrorMessages.MAXIMUM_SELECTION_REACHED)

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(interests)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(interests)
            }
        else
            repository.updateProfileToDataStore(interests)
    }
}