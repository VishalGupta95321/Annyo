package com.example.profile.use_cases.update_profile

import com.example.profile.model.Job
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateJobUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        job: Job,
        updateToServer : Boolean = true
    ):GetBackBasic{

//        if (job.jobTitle.isBlank())
//            return GetBack.Success()     /// No need to give error here because its not mandatory for user to add job

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(job)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(job)
            }
        else
            repository.updateProfileToDataStore(job)
    }
}