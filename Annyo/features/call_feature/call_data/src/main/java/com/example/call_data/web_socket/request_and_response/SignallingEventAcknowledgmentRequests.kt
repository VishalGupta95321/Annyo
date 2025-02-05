package com.example.call_data.web_socket.request_and_response

import com.app.annyo.models.request.SignallingAcknowledgmentRequests

sealed interface SignallingEventAcknowledgmentRequests {
    data object AcknowledgeInitialOffer: SignallingEventAcknowledgmentRequests
    data object AcknowledgeAnswer: SignallingEventAcknowledgmentRequests
    data object AcknowledgeHangUp: SignallingEventAcknowledgmentRequests
    data object AcknowledgeDecline: SignallingEventAcknowledgmentRequests
    data object AcknowledgeOnAnotherCall: SignallingEventAcknowledgmentRequests
    data object AcknowledgeCallDataPayloadDeliveredEvent: SignallingEventAcknowledgmentRequests
    data object AcknowledgeUpdateOffer: SignallingEventAcknowledgmentRequests
    data class AcknowledgeIceCandidate(
        val indexCount: Int
    ):SignallingEventAcknowledgmentRequests
}


fun SignallingAcknowledgmentRequests.toSignallingAcknowledgeRequests():SignallingEventAcknowledgmentRequests{
    return when(this){
        SignallingAcknowledgmentRequests.AcknowledgeAnswer ->
            SignallingEventAcknowledgmentRequests.AcknowledgeAnswer
        SignallingAcknowledgmentRequests.AcknowledgeCallDataPayloadDeliveredEvent ->
            SignallingEventAcknowledgmentRequests.AcknowledgeCallDataPayloadDeliveredEvent
        SignallingAcknowledgmentRequests.AcknowledgeDecline ->
            SignallingEventAcknowledgmentRequests.AcknowledgeDecline
        SignallingAcknowledgmentRequests.AcknowledgeHangUp ->
            SignallingEventAcknowledgmentRequests.AcknowledgeHangUp
        is SignallingAcknowledgmentRequests.AcknowledgeIceCandidate ->
            SignallingEventAcknowledgmentRequests.AcknowledgeIceCandidate(this.indexCount)
        SignallingAcknowledgmentRequests.AcknowledgeInitialOffer ->
            SignallingEventAcknowledgmentRequests.AcknowledgeInitialOffer
        SignallingAcknowledgmentRequests.AcknowledgeOnAnotherCall ->
            SignallingEventAcknowledgmentRequests.AcknowledgeOnAnotherCall
        SignallingAcknowledgmentRequests.AcknowledgeUpdateOffer ->
            SignallingEventAcknowledgmentRequests.AcknowledgeUpdateOffer
    }
}