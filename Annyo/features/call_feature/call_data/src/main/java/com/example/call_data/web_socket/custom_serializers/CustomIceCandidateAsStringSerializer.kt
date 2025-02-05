package com.example.call_data.web_socket.custom_serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.webrtc.IceCandidate
import org.webrtc.PeerConnection

object CustomIceCandidateAsStringSerializer : KSerializer<IceCandidate> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Ice Candidate"){
        element<String>("adapterType")
        element<String>("sdp")
        element<String>("sdpMid")
        element<Int>("sdpMLineIndex")
        element<String>("serverUrl")

    }

    override fun serialize(encoder: Encoder, value: IceCandidate) {
        encoder.encodeStructure(descriptor){
            encodeStringElement(descriptor,0, value.sdp)
            encodeIntElement(descriptor,1 , value.sdpMLineIndex)
            encodeStringElement(descriptor,2, value.sdpMid)
            encodeStringElement(descriptor,3, value.serverUrl)
            encodeSerializableElement(descriptor,4, CustomIceCandidateAdapterTypeAsString ,value.adapterType)
        }
    }

    @Throws(SerializationException::class)
    override fun deserialize(decoder: Decoder): IceCandidate {
        return decoder.decodeStructure(descriptor){
            var adapterType : PeerConnection.AdapterType? = null
            var sdp: String? = null
            var sdpMid: String? = null
            var serverUrl: String? = null
            var sdpMLineIndex: Int? = null

            loop@ while (true){
                when(decodeElementIndex(descriptor)){
                    CompositeDecoder.DECODE_DONE -> break@loop
                    0 -> sdp = decodeStringElement(descriptor,0)
                    1 -> sdpMLineIndex = decodeIntElement(descriptor,1)
                    2 -> sdpMid = decodeStringElement(descriptor,2)
                    3 -> serverUrl = decodeStringElement(descriptor,3) /// NO Need For This
                    4 -> adapterType = decodeSerializableElement( /// NO Need For This
                        descriptor = descriptor,0,CustomIceCandidateAdapterTypeAsString)
                    else -> throw SerializationException()
                }
            }
            IceCandidate(
                sdpMid,sdpMLineIndex!!,sdp
            )
        }
    }
}

object CustomIceCandidateAdapterTypeAsString : KSerializer<PeerConnection.AdapterType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "PeerConnectionAdapterType",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: PeerConnection.AdapterType) {
        encoder.encodeString(value.bitMask.toString())
    }

    @Throws(SerializationException::class)
    override fun deserialize(decoder: Decoder): PeerConnection.AdapterType {
        val bitmask = decoder.decodeInt()
        return when(bitmask){
            0 -> PeerConnection.AdapterType.UNKNOWN
            1 -> PeerConnection.AdapterType.ETHERNET
            2 -> PeerConnection.AdapterType.WIFI
            4 -> PeerConnection.AdapterType.CELLULAR
            8 -> PeerConnection.AdapterType.VPN
            16 -> PeerConnection.AdapterType.LOOPBACK
            32 -> PeerConnection.AdapterType.ADAPTER_TYPE_ANY
            64 -> PeerConnection.AdapterType.CELLULAR_2G
            128 -> PeerConnection.AdapterType.CELLULAR_3G
            256 -> PeerConnection.AdapterType.CELLULAR_4G
            512 -> PeerConnection.AdapterType.CELLULAR_5G
            else -> throw SerializationException()
        }
    }
}
