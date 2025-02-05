package com.app.annyo.data.entity

import org.bson.codecs.pojo.annotations.BsonId

data class Caller(
    val callerId:String,
    val callerEvents: SignallingEvents = SignallingEvents(),
)

fun Caller.addCallerEvents(
    callerEvents: SignallingEvents
) = this.copy(
    callerEvents = callerEvents
)