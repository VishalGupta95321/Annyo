package com.example.call_presentation.views

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.webrtc.SurfaceViewRenderer

@Composable
fun RtcSurfaceViewRenderer(
    surface: (SurfaceViewRenderer)->Unit,
    modifier: Modifier = Modifier
) {
    Column(Modifier.fillMaxSize()) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val surfaceViewRenderer =  SurfaceViewRenderer(context).apply{
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                surface(surfaceViewRenderer)
                surfaceViewRenderer
            })
    }
}

//.combinedClickable {  } // todo new modifier i think at least for me