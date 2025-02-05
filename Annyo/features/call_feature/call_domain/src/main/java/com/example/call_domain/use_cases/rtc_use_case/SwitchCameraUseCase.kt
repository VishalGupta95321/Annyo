package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.utils.GetBackBasic
import javax.inject.Inject

class SwitchCameraUseCase @Inject constructor(
    private val rtcMediaCaptureClient: RtcMediaCaptureClient
) {
    suspend operator fun invoke():GetBackBasic {
        return rtcMediaCaptureClient.switchCamera()
    }
}
