package com.example.util

import android.location.Location
import androidx.activity.result.IntentSenderRequest
import com.example.utils.GetBack
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsStates

interface LocationProvider {  //// "CurrentLocationFinder" , "LastLocationFinder"

    fun getDefaultLocationRequest(): LocationRequest

    suspend fun isLocationSettingsSatisfied(
        locationRequest: LocationRequest
    ): LocationSettingsResponse

    suspend fun getLocation(): LocationResponse?

}

data class LocationResponse(
    val latitude : Double,
    val longitude : Double
)

sealed interface LocationSettingsResponse {

    data class Satisfied(
        val locationSettingStates: LocationSettingsStates?
    ) : LocationSettingsResponse

    data class NotSatisfied(
        val intentSenderRequest: IntentSenderRequest
    ) : LocationSettingsResponse

}
