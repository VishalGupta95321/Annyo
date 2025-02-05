package com.example.util

import android.app.Activity
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

class CurrentLocationProvider(
    private val activity: Activity
) : LocationProvider {

    override fun getDefaultLocationRequest(): LocationRequest {
        return LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            500
        )
            .setWaitForAccurateLocation(true)
            .build()
    }

    override suspend fun isLocationSettingsSatisfied(
        locationRequest: LocationRequest
    ): LocationSettingsResponse {

        val settingClient = LocationServices
            .getSettingsClient(activity)

        val locationSettingRequest =
            LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
                .build()

        return try {
            val response = settingClient
                .checkLocationSettings(
                    locationSettingRequest
                ).await()

            LocationSettingsResponse.Satisfied(
                response.locationSettingsStates
            )
        } catch (e: ResolvableApiException) {
            e.printStackTrace()
            LocationSettingsResponse.NotSatisfied(
                IntentSenderRequest.Builder(
                    e.resolution
                ).build()
            )
        }
    }


    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    override suspend fun getLocation(): LocationResponse? {
        return try {
            val location = LocationServices
                .getFusedLocationProviderClient(activity)
                .getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                )  ///  this fun will never return  null .
                .await()
            println(location.latitude.toString() +"  "+location.longitude)
            LocationResponse(location.latitude, location.longitude)
        }
        catch (e : SecurityException){ // "SecurityException" if permission is not granted
            null
        }catch (e : ApiException){   ///  if location service api is not available on the device .....
            null
        }catch (e : Exception){
            null         //// todo send some message if we dont get location
        }   /// todo because  getting this execption sometimes "java.lang.NullPointerException: Attempt to invoke virtual method 'double android.location.Location.getLatitude()' on a null object reference"
    }
}