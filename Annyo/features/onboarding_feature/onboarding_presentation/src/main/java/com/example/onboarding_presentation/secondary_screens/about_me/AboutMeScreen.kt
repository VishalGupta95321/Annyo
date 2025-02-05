package com.example.onboarding_presentation.secondary_screens.about_me

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.AboutMeAddScreen


@Composable
fun AboutMeScreen(
    viewModel: AboutMeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val aboutMeState by viewModel
        .aboutMeState
        .collectAsState()

    val aboutUpdateState by viewModel
        .aboutMeUpdateState
        .collectAsState()

    when (aboutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    AboutMeAddScreen(
        text = aboutMeState.aboutMe.aboutYou,
        onValueChange = { value ->
            viewModel.updateAboutMe(
                aboutMeState.aboutMe
                    .copy(value)
            )
        },
        onBackClick = onBackClick
    ){onNextCLick()}
    BackHandler { onBackClick() }
}


//
//BaseInputScreen(
//label1 = "Let others Know you.",
//label2 = "Tell people about you\n" +
//"May be your personality , interests",
//inputContent = {
//    StandardTextField(
//        placeholderText = "About you",
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(0.9f),
//        singleLine = false,
//        maxLines = 5,
//        value = ,
//        onValueChange =
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }