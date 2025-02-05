package com.app.annyo

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.ImageDecoderDecoder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnnyoApplication:Application(),ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(ImageDecoderDecoder.Factory()) // TODO()
            }.build()
    }
}