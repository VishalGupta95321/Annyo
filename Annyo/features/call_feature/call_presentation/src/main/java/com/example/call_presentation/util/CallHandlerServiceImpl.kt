package com.example.call_presentation.util

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.call_domain.util.CallHandlerService
import com.example.call_domain.util.OfferType
import dagger.hilt.android.AndroidEntryPoint
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@AndroidEntryPoint /// todo check if we can annotate 2 classes wit @AndroidEntryPoint
class CallHandlerServiceImpl @Inject constructor() : CallHandlerService, Service() {

    //    @Inject
//    lateinit var rtcUseCases: RtcUseCases
//
//    @Inject
//    lateinit var signallingUseCases: SignallingUseCases
//
//    @Inject
//    @SupervisorMainImmediateScope
//    lateinit var scope: CoroutineScope
//
//    @Inject
//    lateinit var notifier: CallNotifier
//
//    private val allowRebind: Boolean = false
//    private val startMode: Int = START_NOT_STICKY
//    private var binder: IBinder? = null
//
//    //// jobs
//    private lateinit var peerConnectionObserverJob: Job
//    private lateinit var signallingEventObserverJob: Job
//    private lateinit var signallingSocketConnectionEventObserverJob: Job
//    private lateinit var ongoingCallJob: Job
//
//    /// boolean
//    private var isUserOnAnotherCall: Boolean = false
//    private var callMode: CallMode = CallMode.AudioCall
//
//    //// state
//    //// only one state will be observed CallState or Call ongoing Call State
//    private lateinit var callConnectionState: MutableSharedFlow<CallConnectionState>
//
//
//    /// test var
//    val individual = Individual(
//        "Angella",
//        IconCompat.createWithContentUri(Uri.parse("res/drawable/answer_call.xml"))
//    )
//    var state = OngoingCallNotificationState.CallConnecting
//
//    override fun onCreate() {
//        super.onCreate()
//        ongoingCallJob = Job()
//        ongoingCallJob.cancel()
//        observePeerConnectionEvents()
//        observeSignallingEvents()
//        observeSignallingSocketEvents()
//
//    }
//
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        intent?.extras?.let {
//
//            if (isUserOnAnotherCall){
//                /// todo
//                //// signal that user is on another call
//                //// send callCallConnection State as user is busy on another call
//                //// beep for few seconds and play audio "User is busy on another call"
//                /// close this service
//            }
//
//            it.getString(INTENT_EXTRAS_CALL_MODE_KEY).let { value ->
//                callMode = if (value == INTENT_EXTRAS_CALL_MODE_AUDIO) {
//                    CallMode.AudioCall
//                } else {
//                    CallMode.VideoCall
//                }
//            }
//
//            val callType = it.getString(INTENT_EXTRAS_CALL_TYPE_KEY)
//            if (callType == INTENT_EXTRAS_CALL_TYPE_INCOMING)
//                startForegroundForIncomingCall(individual)
//            else
//                startForegroundForOngoingCall(
//                    individual,
//                    state
//                )
//        }
//
//
//        return startMode
//    }
//
//    private fun startForegroundForIncomingCall(
//        caller: Individual,
//    ){
//        startForeground(
//            NotificationsIds.CALL_NOTIFICATION_ID,
//            notifier.createIncomingCallNotification(
//                caller = caller,
//                isVideoCall = callMode==CallMode.VideoCall
//            ),
//        )
//    }
//
//    //// check service overview there are a lot of restrictions to open a service from background like having all the permissions required , a service using camera cannot be opened from the background etc.
//    private fun startForegroundForOngoingCall(
//        callee: Individual,
//        state: OngoingCallNotificationState
//    ){
//        startForeground(
//            NotificationsIds.CALL_NOTIFICATION_ID, // cant be 0
//            notifier.createOngoingCallNotification(
//                callee = callee,
//                isVideoCall = callMode==CallMode.VideoCall,
//                state
//            )
//        )
//    }
//
//
//
//    private fun observePeerConnectionEvents() {
//        peerConnectionObserverJob = scope.launch {
//            rtcUseCases.createPeerConnection().collect {
//                when (it) {
//                    is PeerConnectionObserverEvent.OnAddTrack -> {
//                        /// track from the peer
//                        callConnectionState.tryEmit(
//                            CallConnectionState.Ongoing(
//                                duration = "00:00:00",
//                                mediaStream = it.mediaStreams
//                            )
//                        )
//                    }
//
//                    is PeerConnectionObserverEvent.OnIceCandidate -> {
//                        rtcUseCases.handleLocalIceCandidate(it.iceCandidate).let { result ->
//                            when (result) {
//                                is GetBack.Error -> callConnectionState.tryEmit(CallConnectionState.Failed)
//                                is GetBack.Success -> {}
//                            }
//                        }
//                    }
//
//                    is PeerConnectionObserverEvent.OnIceConnectionChange -> {
//                        /// ice restart
//                    }
//
//                    is PeerConnectionObserverEvent.OnIceConnectionReceivingChange -> {
//                        // ice restart
//                    }
//
//                    PeerConnectionObserverEvent.OnRenegotiationNeeded -> {}
//                    is PeerConnectionObserverEvent.OnSignalingChange -> {}
//                    PeerConnectionObserverEvent.PeerConnectionFailed -> {
//                        callConnectionState.tryEmit(CallConnectionState.Failed)
//                    }
//
//                    else -> {}
//                }
//            }
//        }
//    }
//
//    private fun observeSignallingEvents() {
//        signallingEventObserverJob = scope.launch {
//            signallingUseCases.observeSignallingEvent().collect {
//                when (it) {
//                    is SignallingEvents.OnAnswer -> {
//                        rtcUseCases.handleIncomingAnswer(SignallingEvents.OnAnswer(it.sessionDescription)) // fixme dont know why i used signalling event in the paarmeter
//                            .let { result ->
//                                when (result) {
//                                    is GetBack.Error -> callConnectionState.tryEmit(
//                                        CallConnectionState.Failed
//                                    )
//                                    is GetBack.Success -> {}
//                                } }
//                    }
//
//                    SignallingEvents.OnHangUp -> {
//                        rtcUseCases.handleIncomingCallHangUpRequest()
//                    }
//
//                    is SignallingEvents.OnIceCandidate -> {
//                        rtcUseCases.handleRemoteIceCandidate(it.candidate)
//                    }
//
//                    is SignallingEvents.OnInitialOffer -> {
////                        rtcUseCases.handleIncomingCallOffer(
////                            receivedOfferType = if (callMode == CallMode.AudioCall) {
////                                OfferType.AudioCall
////                            } else OfferType.VideoCall,
////                            offer = SignallingEvents.OnInitialOffer(it.sessionDescription)
////                        ).let { result ->
////                            when (result) {
////                                is GetBack.Error -> callConnectionState.tryEmit(CallConnectionState.Failed)
////                                is GetBack.Success -> {}
////                            }
//                        }
//                    }
//
//                    is SignallingEvents.OnUpdatedOffer -> {
//                        /// Todo
//                    }
//
//                    SignallingEvents.OnAnotherCall -> {
//                        // todo
//                        /// show state as  OnAnotherCall, beep for a few seconds and then close the call
//                    }
//
//                    SignallingEvents.OnCallDataPayloadDelivered -> {}
//                    is SignallingEvents.OnCallFailed -> {}
//                    is SignallingEvents.OnNoSuchCallSession -> {}
//                }
//            }
//        }
//    }
//
//    private fun observeSignallingSocketEvents() {
//        signallingSocketConnectionEventObserverJob = scope.launch {
//            signallingUseCases.observeSignallingSocketConnectionEvent().collect {
//                when (it) {
//                    is SignallingSocketEvents.OnConnectionClosed ->{
//                        //println("Shutdown code"+it.shutdownCode+"  reason  "+it.shutdownReason+"   closwed")
//                    }
//                    SignallingSocketEvents.OnConnectionClosing -> {}
//                    is SignallingSocketEvents.OnConnectionFailed -> {
//                        println(it.throwable.toString()+"Faileddddddd")
//                    }
//                    SignallingSocketEvents.OnConnectionOpened ->{
//                        println("opennnnn")
//                    }
//                    SignallingSocketEvents.OnMessageReceived -> {}
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    override fun toggleMic() {
//        rtcUseCases.toggleMic()
//    }
//
//    override fun toggleVideo() {
//        rtcUseCases.toggleVideo()
//    }
//
//    override fun toggleAudioOutput() {
//        rtcUseCases.toggleAudioOutput()
//    }
//
//    override suspend fun handleLocalIceCandidate(
//        localIceCandidate: IceCandidate
//    ) {
//        rtcUseCases.handleLocalIceCandidate(localIceCandidate)  // handle all the returning result from the functions
//    }
//
//    override suspend fun handleRemoteIceCandidate(
//        remoteIceCandidate: IceCandidate
//    ) {
//        rtcUseCases.handleRemoteIceCandidate(remoteIceCandidate)
//    }
//
//
//    override fun setUpSurfaceViewRendererForVideoCapturing(
//        localSurfaceRenderer: SurfaceViewRenderer
//    ) {
//        rtcUseCases.setUpSurfaceViewRendererForVideoCapturing(localSurfaceRenderer)
//    }
//
//    override suspend fun switchCamera() {
//        rtcUseCases.switchCamera().let { result ->
//            when (result) {
//                is GetBack.Error -> callConnectionState.tryEmit(CallConnectionState.Failed)
//                is GetBack.Success -> {}
//
//            }
//        }
//    }
//
//    inner class CallHandlerServiceBinder : Binder() {
//        fun getService(): CallHandlerService = this@CallHandlerServiceImpl
//    }
//
//    override fun onBind(p0: Intent?): IBinder? {
//        binder = CallHandlerServiceBinder()
//        return binder
//    }
//
//    override fun onUnbind(intent: Intent?): Boolean {
//        return allowRebind
//    }
//
//    override fun onRebind(intent: Intent?) {
//        /// todo
//    }
//
//
//    override fun reconnectIceConnection() {
//        // TODO()
//    }
//    override fun switchFromAudioToVideoCallOffer() {
//        rtcUseCases.switchFromAudioToVideoCallOffer()
//    }
//    override suspend fun handleIncomingAnswer(
//        sessionDescription: SessionDescription
//    ) {
//        rtcUseCases.handleIncomingAnswer(sessionDescription)
//    }
//    override suspend fun handleIncomingCallOffer(
//        receivedOfferType: OfferType,
//        sessionDescription: SessionDescription
//    ) {
//        rtcUseCases.handleIncomingCallOffer(receivedOfferType, sessionDescription)
//    }
//    override suspend fun handleIncomingCallHangUpRequest() {
//        rtcUseCases.handleIncomingCallHangUpRequest()
//    }
//    override suspend fun hangUpOutgoingCall() {
//        rtcUseCases.hangUpOutgoingCall()
//    }
//    override suspend fun sendOutgoingCallOffer(
//        offerType: OfferType
//    ) {
//        rtcUseCases.sendOutgoingCallOffer(offerType)
//    }
//
//
//    override fun onDestroy() {
//        peerConnectionObserverJob.cancel()
//        signallingEventObserverJob.cancel()
//        signallingSocketConnectionEventObserverJob.cancel()
//    }
//
//    companion object {
//        const val INTENT_EXTRAS_CALL_TYPE_KEY = "CALL_TYPE"
//        const val INTENT_EXTRAS_CALL_MODE_KEY = "CALL_MODE"
//        const val INTENT_EXTRAS_CALL_TYPE_INCOMING = "INCOMING_CALL"
//        const val INTENT_EXTRAS_CALL_TYPE_OUTGOING = "OUTGOING_CALL"
//        const val INTENT_EXTRAS_CALL_MODE_AUDIO = "AUDIO_CALL"
//        const val INTENT_EXTRAS_CALL_MODE_VIDEO = "VIDEO_CALL"
//    }
    override suspend fun sendOutgoingCallOffer(offerType: OfferType) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIncomingAnswer(sessionDescription: SessionDescription) {
        TODO("Not yet implemented")
    }

    override suspend fun handleLocalIceCandidate(localIceCandidate: IceCandidate) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIncomingCallOffer(
        receivedOfferType: OfferType,
        sessionDescription: SessionDescription
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun hangUpOutgoingCall() {
        TODO("Not yet implemented")
    }

    override suspend fun switchCamera() {
        TODO("Not yet implemented")
    }

    override suspend fun handleRemoteIceCandidate(remoteIceCandidate: IceCandidate) {
        TODO("Not yet implemented")
    }

    override fun setUpSurfaceViewRendererForVideoCapturing(localSurfaceRenderer: SurfaceViewRenderer) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIncomingCallHangUpRequest() {
        TODO("Not yet implemented")
    }

    override fun switchFromAudioToVideoCallOffer() {
        TODO("Not yet implemented")
    }

    override fun reconnectIceConnection() {
        TODO("Not yet implemented")
    }

    override fun toggleAudioOutput() {
        TODO("Not yet implemented")
    }

    override fun toggleVideo() {
        TODO("Not yet implemented")
    }

    override fun toggleMic() {
        TODO("Not yet implemented")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}


// configure the whole service according to Audio and video call

sealed interface CallConnectionState {
    data object Calling : CallConnectionState
    data object Connecting : CallConnectionState
    data object ReConnecting : CallConnectionState
    data object Ringing : CallConnectionState
    data class Ongoing(
        val duration: String, /// fixme: use long,timestamp or duac
        // ration type etc
        val mediaStream: List<MediaStream>?
    ) : CallConnectionState
    data object NoAnswer : CallConnectionState
    data object Busy : CallConnectionState
    data object Failed : CallConnectionState
}

/// todo just send video stream and play audio stream in here












/// check all the things and if you really need Shared flow or state flow
//    private lateinit var peerConnectionObserverEvents: SharedFlow<PeerConnectionObserverEvent>
//    private lateinit var signallingEvents: SharedFlow<SignallingEvent>
//    private lateinit var signallingSocketConnectionEvents: SharedFlow<SignallingSocketEvent>


//    override fun onCreate() {
//        super.onCreate()  /// Without calling super its giving late init variable not initialize
//        // check if you wanna put all these in onStartCommand later
//        startForeground()
//        jjj.cancel()
////        peerConnectionObserverJob = scope.launch {
////            peerConnectionObserverEvents = rtcUseCases
////                .createPeerConnection()
////                .shareIn(scope, SharingStarted.WhileSubscribed(5_000) //// Modify later acc. to need
////            )
////            rtcUseCases.sendOutgoingCallOffer(OfferType.VideoCall)
////        }
////
////        signallingEventObserverJob = scope.launch {
////            signallingEvents = signallingUseCases.observeSignallingEvent()
////                .shareIn(scope, SharingStarted.WhileSubscribed(5_000))
////                .also { signallingEvents = it }
////        }
////        signallingSocketConnectionEventObserverJob = scope.launch {
////            signallingSocketConnectionEvents = signallingUseCases
////                .observeSignallingSocketConnectionEvent()
////                .shareIn(scope, SharingStarted.WhileSubscribed(5_000))
////        }
//    }



//    override suspend fun observePeerConnectionEvents(): SharedFlow<PeerConnectionObserverEvent> {
//        return peerConnectionObserverEvents
//    }

//    override suspend fun observeSignallingEvents(): SharedFlow<SignallingEvent> {
//        return signallingEvents
//    }
//
//    override suspend fun observeSignallingSocketEvents(): SharedFlow<SignallingSocketEvent> {
//        return signallingSocketConnectionEvents
//    }