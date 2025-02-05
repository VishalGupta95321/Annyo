package com.example.profile.use_cases.update_profile

import com.example.profile.model.Pets
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdatePetsUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        pets: Pets,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(pets)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(pets)
            }
        else
            repository.updateProfileToDataStore(pets)
    }
}