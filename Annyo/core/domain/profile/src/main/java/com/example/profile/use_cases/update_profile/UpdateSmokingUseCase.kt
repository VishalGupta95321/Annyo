package com.example.profile.use_cases.update_profile

import com.example.profile.model.Smoking
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateSmokingUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        smoking: Smoking,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(smoking)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(smoking)
            }
        else
            repository.updateProfileToDataStore(smoking)
    }
}