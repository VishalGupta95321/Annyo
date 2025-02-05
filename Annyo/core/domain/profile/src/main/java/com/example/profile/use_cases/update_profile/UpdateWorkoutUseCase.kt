package com.example.profile.use_cases.update_profile

import com.example.profile.model.Workout
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateWorkoutUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        workout: Workout,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(workout)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(workout)
            }
        else
            repository.updateProfileToDataStore(workout)
    }
}