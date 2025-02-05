package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.util.RtcMediaCaptureClient
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

class SetUpSurfaceViewRendererForVideoCapturingUseCase @Inject constructor(
    private val rtcMediaCaptureClient: RtcMediaCaptureClient
) {
    operator fun invoke(surfaceViewRenderer: SurfaceViewRenderer) {  /// fixme: i know i am taking  android.views in data and domain and it works fine maybe fix it later // i should have created  a separate webrtc module
        rtcMediaCaptureClient.initVideoCapturer(surfaceViewRenderer)
        rtcMediaCaptureClient.initSurfaceViewRenderer(surfaceViewRenderer)
        rtcMediaCaptureClient.addVideoSink(surfaceViewRenderer)
    }
}