package com.app.ggg

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

data class item(
    val name: String,
    @DrawableRes val icon: Int
)


@Composable
fun casac(
    v : test2 = hiltViewModel()

) {
    val act = LocalContext.current as Activity
   // v.auth(act)

    val chooseImageFromGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()) { Uri ->
       v.storage(Uri!!)
    }

    Button(onClick = {chooseImageFromGallery.launch("image/*") }) {

    }
}

