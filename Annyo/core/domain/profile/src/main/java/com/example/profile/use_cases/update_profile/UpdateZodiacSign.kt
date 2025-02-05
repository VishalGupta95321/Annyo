package com.example.profile.use_cases.update_profile

import com.example.profile.model.ZodiacSign
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateZodiacSign @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        zodiacSign: ZodiacSign,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(zodiacSign)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(zodiacSign)
            }
        else
            repository.updateProfileToDataStore(zodiacSign)
    }
}