package com.example.onboarding_presentation.secondary_screens.height

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.HeightAddScreen

@Composable
fun HeightScreen(
    viewModel: HeightViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val heightState by viewModel
        .heightState
        .collectAsState()

    val aboutUpdateState by viewModel
        .heightUpdateState
        .collectAsState()

    when (aboutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    HeightAddScreen(
        heightInFeet = heightState.height.heightInFeet,
        onSelect = { value ->
            viewModel.updateHeight(
                heightState.height
                    .copy(value)
            )
        },
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

//BaseInputScreen(
//label1 = "Ok Cool,\nWhat about your height ?",
//inputContent = {
//    Column(
//        Modifier
//            .fillMaxWidth(0.95f)
//            .padding(top = 20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        DropDownList(
//            modifier = Modifier.width(150.dp),
//            title = if (heightState.height.heightInFeet=="")
//                "Height"
//            else heightState.height.heightInFeet,
//            options = HeightOptions() ,
//            listHeight = 500.dp,
//            onSelect = { value ->
//                viewModel.updateHeight(
//                    heightState.height
//                        .copy(value)
//                )
//            }
//        )
//    }
//},
//onBackClick = onBackClick
//) { onNextCLick() }