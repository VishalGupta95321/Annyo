package com.example.profile.use_cases.update_profile

import com.example.profile.model.Children
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateChildrenUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        children : Children,
        updateToServer : Boolean = true
    ):GetBackBasic{
        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(children)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(children)
            }
        else
            repository.updateProfileToDataStore(children)
    }
}