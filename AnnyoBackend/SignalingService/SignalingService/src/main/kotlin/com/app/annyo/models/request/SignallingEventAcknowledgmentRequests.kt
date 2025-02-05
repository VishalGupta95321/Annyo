package com.app.annyo.models.request

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
    ): SignallingEventAcknowledgmentRequests /// i dont think its neccessary to acknowledge this
}

/// callDataPayloadDeliveredEvent remove this from the signalling and put it in the acknowledge events
/// on the client side store the acknowledge events just in the viewmodel.
///