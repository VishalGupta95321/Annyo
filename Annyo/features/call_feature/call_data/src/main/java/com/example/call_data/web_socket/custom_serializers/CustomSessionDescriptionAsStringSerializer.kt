package com.example.call_data.web_socket.custom_serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.webrtc.SessionDescription

object CustomSessionDescriptionAsStringSerializer: KSerializer<SessionDescription> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "SessionDescription",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: SessionDescription) {
        encoder.encodeString("${value.type.name}::${value.description}")
    }

    @Throws(SerializationException::class)
    override fun deserialize(decoder: Decoder): SessionDescription {
        val (type,value) = decoder.decodeString().split("::")
        return sessionDescTypeToSessionDescription(type,value)
    }

    @Throws(SerializationException::class)
    private fun sessionDescTypeToSessionDescription(
        type: String,
        value: String
    ): SessionDescription {
        return when(type){
            "ANSWER" -> SessionDescription(SessionDescription.Type.ANSWER,value)
            "PRANSWER" -> SessionDescription(SessionDescription.Type.PRANSWER,value)
            "OFFER" -> SessionDescription(SessionDescription.Type.OFFER,value)
            "ROLLBACK" -> SessionDescription(SessionDescription.Type.ROLLBACK,value)
            else ->  throw SerializationException()
        }
    }
}


