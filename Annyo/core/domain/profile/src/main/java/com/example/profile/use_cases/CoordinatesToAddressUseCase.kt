package com.example.profile.use_cases

import com.example.profile.util.GeocoderService
import javax.inject.Inject

class CoordinatesToAddressUseCase @Inject constructor(
    private val reverseGeocoderService: GeocoderService
){
    suspend operator fun invoke(
        latitude : Double,
        longitude : Double
    ):String{
        return reverseGeocoderService.coordinateToAddress(
            latitude,
            longitude
        )
    }
}