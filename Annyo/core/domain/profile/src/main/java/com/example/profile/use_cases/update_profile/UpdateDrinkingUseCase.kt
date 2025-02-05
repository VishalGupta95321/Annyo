package com.example.profile.use_cases.update_profile

import com.example.profile.model.Drinking
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateDrinkingUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        drinking: Drinking,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(drinking)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(drinking)
            }
        else
            repository.updateProfileToDataStore(drinking)
    }
}