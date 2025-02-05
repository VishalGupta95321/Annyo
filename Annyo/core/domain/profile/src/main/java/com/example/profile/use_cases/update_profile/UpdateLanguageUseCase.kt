package com.example.profile.use_cases.update_profile

import com.example.profile.model.Languages
import com.example.profile.repository.ProfileRepository
import com.example.utils.ErrorMessages.MAXIMUM_SELECTION_REACHED
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        languages: Languages,
        updateToServer : Boolean = true
    ):GetBackBasic{

        if (languages.languages.size > 5)
            return GetBack.Error(MAXIMUM_SELECTION_REACHED)

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(languages)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(languages)
            }
        else
            repository.updateProfileToDataStore(languages)
    }
}