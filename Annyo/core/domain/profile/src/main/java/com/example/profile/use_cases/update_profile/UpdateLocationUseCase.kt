package com.example.profile.use_cases.update_profile

import com.example.profile.model.Location
import com.example.profile.repository.ProfileRepository
import com.example.profile.use_cases.CoordinatesToAddressUseCase
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val coordinatesToAddressUseCase: CoordinatesToAddressUseCase
) {
    suspend operator fun invoke(
        location: Location,
        updateToServer: Boolean = true
    ): GetBackBasic {

        val loc = location.copy(
            address = coordinatesToAddressUseCase(
                location.latitude, location.longitude
            )
        )


        return if (updateToServer)
            return when (
                val serverRes = repository.updateProfileToServer(loc)
            ) {
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(loc)
            }
        else
            repository.updateProfileToDataStore(loc)
    }
}