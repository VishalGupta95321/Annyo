package com.example.ui.userInput

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.example.PermissionDialogBox
import com.example.ui.BaseInputScreen
import com.example.ui.StandardButton
import com.example.ui.StandardTextField
import com.example.util.*
import kotlinx.coroutines.launch

@Composable
fun LocationInputScreen(
    isNextButtonEnabled: Boolean = true,
    locationProvider: LocationProvider,
    address: String,
    onAddressChange: (String) -> Unit,
    onGetLocation: (LocationResponse) -> Unit,
    onBackClick : () -> Unit,
    onNextClick : () -> Unit
) {
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    val locationRequest = locationProvider.getDefaultLocationRequest()

    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    checkSelfPermission(activity, permissions.first())
    val locationSettingResolutionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    locationProvider
                        .getLocation()?.also(onGetLocation)
                }
            }
        }
    )

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { Permissions ->

            isDialogVisible = Permissions[permissions.first()] == false

            if (Permissions[permissions.first()] == true) {
                scope.launch {
                    when (val response = locationProvider
                        .isLocationSettingsSatisfied(locationRequest)
                    ) {
                        is LocationSettingsResponse.Satisfied -> {
                            locationProvider
                                .getLocation()?.also(onGetLocation)
                        }
                        is LocationSettingsResponse.NotSatisfied -> {
                            locationSettingResolutionRequest.launch(
                                response.intentSenderRequest
                            )
                        }
                    }
                }
            }
        }
    )

    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        label1 = "Let me know where are you from.",
        label2 = "We Use Your Location to Recommend you nearby matches." +
                "Your exact location will not be shared.",
        inputContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                StandardTextField(
                    enabled = false , // todo until i use google map Places api to search locations
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 25.dp)
                        .fillMaxWidth(0.9f),
                    value = address,
                    onValueChange = onAddressChange,
                    placeholderText = "Enter Your location"
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Or",
                    style = MaterialTheme.typography.titleLarge
                        .copy(color = MaterialTheme.colorScheme.primary)
                )
                StandardButton(
                    onClick = {
                        locationPermissionRequest.launch(permissions)
                    }) {
                    Text(
                        text = "Get Current Location",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        onBackClick = onBackClick
    ) { onNextClick()}

    AnimatedVisibility(
        visible = isDialogVisible,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        PermissionDialogBox(
            dialogContent = LocationDialogContent(),
            isPermissionPermanentlyDeclined =
            !shouldShowRequestPermissionRationale(
                activity, permissions.first()
            ),
            onGoToSettingsClick = {
                activity.openAppSettings()
                isDialogVisible = !isDialogVisible
            },
            onAllowAccessClick = {
                locationPermissionRequest
                    .launch(permissions)
                isDialogVisible = !isDialogVisible
            },
            onNotNowClick = {
                isDialogVisible = !isDialogVisible
            }
        ) { isDialogVisible = !isDialogVisible }
    }
}
