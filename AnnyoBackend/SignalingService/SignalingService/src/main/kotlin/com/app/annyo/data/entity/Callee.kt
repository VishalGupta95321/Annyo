package com.app.annyo.data.entity

import org.bson.codecs.pojo.annotations.BsonId

data class Callee(
    val calleeId:String,
    val calleeEvents: SignallingEvents = SignallingEvents(),
){}

fun Callee.addCalleeEvents(
    calleeEvents: SignallingEvents
) = this.copy(
    calleeEvents = calleeEvents
)