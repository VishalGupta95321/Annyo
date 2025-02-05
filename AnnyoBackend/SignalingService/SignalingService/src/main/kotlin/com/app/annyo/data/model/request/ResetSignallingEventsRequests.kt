package com.app.annyo.data.model.request

sealed interface ResetSignallingEventsRequests {
    data object RemoveOfferEvent: ResetSignallingEventsRequests
    data object RemoveAnswerEvent: ResetSignallingEventsRequests
    data object RemoveHangUpEvent: ResetSignallingEventsRequests
    data object RemoveDeclineEvent: ResetSignallingEventsRequests
    data object RemoveOnAnotherCallEvent: ResetSignallingEventsRequests
    data object RemoveCallDataPayloadDeliveredEvent: ResetSignallingEventsRequests
    data object RemoveUpdateOfferEvent: ResetSignallingEventsRequests
    data class RemoveIceCandidateEvent(
        val indexCount: Int
    ):ResetSignallingEventsRequests
}