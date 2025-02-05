package com.example.profile.use_cases.update_profile

import com.example.profile.model.College
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateCollegeUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        college: College,
        updateToServer : Boolean = true
    ):GetBackBasic{

//        if (college.collegeName.isBlank())
//            return GetBack.Success()     /// No need to give error here because its not mandatory for user to add college

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(college)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(college)
            }
        else
            repository.updateProfileToDataStore(college)
    }
}