package com.app.annyo.data.entity

import com.app.annyo.callSessions
import com.app.annyo.models.responce.CallInfo
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

data class CallSession(
    @BsonId
    val callSessionId : String,
    val callerId: String,
    val calleeId: String,
    val isHangUpRequested: Boolean = false,
    val isConnectionEstablished: Boolean = false,
    val isCallPickedUp: Boolean = false,
    val isAudioToVideoSwitchRequested: Boolean = false,
    val disconnectedAt: Date? = null
)

/**
 * Marks the current call session as having a hang-up requested.
 *
 * This function sets the `isHangUpRequested` property of the call session to `true`.
 * It indicates that a participant has either requested to hang up the call or has disconnected during the ongoing call.
 *
 * This field is used to determine the appropriate action:
 * - If the user has deliberately hung up, the call session will be cleared immediately.
 * - If the disconnection is due to other reasons (e.g., network issues), the system will wait for a specified time interval
 *   before allowing to reconnect or clearing the session.
 *
 * Note: This action helps handle scenarios where participants disconnect from the call, whether due to a deliberate hang-up
 * or other issues such as network problems.
 */
fun CallSession.updateIsHangUpRequested(){
    callSessions[callSessionId] = this.copy(isHangUpRequested = true)
}

/**
 * Checks is the Call is connected between the caller and the callee.
 */
fun CallSession.updateIsConnectionEstablished(
    isCallConnected: Boolean
){
    callSessions[callSessionId] = this.copy(isConnectionEstablished = isCallConnected)
}


/// checking is call picked is because is call picked is getting called in onAnswer that will get called multiple times
fun CallSession.updateIsCallPickedUp(){
    if (!isCallPickedUp)
        callSessions[callSessionId] = this.copy(isCallPickedUp = true)
}

fun CallSession.updateIsAudioToVideoSwitchRequested(){
    callSessions[callSessionId] = this.copy(isAudioToVideoSwitchRequested = true)
}



fun CallSession.toCallInfo() = CallInfo(
    callSessionId = callSessionId,
    calleeId = calleeId,
    callerId = callerId
)





