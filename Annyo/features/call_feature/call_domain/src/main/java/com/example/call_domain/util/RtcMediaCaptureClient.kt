package com.example.call_domain.util

import com.example.utils.GetBackBasic
import org.webrtc.MediaStream
import org.webrtc.SurfaceViewRenderer

interface RtcMediaCaptureClient {
    suspend fun startCapturing():MediaStream
    fun initVideoCapturer(surfaceViewRenderer: SurfaceViewRenderer)
    fun initSurfaceViewRenderer(surfaceViewRenderer: SurfaceViewRenderer)
    fun addVideoSink(localSurfaceViewRenderer: SurfaceViewRenderer)
    fun stopCapturing(): GetBackBasic
    fun enableOrDisableAudio()
    fun enableOrDisableVideo()
    suspend fun switchCamera(): GetBackBasic
}
/// todo make error class and return