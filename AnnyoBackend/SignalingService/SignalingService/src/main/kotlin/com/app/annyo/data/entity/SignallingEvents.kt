package com.app.annyo.data.entity

import org.bson.codecs.pojo.annotations.BsonId

//// instead of putting all the function in the data class body we can make them extension function outside the body and that will follow SRP principle
//// actually it should be a class instead of data class

// events sent by the user to the server to send to client
data class SignallingEvents(
    @BsonId
    val userId: String,
    val sessionId: String,

    val initialOfferEvent : String? = null,  /// Offer should be just be one cuz user will only receive the latest option
    val answerEvent : String? = null,   ///
    val hangUpEvent : String? = null,
    val declineEvent : String? = null,
    val onAnotherCallEvent: String? = null,
   // val callDataPayloadDeliveredEvent : String? = null, // remove this cuz its transfered to acknowledghe evenst
    val updateOfferEvent: String? = null,
    val iceCandidateEvents : List<String> = emptyList()
)

fun SignallingEvents.addInitialOffer(
    offer:String,
) = this.copy(initialOfferEvent = offer)

fun SignallingEvents.addAnswer(
    answer :String,
) = this.copy(answerEvent = answer)

fun SignallingEvents.addIceCandidates(
    iceCandidate: String
) = this.copy(iceCandidateEvents = iceCandidateEvents + iceCandidate)

fun SignallingEvents.addHangUp(
    hangUp:String
) = this.copy(hangUpEvent = hangUp)

fun SignallingEvents.addDecline(
    decline:String
) = this.copy(declineEvent = decline)

fun SignallingEvents.addOnAnotherCall(
    onAnotherCall: String
) = this.copy(onAnotherCallEvent = onAnotherCall)

//fun SignallingEvents.addCallDataPayloadDelivered(
//    callDataPayloadDelivered: String
//) = this.copy(callDataPayloadDeliveredEvent = callDataPayloadDelivered)

fun SignallingEvents.addUpdateOfferMessage(
    updateOfferMessage: String
) = this.copy(updateOfferEvent = updateOfferMessage)


fun SignallingEvents.removeOffer() = this.copy(initialOfferEvent = null)
fun SignallingEvents.removeAnswer() = this.copy(answerEvent = null)
fun SignallingEvents.removeHangUp() = this.copy(hangUpEvent = null)
fun SignallingEvents.removeDecline() = this.copy(declineEvent = null)
fun SignallingEvents.removeOnAnotherCall() = this.copy(onAnotherCallEvent = null)
//fun SignallingEvents.removeCallDataPayloadDelivered() = this.copy(callDataPayloadDeliveredEvent = null)
fun SignallingEvents.removeUpdateOffer() = this.copy(updateOfferEvent = null)
fun SignallingEvents.removeIceCandidate(
    index : Int
) = this.copy(
    iceCandidateEvents = iceCandidateEvents.drop(index)  //////
)
