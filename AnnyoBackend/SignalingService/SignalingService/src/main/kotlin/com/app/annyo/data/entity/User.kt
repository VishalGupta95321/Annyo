package com.app.annyo.data.entity

import io.ktor.server.websocket.*
import kotlinx.coroutines.isActive
import org.bson.codecs.pojo.annotations.BsonId

data class User(
    @BsonId
    val id : String,
   // val socketSession : DefaultWebSocketServerSession, // todo  it cant be stored in database directly so just store in
  //  val notificationId: String, /// no need here
    val currentCallSessionId: String?= null,
)

//fun User.isSocketSessionActive() = socketSession.isActive

fun User.updateUsersCurrentCallSessionId(
    currentCallSessionId : String
) = this.copy(currentCallSessionId = currentCallSessionId)