package com.example.onboarding_presentation.secondary_screens.zodiac_sign

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.ZodiacSignAddScreen

@Composable
fun ZodiacSignScreen(
    viewModel: ZodiacSignViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val zodiacSignState by viewModel
        .zodiacSignState
        .collectAsState()

    val zodiacSignUpdateState by viewModel
        .zodiacSignUpdateState
        .collectAsState()

    when (zodiacSignUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    ZodiacSignAddScreen(
        selectedOption = zodiacSignState.zodiacSign.sign,
        onSelect =  {
            viewModel.updateZodiacSign(
                zodiacSignState.zodiacSign
                    .copy(it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}

    BackHandler { onBackClick() }
}

// BaseInputScreen(
//isNextButtonEnabled = zodiacSignState.zodiacSign.sign.isNotEmpty(),
//label1 = "Whats Your Zodiac Sign ?",
//inputContent = {
//    ChipsGroup(
//        choices = ZodiacSignChoices() ,
//        selectedOptionOrOptions = listOf(zodiacSignState.zodiacSign.sign),
//        onSelect = {
//            viewModel.updateZodiacSign(
//                zodiacSignState.zodiacSign
//                    .copy(it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }