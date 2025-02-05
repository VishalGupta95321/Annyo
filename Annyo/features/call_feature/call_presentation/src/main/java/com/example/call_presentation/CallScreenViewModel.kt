package com.example.call_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.call_domain.model.SignallingEvents
import com.example.call_domain.use_cases.rtc_use_case.RtcUseCases
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.call_domain.util.PeerConnectionObserverEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallScreenViewModel @Inject constructor(
    private val rtcUseCases: RtcUseCases,
    private val signallingUseCases: SignallingUseCases
):ViewModel() {
    private fun observePeerConnectionEvents(){
       viewModelScope.launch {
            rtcUseCases.createPeerConnection().collect{ peerConnectionObserverEvent->
                when(peerConnectionObserverEvent){
                    is PeerConnectionObserverEvent.OnAddStream -> TODO()
                    is PeerConnectionObserverEvent.OnAddTrack -> {

                    }
                    is PeerConnectionObserverEvent.OnConnectionChange -> TODO()
                    is PeerConnectionObserverEvent.OnDataChannel -> TODO()
                    is PeerConnectionObserverEvent.OnIceCandidate -> {
                        rtcUseCases.handleLocalIceCandidate(peerConnectionObserverEvent.iceCandidate)
                    }
                    is PeerConnectionObserverEvent.OnIceCandidatesRemoved -> TODO()
                    is PeerConnectionObserverEvent.OnIceConnectionChange -> TODO()
                    is PeerConnectionObserverEvent.OnIceConnectionReceivingChange -> TODO()
                    is PeerConnectionObserverEvent.OnIceGatheringChange -> TODO()
                    is PeerConnectionObserverEvent.OnRemoveStream -> TODO()
                    is PeerConnectionObserverEvent.OnRemoveTrack -> TODO()
                    PeerConnectionObserverEvent.OnRenegotiationNeeded -> TODO()
                    is PeerConnectionObserverEvent.OnSignalingChange -> TODO()
                    PeerConnectionObserverEvent.PeerConnectionFailed -> {
                        // todo: Handle
                    }
                }
            }
        }
    }

    private fun observeSignallingSocketEvents(){
        viewModelScope.launch {
            signallingUseCases.observeSignallingEvent().collect{ signallingEvent->
                when(signallingEvent){
                    is SignallingEvents.OnAnswer -> {

                    }
                    SignallingEvents.OnHangUp -> {
                        rtcUseCases.hangUpOutgoingCall()
                    }
                    is SignallingEvents.OnIceCandidate -> {

                    }
                    is SignallingEvents.OnUpdatedOffer -> {

                    } /// handle later
                    is SignallingEvents.OnInitialOffer -> {

                    }

                    SignallingEvents.OnAnotherCall -> TODO()
                    SignallingEvents.OnCallDataPayloadDelivered -> TODO()
                    is SignallingEvents.OnCallFailed -> TODO()
                    is SignallingEvents.OnNoSuchCallSession -> TODO()
                    SignallingEvents.OnDecline -> TODO()
                    SignallingEvents.OnPolitePeerAssigned -> TODO()
                }
            }
        }
    }
}