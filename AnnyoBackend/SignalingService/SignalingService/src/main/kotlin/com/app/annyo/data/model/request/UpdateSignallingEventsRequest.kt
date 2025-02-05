package com.app.annyo.data.model.request

sealed interface UpdateSignallingEventsRequest {
    data class AddInitialOffer(
        val offer: String
    ):UpdateSignallingEventsRequest

    data class AddAnswer(
        val answer: String
    ):UpdateSignallingEventsRequest

    data class AddIceCandidate(
        val iceCandidate: String
    ):UpdateSignallingEventsRequest

    data class AddHangUp(
        val hangupEvent: String
    ):UpdateSignallingEventsRequest

    data class AddDecline(
        val declineEvent: String
    ):UpdateSignallingEventsRequest

    data class AddOnAnotherCall(
        val onAnotherCallEvent: String
    ):UpdateSignallingEventsRequest

    data class AddCallDataPayloadDelivered(
       val callDataPayloadDeliveredEvent: String
    ):UpdateSignallingEventsRequest

    data class AddUpdateOfferMessage(
        val updateOfferMessageEvent: String
    ):UpdateSignallingEventsRequest

}