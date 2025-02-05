package com.example.profile.use_cases.update_profile

import com.example.profile.model.Name
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateNameUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend  operator fun invoke(
        name: Name,
        updateToServer : Boolean = true
    ):GetBackBasic{

//        if (name.name.isBlank())
//            return GetBack.Error(NAME_CANNOT_NOT_BE_EMPTY)

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(name)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(name)
            }
        else
            repository.updateProfileToDataStore(name)
    }
}