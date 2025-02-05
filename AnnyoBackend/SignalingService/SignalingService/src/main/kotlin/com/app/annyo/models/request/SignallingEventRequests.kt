package com.app.annyo.models.request

sealed class SignallingEventRequests(val eventType: String) {
    data object InitialOffer : SignallingEventRequests("TypeSendInitialOffer")
    data object Answer : SignallingEventRequests("TypeSendAnswer")
    data object IceCandidate : SignallingEventRequests("TypeSendIceCandidate")
    data object HangUp : SignallingEventRequests("TypeSendHangUp")
    data object Decline : SignallingEventRequests("TypeSendDecline")
    data object UpdatedOffer : SignallingEventRequests("TypeSendUpdatedOffer")
    data object GetMissedEvents : SignallingEventRequests("TypeGetMissedEvents")
    data object RegisterCallSession: SignallingEventRequests("TypeRegisterCallSession")
    data object ConnectToCallServer : SignallingEventRequests("TypeConnectToCallServer")
    data object OnAnotherCall : SignallingEventRequests("TypeSendOnAnotherCall")
}