package com.example.profile.use_cases.update_profile

import com.example.profile.model.DateOfBirth
import com.example.profile.repository.ProfileRepository
import com.example.profile.use_cases.ValidateDateUseCase
import com.example.utils.ErrorMessages.INVALID_DATE
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateDateOfBirthUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val validateDateUseCase: ValidateDateUseCase
) {
    suspend operator fun invoke(
        dateOfBirth: DateOfBirth,
        updateToServer : Boolean = true
    ):GetBackBasic{

        val isValidDate = validateDateUseCase(
            year = dateOfBirth.year, month = dateOfBirth.month, day = dateOfBirth.day
        )

        if(!isValidDate){
            return GetBack.Error(INVALID_DATE)
        }

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(dateOfBirth)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(dateOfBirth)
            }
        else
            repository.updateProfileToDataStore(dateOfBirth)
    }

}