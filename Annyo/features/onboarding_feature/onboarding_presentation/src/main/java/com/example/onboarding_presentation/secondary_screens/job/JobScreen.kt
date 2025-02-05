package com.example.onboarding_presentation.secondary_screens.job

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.JobAddScreen

@Composable
fun JobScreen(
    viewModel: JobViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val jobState by viewModel
        .jobState
        .collectAsState()

    val aboutUpdateState by viewModel
        .jobUpdateState
        .collectAsState()

    when (aboutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    JobAddScreen(
        jobTitle = jobState.job.jobTitle,
        onValueChange = { value ->
            viewModel.updateJob(
                jobState.job
                    .copy(value)
            )
        },
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}


//BaseInputScreen(
//label1 = "Ok Cool,\nWhat about your job ?",
//inputContent = {
//    StandardTextField(
//        placeholderText = "job title/where",
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(0.9f),
//        singleLine = false,
//        maxLines = 5,
//        value = jobState.job.jobTitle,
//        onValueChange = { value ->
//            viewModel.updateJob(
//                jobState.job
//                    .copy(value)
//            )
//        },
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }