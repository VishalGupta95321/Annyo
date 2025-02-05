package com.example.call_data.web_socket.custom_scarlet_adapters

import com.example.call_data.web_socket.request_and_response.SignallingEventRequests
import com.example.call_data.web_socket.request_and_response.SignallingEventsDto
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.lang.reflect.Type

class CustomKotlinSerializationMessageAdapter<T> private constructor(
    private val serializer : Json
): MessageAdapter<T> {

    @Throws(TypeCastException::class)
    override fun fromMessage(message: Message): T {
        val stringValue = when(message) {
            is Message.Text -> message.value
            is Message.Bytes -> message.value.toString()
        }
        val jsonObject = serializer.parseToJsonElement(stringValue).jsonObject
        return when(jsonObject["eventType"].toString()){
            SignallingEventRequests.TYPE_SEND_INITIAL_OFFER ->
                serializer.decodeFromString< SignallingEventsDto.OnInitialOffer>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_ANSWER ->
                serializer.decodeFromString<SignallingEventsDto.OnAnswer>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_ICE_CANDIDATE ->
                serializer.decodeFromString<SignallingEventsDto.OnIceCandidate>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_HANG_UP->
                serializer.decodeFromString<SignallingEventsDto.OnHangUp>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_ON_ANOTHER_CALL->
                serializer.decodeFromString<SignallingEventsDto.OnAnotherCall>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_UPDATED_OFFER->
                serializer.decodeFromString<SignallingEventsDto.OnUpdatedOffer>(stringValue) as T
            SignallingEventRequests.TYPE_SEND_DECLINE->
                serializer.decodeFromString<SignallingEventsDto.OnDecline>(stringValue) as T
            SignallingEventsDto.TYPE_ON_POLITE_PEER_ASSIGNED->
                serializer.decodeFromString<SignallingEventsDto.OnPolitePeerAssigned>(stringValue) as T


            else -> throw TypeCastException("")  ///// maybe come with something better
        }
    }

    @Throws(TypeCastException::class)
    override fun toMessage(data: T): Message {
        val convertedData  = data as SignallingEventRequests
        return when(convertedData.eventType){  /// I think we just send the data normally cuz in the backend backend we are directly sending the raw message.
            SignallingEventRequests.TYPE_SEND_INITIAL_OFFER ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendInitialOffer))
            SignallingEventRequests.TYPE_SEND_ANSWER ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendAnswer))
            SignallingEventRequests.TYPE_SEND_ICE_CANDIDATE ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendIceCandidate))
            SignallingEventRequests.TYPE_SEND_HANG_UP ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendHangUp))
            SignallingEventRequests.TYPE_SEND_ON_ANOTHER_CALL ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendOnAnotherCall))
            SignallingEventRequests.TYPE_GET_MISSED_EVENTS ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.GetMissedEvents))
            SignallingEventRequests.TYPE_SEND_UPDATED_OFFER ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendUpdatedOffer))
            SignallingEventRequests.TYPE_REGISTER_CALL_SESSION ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.RegisterCallSession))
            SignallingEventRequests.TYPE_CONNECT_TO_CALL_SERVER ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.ConnectToCallServer))
//            SignallingEventRequests.TYPE_SEND_CALL_DATA_PAYLOAD_DELIVERED ->
//                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendCallDataPayloadDelivered))
            SignallingEventRequests.TYPE_SEND_DECLINE ->
                Message.Text(serializer.encodeToString(convertedData as SignallingEventRequests.SendDecline))


            else -> throw TypeCastException("")
        }


    }

    class Factory(
        private val serializer: Json
    ): MessageAdapter.Factory {
        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomKotlinSerializationMessageAdapter<Any>(serializer)
        }
    }
}

// TODO(This File Need Edit)

//        var convertedData = data as BaseModel
//        convertedData = when(convertedData.type) {
//            TYPE_CHAT_MESSAGE -> convertedData as ChatMessage
//            TYPE_DRAW_DATA -> convertedData as DrawData
//            TYPE_ANNOUNCEMENT -> convertedData as Announcement
//            TYPE_JOIN_ROOM_HANDSHAKE -> convertedData as JoinRoomHandshake
//            TYPE_PHASE_CHANGE ->convertedData as PhaseChange
//            TYPE_CHOSEN_WORD -> convertedData as ChosenWord
//            TYPE_GAME_STATE -> convertedData as GameState
//            TYPE_PING -> convertedData as Ping
//            TYPE_DISCONNECT_REQUEST -> convertedData as DisconnectRequest
//            TYPE_DRAW_ACTION ->convertedData as DrawAction
//            TYPE_CUR_ROUND_DRAW_INFO -> convertedData as RoundDrawInfo
//            TYPE_GAME_ERROR -> convertedData as GameError
//            TYPE_NEW_WORDS -> convertedData as NewWords
//            TYPE_PLAYERS_LIST -> convertedData as PlayersList
//            else -> convertedData
//        }
//        return Message.Text(gson.toJson(convertedData))


// val jsonObject = JsonParser.parseString(stringValue).asJsonObject
//        val type = when(jsonObject.get("type").asString) {
//            TYPE_CHAT_MESSAGE -> ChatMessage::class.java
//            TYPE_DRAW_DATA -> DrawData::class.java
//            TYPE_ANNOUNCEMENT -> Announcement::class.java
//            TYPE_JOIN_ROOM_HANDSHAKE -> JoinRoomHandshake::class.java
//            TYPE_PHASE_CHANGE -> PhaseChange::class.java
//            TYPE_CHOSEN_WORD -> ChosenWord::class.java
//            TYPE_GAME_STATE -> GameState::class.java
//            TYPE_PING -> Ping::class.java
//            TYPE_DISCONNECT_REQUEST -> DisconnectRequest::class.java
//            TYPE_DRAW_ACTION -> DrawAction::class.java
//            TYPE_CUR_ROUND_DRAW_INFO -> RoundDrawInfo::class.java
//            TYPE_GAME_ERROR -> GameError::class.java
//            TYPE_NEW_WORDS -> NewWords::class.java
//            TYPE_PLAYERS_LIST -> PlayersList::class.java
//            else -> BaseModel::class.java
//        }
//        val obj = gson.fromJson(stringValue, type)
//        return obj as T