package com.example.profile.use_cases.update_profile

import com.example.profile.model.AboutMe
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateAboutMeUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        aboutMe : AboutMe,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(aboutMe)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(aboutMe)
            }
        else
            repository.updateProfileToDataStore(aboutMe)
    }
}