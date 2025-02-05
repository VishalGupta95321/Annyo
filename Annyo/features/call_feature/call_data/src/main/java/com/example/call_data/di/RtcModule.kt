package com.example.call_data.di

import android.content.Context
import com.example.call_data.util.IceServers
import com.example.call_data.util.RtcConstants
import com.example.call_data.util.RtcConstants.AUDIO_TRACK_ID
import com.example.call_data.util.RtcConstants.VIDEO_TRACK_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.webrtc.AudioSource
import org.webrtc.AudioTrack
import org.webrtc.Camera2Enumerator
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.VideoSource
import org.webrtc.VideoTrack
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RtcModule {

    @Provides
    @Singleton
    fun provideApplicationContext(            /// TODO(should be in core or app module )
        @ApplicationContext context: Context
    ): Context = context

    @Provides
    @Singleton
    fun provideEglBaseContext(): EglBase.Context = EglBase
        .create().eglBaseContext



    @Provides
    @Singleton
    fun provideIceServers(): PeerConnection.IceServer =
        PeerConnection.IceServer.builder(
            IceServers.DefaultGoogleStunServer.uri
        ).createIceServer()
    //            PeerConnection.IceServer.builder(
//                IceServers.CoturnStunTurnServer.uri).createIceServer()

    @Provides
    @Singleton
    fun provideEncoderFactory(
        eglBaseContext: EglBase.Context
    ): DefaultVideoEncoderFactory =
        DefaultVideoEncoderFactory(
            eglBaseContext,
            true,
            true
        )

    @Provides
    @Singleton
    fun provideDecoderFactory(
        eglBaseContext: EglBase.Context
    ): DefaultVideoDecoderFactory =
        DefaultVideoDecoderFactory(eglBaseContext)

    @Provides
    @Singleton
    fun providePeerConnectionFactory(
        @ApplicationContext context: Context,
        encoderFactory: DefaultVideoEncoderFactory,
        decoderFactory: DefaultVideoDecoderFactory
    ): PeerConnectionFactory {
        PeerConnectionFactory.initialize(
            PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .createInitializationOptions()
        )

        return PeerConnectionFactory.builder()        //// I think its better to provide only peer connection factory from hilt but I am providing because i split the rtc class into two for SRP principle
            .setVideoEncoderFactory(encoderFactory)
            .setVideoDecoderFactory(decoderFactory)
            .createPeerConnectionFactory()
    }

    @Provides
    @Singleton
    fun provideCameraEnumerator(
        context:Context
    ) = Camera2Enumerator(context)

    @Provides
    @Singleton
    fun provideVideoSource(
        peerConnectionFactory: PeerConnectionFactory
    ): VideoSource = peerConnectionFactory.createVideoSource(false)

    @Provides
    @Singleton
    fun provideAudioSource(
        peerConnectionFactory: PeerConnectionFactory
    ): AudioSource = peerConnectionFactory.createAudioSource(MediaConstraints())

    @Provides
    @Singleton
    fun provideVideoTrack(
       peerConnectionFactory: PeerConnectionFactory,
       videoSource: VideoSource
    ): VideoTrack = peerConnectionFactory.createVideoTrack(VIDEO_TRACK_ID,videoSource)

    @Provides
    @Singleton
    fun provideAudioTrack(
        peerConnectionFactory: PeerConnectionFactory,
        audioSource: AudioSource
    ): AudioTrack = peerConnectionFactory.createAudioTrack(AUDIO_TRACK_ID,audioSource)

    @Provides
    @Singleton
    fun provideLocalMediaStream(
        peerConnectionFactory: PeerConnectionFactory,
        audioTrack: AudioTrack,
        videoTrack: VideoTrack,
    ): MediaStream = peerConnectionFactory.createLocalMediaStream(RtcConstants.MEDIA_STREAM_ID)
        .apply {
            addTrack(audioTrack)
            addTrack(videoTrack)
        }
}