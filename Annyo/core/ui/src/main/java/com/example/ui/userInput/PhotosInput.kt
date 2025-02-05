package com.example.ui.userInput

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.PhotoCard
import com.example.ui.R



@Composable
fun PhotoInput(
    photo1: Uri? = null,
    photo2: Uri? = null,
    photo3: Uri? = null,
    photo4: Uri? = null,
    photo5: Uri? = null,
    photo6: Uri? = null,
    isOnboarding:Boolean = true,
    onGetImageUri: (photoCount: Int, uri: Uri) -> Unit,
) {

    var selectedImage by remember {
        mutableStateOf<Int?>(null)
    }

    val uploadPhotoFromGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { Uri ->
        Uri?.let { uri -> selectedImage?.let { onGetImageUri(it, uri) } }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column {
            PhotoCard(model = photo1?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 1
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }

            PhotoCard(model =  photo2?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 2
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }
        }
        Column(
            modifier = Modifier.graphicsLayer {
                translationY = if (isOnboarding)
                    size.height * 0.3f
                else 0f

            }) {

            PhotoCard(model =  photo3?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 3
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }

            PhotoCard(model =  photo4?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 4
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }
        }
        Column(
            modifier = Modifier.graphicsLayer {
                translationY = if (isOnboarding)
                    size.height * 0.55f
                else 0f
            }) {

            PhotoCard(model =  photo5?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 5
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }

            PhotoCard(model =  photo6?:R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) {
                selectedImage = 6
                uploadPhotoFromGallery.launch(PickVisualMediaRequest(ImageOnly))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pr20() {
    //PhotosScreen()
}


@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=portrait"
)

@Composable
fun Pr21() {
   // PhotosScreen()
}

