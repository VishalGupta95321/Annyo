package com.example.call_domain.model

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

sealed class SignallingRequests(
    val type: String
) {

    data object GetMissedEvents : SignallingRequests(TYPE_GET_MISSED_EVENTS)

    data class RegisterCallSession(
        val sessionId:String,
        val callerId:String,
        val calleeId:String
    ):SignallingRequests(TYPE_REGISTER_CALL_SESSION)

    data class SendInitialOffer (
        val sessionDescription: SessionDescription,
    ): SignallingRequests(type = TYPE_SEND_INITIAL_OFFER)

    data class SendUpdatedOffer(
        val sessionDescription: SessionDescription,
    ): SignallingRequests(TYPE_SEND_UPDATED_OFFER)

    data class SendAnswer (
        val sessionDescription: SessionDescription,
    ): SignallingRequests(type = TYPE_SEND_ANSWER)


    data class SendIceCandidate (
        val candidate : IceCandidate,
    ): SignallingRequests(type = TYPE_SEND_ICE_CANDIDATE)

    data object SendHangUp: SignallingRequests(type = TYPE_SEND_HANG_UP)

    data object SendDecline: SignallingRequests(type = TYPE_SEND_DECLINE)

    data object SendOnAnotherCall: SignallingRequests(type = TYPE_SEND_ON_ANOTHER_CALL)

    data class ConnectToCallServer(
        val sessionId: String,
    ) : SignallingRequests(TYPE_CONNECT_TO_CALL_SERVER)

    companion object {
        const val TYPE_REGISTER_CALL_SESSION = "TypeRegisterCallSession"
        const val TYPE_SEND_INITIAL_OFFER = "TypeSendInitialOffer"
        const val TYPE_SEND_ANSWER = "TypeSendAnswer"
        const val TYPE_SEND_ICE_CANDIDATE = "TypeSendIceCandidate"
        const val TYPE_SEND_HANG_UP = "TypeSendHangUp"
        const val TYPE_SEND_DECLINE = "TypeSendDecline"
        const val TYPE_SEND_ON_ANOTHER_CALL = "TypeSendOnAnotherCall"
        const val TYPE_SEND_UPDATED_OFFER = "TypeSendUpdatedOffer"
        const val TYPE_GET_MISSED_EVENTS = "TypeGetMissedEvents"
        const val TYPE_CONNECT_TO_CALL_SERVER = "TypeConnectToCallServer"
    }
}
