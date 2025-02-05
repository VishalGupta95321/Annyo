package com.example.onboarding_presentation.secondary_screens.location

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.screens.LocationAddScreen

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {
    val activity = LocalContext.current as Activity

    val locationUiState by viewModel.locationState.collectAsState()

    activity.LocationAddScreen(
        address = locationUiState.location.address,
        onAddressChange = {
            viewModel.updateLocation(
                locationUiState.location.copy(
                    address = it
                )
            )
        },
        onGetLocation = {
            viewModel.updateLocation(
                locationUiState.location.copy(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            )
        },
        onBackClick = onBackClick
    ) { onNextCLick() }

    BackHandler { onBackClick() }
}
//
//LocationInputScreen(
//isNextButtonEnabled = locationUiState.location.address.isNotEmpty(),
//locationProvider = CurrentLocationProvider(activity),
//address = locationUiState.location.address,
//onAddressChange = {
//    viewModel.updateLocation(
//        locationUiState.location.copy(
//            address = it
//        )
//    )
//},
//onGetLocation = {
//    viewModel.updateLocation(
//        locationUiState.location.copy(
//            latitude = it.latitude,
//            longitude = it.longitude
//        )
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }