package com.example.call_domain.model

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

sealed class SignallingEvents(
    val eventType: String
) {
    data class OnInitialOffer (
        val sessionDescription: SessionDescription,
    ): SignallingEvents(eventType = TYPE_ON_INITIAL_OFFER)

    data class OnUpdatedOffer(
        val sessionDescription: SessionDescription,
    ): SignallingEvents(TYPE_ON_UPDATED_OFFER)

    data class OnAnswer (
        val sessionDescription: SessionDescription,
    ): SignallingEvents(eventType = TYPE_ON_ANSWER)


    data class OnIceCandidate (
        val candidate : IceCandidate,
    ): SignallingEvents(eventType = TYPE_ON_ICE_CANDIDATE)

    data object OnHangUp: SignallingEvents(eventType = TYPE_ON_HANG_UP)

    data object OnDecline: SignallingEvents(eventType = TYPE_ON_DECLINE)

    data object OnAnotherCall: SignallingEvents(eventType = TYPE_ON_ANOTHER_CALL)

    data object OnPolitePeerAssigned: SignallingEvents(eventType = TYPE_ON_POLITE_PEER_ASSIGNED)

    data class OnCallFailed(
        val callSessionId: String
    ): SignallingEvents(TYPE_ON_CALL_FAILED)

    data class OnNoSuchCallSession(
        val callSessionId: String
    ): SignallingEvents(TYPE_ON_NO_SUCH_CALL_SESSION)

    data object OnCallDataPayloadDelivered : SignallingEvents(TYPE_ON_CALL_DATA_PAYLOAD_DELIVERED)

    companion object {
        const val TYPE_ON_INITIAL_OFFER = "TypeOnInitialOffer"
        const val TYPE_ON_ANSWER = "TypeOnAnswer"
        const val TYPE_ON_ICE_CANDIDATE = "TypeOnIceCandidate"
        const val TYPE_ON_HANG_UP = "TypeOnHangUp"
        const val TYPE_ON_DECLINE  = "TypeOnDecline"
        const val TYPE_ON_ANOTHER_CALL = "TypeOnAnotherCall"
        const val TYPE_ON_UPDATED_OFFER = "TypeOnUpdatedOffer"
        const val TYPE_ON_CALL_FAILED = "TypeOnCallFailed"
        const val TYPE_ON_NO_SUCH_CALL_SESSION = "TypeOnNoSuchCallSession"
        const val TYPE_ON_CALL_DATA_PAYLOAD_DELIVERED = "TypeOnCallDataPayloadDelivered"
        const val TYPE_ON_POLITE_PEER_ASSIGNED = "TypeOnPolitePeerAssigned"

    }
}
