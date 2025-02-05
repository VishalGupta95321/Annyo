package com.app.annyo.routes

import com.app.annyo.callSessions
import com.app.annyo.controller.SignallingController
import com.app.annyo.users
import com.app.annyo.data.utils.MessageDataType
import com.app.annyo.exception.SocketCloseReasons
import com.app.annyo.models.request.ConnectToCallServerRequest
import com.app.annyo.models.request.RegisterCallSessionRequest
import com.app.annyo.models.request.SignallingEventRequests
import com.app.annyo.data.entity.toCallInfo
import com.app.annyo.data.entity.updateIsCallPickedUp
import com.app.annyo.data.entity.updateIsHangUpRequested
import com.app.annyo.utils.safeLet
import com.example.utils.GetBack
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

fun Route.signallingWebSocketRoute(
    controller: SignallingController,
    json: Json
) {

    webSocket("/signalling") {

        val clientId = call.parameters["clientId"] ?: kotlin.run {
            controller.closeSocketSession(SocketCloseReasons.NoSuchClientId,this)
            return@webSocket
        }

        var callSessionId: String? = null

        try {
            incoming.consumeEach { frame ->

                val callSession = callSessions[callSessionId]

                val recipient = if (clientId == callSession?.callee?.calleeId) {
                    users[callSession.caller.callerId]
                } else users[callSession?.callee?.calleeId]



                if (frame is Frame.Text) {

                    val message = frame.readText()
                    val obj = json.parseToJsonElement(message).jsonObject
                    val eventType = obj["eventType"].toString().trim('"')


                    if (controller.validateAndCloseIfSessionMissing(eventType,callSessionId,this)){
                        return@webSocket
                    }

                    if (controller.validateAndCloseOnSessionMismatch(
                            eventType,
                            recipient?.currentCallSessionId,
                            callSessionId,
                            this)
                    ){ return@webSocket }


                    when (eventType) {

                        SignallingEventRequests.RegisterCallSession.eventType -> {

                            val registerCallSessionRequest = json.decodeFromString<RegisterCallSessionRequest>(message)

                            controller.registerCallSession(registerCallSessionRequest)

                        }


                        SignallingEventRequests.ConnectToCallServer.eventType -> {

                            val connectToCallServerRequest = json.decodeFromString<ConnectToCallServerRequest>(message)
                            callSessionId = connectToCallServerRequest.sessionId

                            val user = controller.ensureUserExists(clientId, this)

                            user?.let {_user ->
                                controller.inializeUserSIgnallingEvents()
                                controller.updateUserSocketSession(_user, this)
                                controller.updateUsersCurrentCallSessionId(_user, callSessionId!!)
                            }
                        }

//                        SignallingEventRequests.CallDataPayloadDelivered.eventType -> {
//                            safeLet(recipient, callSession) { _recipient, _callSession ->
//                                if (!controller.sendEvent(_recipient, message)) {
//                                    controller.storeEvent(
//                                        SignallingEventRequests.CallDataPayloadDelivered,
//                                        _callSession,
//                                        message,
//                                        clientId,
//                                    )
//                                }
//                            }
//                        }  //// todo() This will go to acknowledge events

                        SignallingEventRequests.OnAnotherCall.eventType -> {
                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.OnAnotherCall,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                            }
                        }

                        SignallingEventRequests.Decline.eventType -> {
                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.Decline,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                            }
                        }

                        SignallingEventRequests.InitialOffer.eventType -> {
                            callSession?.let { _callSession ->
                                controller.sendNotification(
                                    MessageDataType.Call(
                                        json.encodeToString(callSession.toCallInfo())
                                    )
                                ).also {
                                    when(it){
                                        is GetBack.Error -> {
                                            controller.closeSocketSession(SocketCloseReasons.CallFailed,this)
                                            return@webSocket
                                        }
                                        is GetBack.Success -> {
                                            controller.storeEvent(
                                                SignallingEventRequests.InitialOffer,
                                                _callSession,
                                                message,
                                                clientId,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        SignallingEventRequests.Answer.eventType -> {
                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.Answer,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                                callSession?.updateIsCallPickedUp() /////
                            }
                        }

                        SignallingEventRequests.HangUp.eventType -> {

                            callSession?.updateIsHangUpRequested()     /// no need to check if notification succeed or not cuz caller will disconnect anyways and then when callee will do anything it will get no such session etc.

                            controller.sendNotification(  /// only send notification for non ongoing call
                                MessageDataType.HangUp(
                                    json.encodeToString(callSession?.toCallInfo())
                                )
                            )
                            /// May Don't need to send notification for hang up cuz the socket would already be open to receive hangup event

                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.HangUp,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                            }
                        }

                        SignallingEventRequests.IceCandidate.eventType -> {
                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.IceCandidate,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                            }
                        }

                        SignallingEventRequests.UpdatedOffer.eventType -> {
                            safeLet(recipient, callSession) { _recipient, _callSession ->
                                if (!controller.sendEvent(_recipient, message)) {
                                    controller.storeEvent(
                                        SignallingEventRequests.UpdatedOffer,
                                        _callSession,
                                        message,
                                        clientId,
                                    )
                                }
                            }
                        }

                        SignallingEventRequests.GetMissedEvents.eventType -> {  /// all this is just for ongoing call
                            safeLet(callSession, recipient) { _callSession, _recipient ->
                                controller.dispatchMissedEvents(
                                    callSession = _callSession, _recipient, clientId
                                )
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()

        } finally {
            closeReason.await()
            callSessions[callSessionId]?.let { controller.clearCallSession(it) }
        }
    }
}


/// send notification for call failed

/// we are checking if call session is null onn every , actually we are already checking and closing the socket if session doesnt exist but
/// there we have exceptions like if event is of particular type So that why kotlin is not smart casting and we have to check for every events.
/// Well its okay later we will fix this minor  issue
// same for the recipient

/// use job to cancel the process of clearing the session when the connection is reestablished
/// so for the session clearence we use mongo db to store the disconnectd session with TTL somit will delete after 30 sec
// No need to learn docker and cloud now , as of now just learn a little mongo db so you can easily make your applicaition,
/// keep in moind that there are a lot of wonderful things waiting for you so complete this project as fast as you can.