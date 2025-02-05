package com.example.profile.use_cases.update_profile

import com.example.profile.model.Gender
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateGenderUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        gender: Gender,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(gender)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(gender)
            }
        else
            repository.updateProfileToDataStore(gender)
    }
}