package com.app.annyo.controller

import com.app.annyo.callSessions
import com.app.annyo.data.entity.*
import com.app.annyo.data.firebase_messaging.FirebaseMessagingClient
import com.app.annyo.data.entity.CallSession
import com.app.annyo.users
import com.app.annyo.data.utils.MessageDataType
import com.app.annyo.exception.SocketCloseReasons
import com.app.annyo.models.request.RegisterCallSessionRequest
import com.app.annyo.models.request.SignallingEventRequests
import com.app.annyo.models.request.toCallSession
import com.app.annyo.utils.safeLet
import com.example.utils.GetBackBasic
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


private const val CALL_RECONNECTION_TIMEOUT_SECONDS = 30000L

// Fixme : create an interface for signalling controller maybe
class SignallingController(
    private val firebaseMessagingClient: FirebaseMessagingClient,
    private val json: Json
) {
    suspend fun clearCallSession(
        callSession: CallSession,
    ) {
        val caller = users[callSession.caller.callerId]
        val callee = users[callSession.callee.calleeId]

        safeLet(caller, callee) { _caller, _callee ->
            /// When both caller and callee is on the same session
            if (_caller.currentCallSessionId == _callee.currentCallSessionId) {

                //// When the  call is not picked up
                if (!callSession.isCallPickedUp){

                    //And the caller is disconnected
                    if (!_caller.isSocketSessionActive()){
                        callSessions.remove(callSession.callSessionId)
                        updateUsersCurrentCallSessionId(_caller, null)
                    }
                    //// And When the callee will disconnect in between call ringing then
                    //// let caller wait to hangup or will eventually call will disconnect from the client side after some timeout.

                } else {   /// When call is picked up

                    // When both or either of them is disconnected
                    if(
                        (!_caller.isSocketSessionActive() || !_callee.isSocketSessionActive()) ||
                        (!_caller.isSocketSessionActive() && !_callee.isSocketSessionActive())
                    ){

                        //// When disconnection is because of network wait 30 sec to reconnect before clearing the session
                        if (!callSession.isHangUpRequested) delay(CALL_RECONNECTION_TIMEOUT_SECONDS)

                        callSessions.remove(callSession.callSessionId)
                        updateUsersCurrentCallSessionId(_caller, null)
                        updateUsersCurrentCallSessionId(_callee, null)
                    }
                }

            } else { /// When callee is on different call session / already on another call

                /// And the call disconnected from the call side
                if (!_caller.isSocketSessionActive()) {
                    callSessions.remove(callSession.callSessionId)
                    updateUsersCurrentCallSessionId(_caller, null)
                }
             }
        }
    }


    suspend fun sendNotification(
        data: MessageDataType
    ): GetBackBasic {
        return firebaseMessagingClient.sendDataMessage(
            registrationToken = "", //// TODO()
            data = data
        )
    }


    suspend fun sendEvent(
        recipient: User,
        event: String
    ): Boolean {
        if (recipient.isSocketSessionActive()) {
            recipient.socketSession.send(event)
            return true
        }
        return false
    }

    /***
     * This function checks if the receiver's (caller/callee) currentCallSessionId is the same as the sender's (caller/callee) currentCallSessionId.
     * This ensures that if another caller (a third party) attempts to send events to the user, the attempt will fail.
     */
    suspend fun validateAndCloseOnSessionMismatch(
        eventType: String,
        recipientCurrentCallSessionId: String?,
        currentCallSessionId: String?,
        socketSession: DefaultWebSocketServerSession,
    ): Boolean {
        val excludedEvents = listOf(
            SignallingEventRequests.RegisterCallSession.eventType,
            SignallingEventRequests.ConnectToCallServer.eventType,
            SignallingEventRequests.InitialOffer.eventType,
            SignallingEventRequests.HangUp.eventType
        )
        if (eventType in excludedEvents) return false

        return if (recipientCurrentCallSessionId != currentCallSessionId) {
            closeSocketSession(SocketCloseReasons.CallSessionMismatch, socketSession)
            true
        } else false
    }

    /***
     * Here we are dispatching all the events that got missed by caller or callee,
     * we are checking if the current client is caller if yes then we are sending the all the callee events that the caller
     * missed and the same for callee.
     *
     * Here we are prioritizing the events according to the connection lifetime, for example if there is already a hangUp
     * event then we are sending that first and after that the connection will get closed by the client so we dont need to
     * send rest of the events.
     *  **/

    suspend fun dispatchMissedEvents(
        callSession: CallSession,
        recipient: User,
        clientId: String
    ) {
        val isCaller = isCaller(clientId, callSession)

        val missedEvents = if (isCaller) {
            callSession.callee.calleeEvents
        } else callSession.caller.callerEvents

        val hangUpEvent = missedEvents.hangUpEvent
        val declineEvent = missedEvents.declineEvent
        val offerEvent = missedEvents.initialOfferEvent
        val answerEvent = missedEvents.answerEvent
        val updateOfferEvent = missedEvents.updateOfferEvent
        val iceCandidateEvents = missedEvents.iceCandidateEvents
        val onAnotherCallEvents = missedEvents.onAnotherCallEvent
        val callDataPayloadDeliveredEvent = missedEvents.callDataPayloadDeliveredEvent

        if (isCaller && declineEvent != null) {      ///// Check and give priority which event to send first and which dont need to send
            if (sendEvent(recipient, declineEvent))
                removeEvent(SignallingEventRequests.Decline, callSession, clientId)
        } else
            if (hangUpEvent != null) {
                if (sendEvent(recipient, hangUpEvent))
                    removeEvent(SignallingEventRequests.HangUp, callSession, clientId)
            } else {

                if (isCaller && callDataPayloadDeliveredEvent != null) {
                    if (sendEvent(recipient, callDataPayloadDeliveredEvent))
                        removeEvent(
                            SignallingEventRequests.CallDataPayloadDelivered,
                            callSession,
                            clientId
                        )
                }

                if (isCaller && onAnotherCallEvents != null) {
                    if (sendEvent(recipient, onAnotherCallEvents))
                        removeEvent(
                            SignallingEventRequests.OnAnotherCall,
                            callSession,
                            clientId
                        )
                }

                if (!isCaller && offerEvent != null) {
                    if (sendEvent(recipient, offerEvent))
                        removeEvent(
                            SignallingEventRequests.InitialOffer,
                            callSession,
                            clientId
                        )
                }

                if (answerEvent != null) {
                    if (sendEvent(recipient, answerEvent))
                        removeEvent(
                            SignallingEventRequests.Answer,
                            callSession,
                            clientId
                        )
                }

                if (iceCandidateEvents.isNotEmpty()) {
                    iceCandidateEvents.forEachIndexed { index, iceCandidate ->
                        if (sendEvent(recipient, iceCandidate))
                            removeEvent(
                                SignallingEventRequests.IceCandidate,
                                callSession,
                                clientId,
                                index
                            )
                    }
                }
                if (updateOfferEvent != null) {
                    if (sendEvent(recipient, updateOfferEvent))
                        removeEvent(
                            SignallingEventRequests.UpdatedOffer,
                            callSession,
                            clientId
                        )
                }
            }
    }

    /***
     * checking if the current client id is of caller or callee and according to that removing their events
     * **/
    private fun removeEvent(
        event: SignallingEventRequests,
        callSession: CallSession,
        clientId: String,
        iceCandidateIndex: Int = 0   ////  could have used sealed interface or class to remove this parameter from here
    ) {
        if (isCaller(clientId, callSession)) {
            callSessions[callSession.callSessionId] = callSession.copy(
                callee = removeCalleeEvents(callSession.callee, event, iceCandidateIndex)
            )
        } else {
            callSessions[callSession.callSessionId] = callSession.copy(
                caller = removeCallerEvents(callSession.caller, event, iceCandidateIndex)
            )
        }

    }

    /***
     * removing all the events send by callee to caller
     * **/
    private fun removeCalleeEvents(
        callee: Callee,
        event: SignallingEventRequests,
        iceCandidateIndex: Int
    ): Callee {
        return callee.copy(
            calleeEvents = when (event) {
                SignallingEventRequests.Answer -> callee.calleeEvents.removeAnswer()
                SignallingEventRequests.UpdatedOffer -> callee.calleeEvents.removeUpdateOffer()
                SignallingEventRequests.IceCandidate -> callee.calleeEvents.removeIceCandidate(iceCandidateIndex)
                SignallingEventRequests.HangUp -> callee.calleeEvents.removeHangUp()
                SignallingEventRequests.CallDataPayloadDelivered -> callee.calleeEvents.removeCallDataPayloadDelivered()
                SignallingEventRequests.Decline -> callee.calleeEvents.removeDecline()
                SignallingEventRequests.OnAnotherCall -> callee.calleeEvents.removeOnAnotherCall()
                else -> callee.calleeEvents // won't reach
            }
        )
    }

    /***
     * removing all the events send by caller to callee
     * **/
    private fun removeCallerEvents(
        caller: Caller,
        event: SignallingEventRequests,
        iceCandidateIndex: Int
    ): Caller {
        return caller.copy(
            callerEvents = when (event) {
                SignallingEventRequests.InitialOffer -> caller.callerEvents.removeOffer()
                SignallingEventRequests.Answer -> caller.callerEvents.removeAnswer()
                SignallingEventRequests.UpdatedOffer -> caller.callerEvents.removeUpdateOffer()
                SignallingEventRequests.IceCandidate -> caller.callerEvents.removeIceCandidate(iceCandidateIndex)
                SignallingEventRequests.HangUp -> caller.callerEvents.removeHangUp()
                else -> caller.callerEvents // won't reach
            }
        )
    }

    /***
     * checking if the current client id is of caller or callee and according to that storing their events
     * **/
    fun storeEvent(
        event: SignallingEventRequests,
        callSession: CallSession,
        message: String,
        clientId: String,
    ) {
        if (isCaller(clientId, callSession)) {

            callSession.copy(
                caller = storeCallerEvents(callSession.caller, event, message)
            ).also { callSessions[callSession.callSessionId] = it }

        } else {

            callSession.copy(
                callee = storeCalleeEvents(callSession.callee, event, message)
            ).also { callSessions[callSession.callSessionId] = it }
        }

    }

    /***
     * storing all the events send by caller to callee
     * **/
    private fun storeCallerEvents(
        caller: Caller,
        event: SignallingEventRequests,
        message: String
    ): Caller {
        return caller.addCallerEvents(
            callerEvents = when (event) {
                SignallingEventRequests.InitialOffer -> caller.callerEvents.addInitialOffer(message)
                SignallingEventRequests.Answer -> caller.callerEvents.addAnswer(message)
                SignallingEventRequests.UpdatedOffer -> caller.callerEvents.addUpdateOfferMessage(message)
                SignallingEventRequests.IceCandidate -> caller.callerEvents.addIceCandidates(message)
                SignallingEventRequests.HangUp -> caller.callerEvents.addHangUp(message)
                else -> caller.callerEvents // won't reach
            }
        )
    }

    /***
     * storing all the events send by callee to caller
     * **/
    private fun storeCalleeEvents(
        callee: Callee,
        event: SignallingEventRequests,
        message: String
    ): Callee {
        return callee.addCalleeEvents(
            calleeEvents = when (event) {
                SignallingEventRequests.Answer -> callee.calleeEvents.addAnswer(message)
                SignallingEventRequests.UpdatedOffer -> callee.calleeEvents.addUpdateOfferMessage(message)
                SignallingEventRequests.IceCandidate -> callee.calleeEvents.addIceCandidates(message)
                SignallingEventRequests.HangUp -> callee.calleeEvents.addHangUp(message)
                SignallingEventRequests.CallDataPayloadDelivered -> callee.calleeEvents.addCallDataPayloadDelivered(
                    message
                )

                SignallingEventRequests.Decline -> callee.calleeEvents.addDecline(message)
                SignallingEventRequests.OnAnotherCall -> callee.calleeEvents.addOnAnotherCall(message)
                else -> callee.calleeEvents // won't reach
            }
        )
    }


    private fun createCloseReason(reason: SocketCloseReasons): String {
        return json.encodeToString(reason)
    }

    fun ensureUserExists(
        uid: String,
        socketSession: DefaultWebSocketServerSession
    ): User? {
        val isUserExists = users.contains(uid)
        if (!isUserExists) {
            users[uid] = User(uid, socketSession)
        }
        return users[uid]
    }

    fun updateUserSocketSession(
        user: User,
        socketSession: DefaultWebSocketServerSession
    ) {
        users[user.id] = user.copy(socketSession = socketSession)
    }

    fun updateUsersCurrentCallSessionId(
        user: User,
        callSessionId: String?,
    ) {
         callSessionId?.let{
             users[user.id] = user.updateUsersCurrentCallSessionId(it)
         }
    }

    fun registerCallSession(
        request: RegisterCallSessionRequest
    ) {
        callSessions[request.sessionId] ?: kotlin.run {
            callSessions[request.sessionId] = request.toCallSession()
        }
    }
    /// the session identifier is caller+callee id , It's different from call session id // just temporary cuz we arent using database as of now


    suspend fun validateAndCloseIfSessionMissing(
        eventType: String,
        sessionId: String?,
        socketSession: DefaultWebSocketServerSession,
    ): Boolean {
        if (eventType != SignallingEventRequests.RegisterCallSession.eventType && eventType != SignallingEventRequests.ConnectToCallServer.eventType) {
            if (callSessions[sessionId] == null) {
                closeSocketSession(SocketCloseReasons.NoSuchCallSession, socketSession)
                return false
            }
        }
        return true
    }

    private fun isCaller(
        clientId: String,
        callSession: CallSession
    ): Boolean {
        return callSession.caller.callerId == clientId
    }

    // this function might be in the util section
    suspend fun closeSocketSession(
        reason: SocketCloseReasons,
        socketSession: DefaultWebSocketServerSession
    ) {
        when (reason) {
            SocketCloseReasons.CallFailed -> {
                socketSession.close(
                    CloseReason(
                        CloseReason.Codes.INTERNAL_ERROR,
                        createCloseReason(SocketCloseReasons.CallFailed)
                    )
                )
            }

            SocketCloseReasons.NoSuchCallSession -> {
                socketSession.close(
                    CloseReason(
                        CloseReason.Codes.VIOLATED_POLICY,
                        createCloseReason(SocketCloseReasons.NoSuchCallSession)
                    )
                )
            }

            SocketCloseReasons.NoSuchClientId -> {
                socketSession.close(
                    CloseReason(
                        CloseReason.Codes.VIOLATED_POLICY,
                        createCloseReason(SocketCloseReasons.NoSuchClientId)
                    )
                )
            }

            SocketCloseReasons.CallSessionMismatch -> {
                socketSession.close(
                    CloseReason(
                        CloseReason.Codes.VIOLATED_POLICY,
                        createCloseReason(SocketCloseReasons.CallSessionMismatch)
                    )
                )
            }
        }
    }
}

/// todo not sending any responses like success or error from the controller

