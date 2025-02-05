package com.example.onboarding_presentation.secondary_screens.name

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.NameAddScreen

@Composable
fun NameScreen(
    viewModel: NameViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val nameState by viewModel
        .nameState
        .collectAsState()

    val aboutUpdateState by viewModel
        .nameUpdateState
        .collectAsState()

    when (aboutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    NameAddScreen(
        name = nameState.name.name,
        onValueChange =  { value ->
            println(value)
            viewModel.updateName(
                nameState.name
                    .copy(value)
            )
        },
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

//BaseInputScreen(
//isNextButtonEnabled = nameState.name.name.isNotEmpty(),
//isBackButtonEnabled = false,
//label1 = "Ok Cool,\nWhat about your name ?",
//inputContent = {
//    StandardTextField(
//        placeholderText = "name",
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(0.9f),
//        singleLine = true,
//        value = nameState.name.name,
//        onValueChange = { value ->
//            println(value)
//            viewModel.updateName(
//                nameState.name
//                    .copy(value)
//            )
//        },
//    )
//},
//) { onNextCLick() }