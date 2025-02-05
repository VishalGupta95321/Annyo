package com.example.profile.use_cases.update_profile

import com.example.profile.model.Ethnicity
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateEthnicityUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        ethnicity: Ethnicity,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(ethnicity)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(ethnicity)
            }
        else
            repository.updateProfileToDataStore(ethnicity)
    }
}