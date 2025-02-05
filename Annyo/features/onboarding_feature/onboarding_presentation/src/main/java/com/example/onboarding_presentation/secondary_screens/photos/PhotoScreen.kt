package com.example.onboarding_presentation.secondary_screens.photos

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Photo
import com.example.screens.PhotosAddScreen

@Composable
fun PhotosScreen(
    viewModel: PhotoViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val photoState by viewModel.photoState.collectAsState()


    val photoUpdateState by viewModel
        .photoUpdateState
        .collectAsState()

    when (photoUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {
            println("Uploaded dailed ")
        }
        ProfileUpdateState.Updated -> {
            println("Uploaded firebase ")
        }
        ProfileUpdateState.Updating -> {
            println("Updating ")
        }
    }

    PhotosAddScreen(
        photo1Uri = photoState.photo1?.photoUrl,
        photo2Uri = photoState.photo2?.photoUrl,
        photo3Uri = photoState.photo3?.photoUrl,
        photo4Uri = photoState.photo4?.photoUrl,
        photo5Uri = photoState.photo5?.photoUrl,
        photo6Uri = photoState.photo6?.photoUrl,
        onGetImageUri = { photoCount, uri ->
            viewModel.updatePhoto(
                Photo(uri.toString(), photoCount)
            )
        },
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

// todo check the screen height because the design layout of photos exceeding  the screen in small screen devices

//BaseInputScreen(
//isNextButtonEnabled = photoState.run {
//    photo1!=null||photo2!=null||photo3!=null||photo4!=null||photo5!=null||photo6!=null
//},
//label1 = "Nice !! \nLet's Add Some Photos.",
//label2 = "Having more pics will increase your chances of being matched.",
//inputContent = {
//    PhotoInput(
//        photo1 = photoState.photo1?.photoUrl?.toUri(),
//        photo2 = photoState.photo2?.photoUrl?.toUri(),
//        photo3 = photoState.photo3?.photoUrl?.toUri(),
//        photo4 = photoState.photo4?.photoUrl?.toUri(),
//        photo5 = photoState.photo5?.photoUrl?.toUri(),
//        photo6 = photoState.photo6?.photoUrl?.toUri(),
//        onGetImageUri = { photoCount, uri ->
//            viewModel.updatePhoto(
//                Photo(uri.toString(), photoCount)
//            )
//        }
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }