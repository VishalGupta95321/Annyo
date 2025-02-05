package com.example.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.ui.userInput.LocationInputScreen
import com.example.util.CurrentLocationProvider
import com.example.util.LocationResponse

@Composable
fun Activity.LocationAddScreen(
    address: String,
    onAddressChange: (String) -> Unit,
    onGetLocation: (LocationResponse) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    LocationInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        locationProvider = CurrentLocationProvider(this),
        address = address,
        onAddressChange = onAddressChange,
        onGetLocation = onGetLocation,
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}