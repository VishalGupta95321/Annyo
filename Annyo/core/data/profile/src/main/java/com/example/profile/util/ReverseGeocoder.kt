package com.example.profile.util

import android.location.Geocoder
import android.os.Build
import java.io.IOException
import javax.inject.Inject

class ReverseGeocoder @Inject constructor(
    private val geoCoder: Geocoder
) : GeocoderService {

    override suspend fun coordinateToAddress(
        lat: Double,
        lng: Double
    ): String {

        var address = ""

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geoCoder.getFromLocation(
                    lat, lng, 1
                ) { addresses ->
                    addresses.isNotEmpty().let {
                        address = addresses.first().adminArea + "," + addresses.first().countryName
                    }
                }

            } else {
                val addresses = geoCoder
                    .getFromLocation(
                        lat, lng, 1
                    )
                addresses?.let {
                    address = it.first().adminArea + "," + it.first().countryName
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return address
    }
}