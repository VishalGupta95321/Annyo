package com.app.annyo.models.request

sealed interface SignallingAcknowledgmentRequests {
    data object AcknowledgeInitialOffer: SignallingAcknowledgmentRequests
    data object AcknowledgeAnswer: SignallingAcknowledgmentRequests
    data object AcknowledgeHangUp: SignallingAcknowledgmentRequests
    data object AcknowledgeDecline: SignallingAcknowledgmentRequests
    data object AcknowledgeOnAnotherCall: SignallingAcknowledgmentRequests
    data object AcknowledgeCallDataPayloadDeliveredEvent: SignallingAcknowledgmentRequests
    data object AcknowledgeUpdateOffer: SignallingAcknowledgmentRequests
    data class AcknowledgeIceCandidate(
        val indexCount: Int
    ): SignallingAcknowledgmentRequests
}

/// callDataPayloadDeliveredEvent remove this from the signalling and put it in the acknowledge events
/// on the client side store the acknowledge events just in the viemodel.
///