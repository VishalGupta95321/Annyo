package com.example.call_data.web_socket.request_and_response

import com.example.call_data.web_socket.custom_serializers.CustomIceCandidateAsStringSerializer
import com.example.call_data.web_socket.custom_serializers.CustomSessionDescriptionAsStringSerializer
import com.example.call_domain.model.SignallingRequests
import kotlinx.serialization.Serializable
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription


@Serializable
sealed class SignallingEventRequests(
    val eventType: String
) {

    @Serializable
    data object GetMissedEvents : SignallingEventRequests(TYPE_GET_MISSED_EVENTS)

    @Serializable
    data class RegisterCallSession(
        val sessionId:String,
        val callerId:String,
        val calleeId:String
    ):SignallingEventRequests(TYPE_REGISTER_CALL_SESSION)

    @Serializable
    data class SendInitialOffer (
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription,
    ): SignallingEventRequests(eventType = TYPE_SEND_INITIAL_OFFER)

    @Serializable
    data class SendUpdatedOffer(
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription
    ): SignallingEventRequests(TYPE_SEND_UPDATED_OFFER)

    @Serializable
    data class SendAnswer (
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription,
    ): SignallingEventRequests(eventType = TYPE_SEND_ANSWER)


    @Serializable
    data class SendIceCandidate (
        @Serializable(with = CustomIceCandidateAsStringSerializer::class)
        val candidate : IceCandidate,
    ): SignallingEventRequests(eventType = TYPE_SEND_ICE_CANDIDATE)

    @Serializable
    data object SendHangUp: SignallingEventRequests(eventType = TYPE_SEND_HANG_UP)

    @Serializable
    data object SendDecline: SignallingEventRequests(eventType = TYPE_SEND_DECLINE)

    @Serializable
    data object SendOnAnotherCall: SignallingEventRequests(eventType = TYPE_SEND_ON_ANOTHER_CALL)

    @Serializable
    data class ConnectToCallServer(
        val sessionId: String,
    ) : SignallingEventRequests(TYPE_CONNECT_TO_CALL_SERVER)


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

fun SignallingRequests.toSignallingEventRequests(): SignallingEventRequests {
    return when (this) {
        is SignallingRequests.GetMissedEvents -> SignallingEventRequests.GetMissedEvents
        is SignallingRequests.SendInitialOffer -> SignallingEventRequests.SendInitialOffer(
            sessionDescription = this.sessionDescription,
        )
        is SignallingRequests.SendUpdatedOffer -> SignallingEventRequests.SendUpdatedOffer(
            sessionDescription = this.sessionDescription,
        )
        is SignallingRequests.SendAnswer -> SignallingEventRequests.SendAnswer(
            sessionDescription = this.sessionDescription,
        )
        is SignallingRequests.SendIceCandidate -> SignallingEventRequests.SendIceCandidate(
            candidate = this.candidate,
        )
        is SignallingRequests.SendHangUp -> SignallingEventRequests.SendHangUp
        is SignallingRequests.SendOnAnotherCall -> SignallingEventRequests.SendOnAnotherCall

        is SignallingRequests.RegisterCallSession -> SignallingEventRequests.RegisterCallSession(
            sessionId = this.sessionId,
            callerId = this.callerId,
            calleeId = this.calleeId
        )

        is SignallingRequests.ConnectToCallServer -> SignallingEventRequests.ConnectToCallServer(
            sessionId = this.sessionId
            )
        SignallingRequests.SendDecline -> SignallingEventRequests.SendDecline
    }
}