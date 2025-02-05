package com.example.profile.util

import com.example.profile.model.Location

interface GeocoderService {
    suspend fun coordinateToAddress(
        lat : Double,
        lng : Double
    ):String
}