package com.example.call_data.web_socket.request_and_response

import com.example.call_data.web_socket.custom_serializers.CustomIceCandidateAsStringSerializer
import com.example.call_data.web_socket.custom_serializers.CustomSessionDescriptionAsStringSerializer
import com.example.call_domain.model.SignallingEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

@Serializable
sealed class SignallingEventsDto(
    val eventType: String
) {

    @Serializable
    data class OnInitialOffer (
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription,
    ): SignallingEventsDto(eventType = TYPE_ON_INITIAL_OFFER)

    @Serializable
    data class OnUpdatedOffer(
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription
    ): SignallingEventsDto(TYPE_ON_UPDATED_OFFER)

    @Serializable
    data class OnAnswer (
        @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
        val sessionDescription: SessionDescription,
    ): SignallingEventsDto(eventType = TYPE_ON_ANSWER)


    @Serializable
    data class OnIceCandidate (
        @Serializable(with = CustomIceCandidateAsStringSerializer::class)
        val candidate : IceCandidate,
    ): SignallingEventsDto(eventType = TYPE_ON_ICE_CANDIDATE)

    @Serializable
    data object OnHangUp: SignallingEventsDto(eventType = TYPE_ON_HANG_UP)

    @Serializable
    data object OnDecline: SignallingEventsDto(eventType = TYPE_ON_DECLINE)

    @Serializable
    data object OnAnotherCall: SignallingEventsDto(eventType = TYPE_ON_ANOTHER_CALL)

    @Serializable
    data object OnPolitePeerAssigned: SignallingEventsDto(eventType = TYPE_ON_POLITE_PEER_ASSIGNED)

    @Serializable
    data class OnCallFailed(
        val callSessionId: String
    ):SignallingEventsDto(TYPE_ON_CALL_FAILED)

    @Serializable
    data class OnNoSuchCallSession(
        val callSessionId: String
    ): SignallingEventsDto(TYPE_ON_NO_SUCH_CALL_SESSION)

    @Serializable
    data object OnCallDataPayloadDelivered : SignallingEventsDto(TYPE_ON_CALL_DATA_PAYLOAD_DELIVERED)

    companion object {
        const val TYPE_ON_INITIAL_OFFER = "TypeOnInitialOffer"
        const val TYPE_ON_ANSWER = "TypeOnAnswer"
        const val TYPE_ON_ANOTHER_CALL = "TypeOnAnotherCall"
        const val TYPE_ON_ICE_CANDIDATE = "TypeOnIceCandidate"
        const val TYPE_ON_HANG_UP = "TypeOnHangUp"
        const val TYPE_ON_DECLINE  = "TypeOnDecline"
        const val TYPE_ON_UPDATED_OFFER = "TypeOnUpdatedOffer"
        const val TYPE_ON_CALL_FAILED = "TypeOnCallFailed"
        const val TYPE_ON_NO_SUCH_CALL_SESSION = "TypeOnNoSuchCallSession"
        const val TYPE_ON_CALL_DATA_PAYLOAD_DELIVERED = "TypeOnCallDataPayloadDelivered"
        const val TYPE_ON_POLITE_PEER_ASSIGNED = "TypeOnPolitePeerAssigned"
    }
}



fun Flow<SignallingEventsDto>.mapToSignallingEvents(): Flow<SignallingEvents> {
    return this.map {
        when (it) {
            is SignallingEventsDto.OnInitialOffer -> SignallingEvents.OnInitialOffer(
                sessionDescription = it.sessionDescription,
            )
            is SignallingEventsDto.OnUpdatedOffer -> SignallingEvents.OnUpdatedOffer(
                sessionDescription = it.sessionDescription,
            )
            is SignallingEventsDto.OnAnswer -> SignallingEvents.OnAnswer(
                sessionDescription = it.sessionDescription,
            )
            is SignallingEventsDto.OnIceCandidate -> SignallingEvents.OnIceCandidate(
                candidate = it.candidate,
            )
            is SignallingEventsDto.OnHangUp -> SignallingEvents.OnHangUp

            is SignallingEventsDto.OnAnotherCall -> SignallingEvents.OnAnotherCall
            is SignallingEventsDto.OnCallFailed -> SignallingEvents.OnCallFailed(callSessionId = it.callSessionId)
            is SignallingEventsDto.OnNoSuchCallSession -> SignallingEvents.OnNoSuchCallSession(callSessionId = it.callSessionId)
            SignallingEventsDto.OnCallDataPayloadDelivered -> SignallingEvents.OnCallDataPayloadDelivered
            SignallingEventsDto.OnDecline -> SignallingEvents.OnDecline
            SignallingEventsDto.OnPolitePeerAssigned -> SignallingEvents.OnPolitePeerAssigned
        }
    }
}  /// Maybe put these functions in the mapper
/// instead of sealed class we can use abstract class or interface to using that make4 code more cleaner and will adhere the single responsibility principle