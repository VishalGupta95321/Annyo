package com.example.call_data.util

import com.annyo.network.di.DefaultDispatcher
import com.example.call_data.util.RtcConstants.VIDEO_FRAME_RATE
import com.example.call_data.util.RtcConstants.VIDEO_HEIGHT
import com.example.call_data.util.RtcConstants.VIDEO_WIDTH
import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.webrtc.AudioTrack
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraVideoCapturer
import org.webrtc.EglBase
import org.webrtc.MediaStream
import org.webrtc.SurfaceTextureHelper
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoSource
import org.webrtc.VideoTrack
import javax.inject.Inject
import kotlin.coroutines.resume

class RtcMediaCaptureClientImpl @Inject constructor(
    private val eglBaseContext: EglBase.Context,
    private val videoSource: VideoSource,
    private val videoTrack: VideoTrack,
    private val audioTrack: AudioTrack,
    private val localMediaStream: MediaStream,
    private val cameraEnumerator: Camera2Enumerator,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher
): RtcMediaCaptureClient {

    private val videoCapturer by lazy { getCapturer() }

    // todo( i think its not good that im taking surface renderer in here or in domain module cuz its containing context)
    override suspend fun startCapturing():MediaStream {

        withContext(defaultDispatcher){  // todo dont know if use default dispatcher but its working anyways
            videoCapturer?.startCapture(
                VIDEO_HEIGHT, VIDEO_WIDTH, VIDEO_FRAME_RATE
            )
        }

        return localMediaStream
    }

    override fun stopCapturing():GetBackBasic {
        try {
            videoCapturer?.stopCapture()
        }catch (e: InterruptedException){
            return GetBack.Error()  /// make an error class and provide specific error
        }catch (e:Exception){
            return GetBack.Error()
        }
        return GetBack.Success()
    }

    override fun enableOrDisableAudio() {
        if (audioTrack.enabled()){
            audioTrack.setEnabled(false)
        } else audioTrack.setEnabled(true)
    }

    override fun enableOrDisableVideo() {
       if (videoTrack.enabled()){
           videoTrack.setEnabled(false)
       } else videoTrack.setEnabled(true)
    }

    override suspend fun switchCamera():GetBackBasic =
        withContext(defaultDispatcher){
            suspendCancellableCoroutine{ continuation ->
                videoCapturer?.switchCamera(object : CameraVideoCapturer.CameraSwitchHandler {
                    override fun onCameraSwitchDone(result: Boolean) {
                        continuation.resume(GetBack.Success())
                    }

                    override fun onCameraSwitchError(error: String?) {
                        continuation.resume(GetBack.Error())
                    } })
                continuation.invokeOnCancellation { }
        }
    }

    private fun getCapturer(): CameraVideoCapturer? {
        cameraEnumerator.deviceNames.forEach {
            if (cameraEnumerator.isFrontFacing(it)) {
                return cameraEnumerator.createCapturer(
                    it, null
                )
            }
        }
        return null
    }

    override fun initVideoCapturer(surfaceViewRenderer: SurfaceViewRenderer) {
        val surfaceTextureHelper =
            SurfaceTextureHelper.create(
                "surfaceViewRendererThread", eglBaseContext
            )
        videoCapturer?.initialize(
            surfaceTextureHelper,
            surfaceViewRenderer.context,
            videoSource.capturerObserver
        )
    }

    override fun initSurfaceViewRenderer(
        surfaceViewRenderer: SurfaceViewRenderer
    ) {
        surfaceViewRenderer.apply {   // handles all the things related to how the video appear in the renderer or the screen
            setMirror(true)
            init(
                eglBaseContext,
                null
            )
        }
    }

    override fun addVideoSink(localSurfaceViewRenderer: SurfaceViewRenderer) {
        videoTrack.addSink(localSurfaceViewRenderer)
    }
}