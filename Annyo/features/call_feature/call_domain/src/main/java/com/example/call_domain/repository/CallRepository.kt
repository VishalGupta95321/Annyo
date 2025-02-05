package com.example.call_domain.repository

import com.example.call_domain.util.CallMode
import com.example.utils.GetBackBasic
import kotlinx.coroutines.flow.Flow
import org.webrtc.MediaStream
import org.webrtc.MediaStreamTrack

interface CallRepository {
    suspend fun beginCall(
        callType: CallMode,
        toId: String,
        localMediaStream: MediaStream,
        onConnectionFailed: () -> Unit
    ):Flow<MediaStreamTrack?> /// TODO(Change GetBackBasic to GetBAck<SomeType>)

    //fun beginVideoCall():GetBackBasic
    suspend fun hangUpCall (toId: String)
    suspend fun toggleAudioVideo(
        localMediaStream: MediaStream,
    ):GetBackBasic
    //fun switchCamera()
    //fun muteMicrophone()
    //fun pauseVideo()
    fun switchToSpeaker():GetBackBasic
  //  fun toggleFullScreen()
}