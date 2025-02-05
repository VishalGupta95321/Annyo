package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.util.RtcMediaCaptureClient
import javax.inject.Inject

class ToggleVideoUseCase @Inject constructor(
    private val rtcMediaCaptureClient: RtcMediaCaptureClient
) {
    operator fun invoke() {
        rtcMediaCaptureClient.enableOrDisableVideo()
    }
}