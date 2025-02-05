package com.example.profile.use_cases.update_profile

import com.example.profile.model.Religion
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic

class UpdateReligionUseCase(
    private val repository: ProfileRepository
){
    suspend operator fun invoke(
        religion: Religion,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(religion)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(religion)
            }
        else
            repository.updateProfileToDataStore(religion)
    }}