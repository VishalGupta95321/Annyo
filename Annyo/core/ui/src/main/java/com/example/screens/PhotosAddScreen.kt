package com.example.screens

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import com.example.ui.BaseInputScreen
import com.example.ui.userInput.PhotoInput

@Composable
fun PhotosAddScreen(
    photo1Uri: String? = null,
    photo2Uri: String? = null,
    photo3Uri: String? = null,
    photo4Uri: String? = null,
    photo5Uri: String? = null,
    photo6Uri: String? = null,
    onGetImageUri: (photoCount:Int,Uri: Uri) -> Unit,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    BaseInputScreen(
        inputContent = {
            PhotoInput(
                photo1 = photo1Uri?.toUri(),
                photo2 = photo2Uri?.toUri(),
                photo3 = photo3Uri?.toUri(),
                photo4 = photo4Uri?.toUri(),
                photo5 = photo5Uri?.toUri(),
                photo6 = photo6Uri?.toUri(),
                onGetImageUri = onGetImageUri
            )
        },
        onBackClick = onBackClick
    ) { onDoneClick() }

    BackHandler { onBackClick() }
    BackHandler{onBackClick()}
}