package com.example.call_data.web_socket

import com.example.call_data.web_socket.custom_serializers.CustomIceCandidateAsStringSerializer
import com.example.call_data.web_socket.custom_serializers.CustomSessionDescriptionAsStringSerializer
import kotlinx.serialization.Serializable
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

/// working
@Serializable
data class OnOffer (
    @Serializable(with = CustomSessionDescriptionAsStringSerializer::class)
    val sessionDescription: SessionDescription,
    @Serializable(with = CustomIceCandidateAsStringSerializer::class)
    val iceCan: IceCandidate
)

/// working
fun test_serialization(){
//    val a  = OnOffer(
//        SessionDescription(SessionDescription.Type.ANSWER,"iguowdw"),
//        IceCandidate("ds",1,"'assa")
//    )
//    val str = Json.encodeToString(a)
//    val str1 = Json.parseToJsonElement(str).jsonObject
//    val str3 = Json.decodeFromString<OnOffer>(str)
//    println(str3)
//
//    println("desc"+str3.sessionDescription.description)
//    println("type"+str3.sessionDescription.type)
//    println("sdpmid"+str3.iceCan.sdpMid)
//    println("sdplineIndex"+str3.iceCan.sdpMLineIndex)
//
//    val str2 = str1["sessionDescription"]
//    println(str2)
//    println(str1)



//    val signalingEvent = SignalingEventReqRes.OnOffer(SessionDescription(SessionDescription.Type.OFFER,
//        "edfuighbdc"),"iouhoih","oihuerf")
//    val str6 = Json.encodeToString(signalingEvent)
//    val str5 = Json.decodeFromString<SignalingEventReqRes.OnOffer>(str6)
//
//    val str7 = signalingEvent as SignalingEventReqRes
//    println(str7)
//    val str8 = signalingEvent as SignalingEventReqRes.OnOffer
//    println(str8)
//    val jsonObject = Json.parseToJsonElement(str6).jsonObject
//    println(jsonObject["eventType"].toString())
//
//    println(signalingEvent)
//    println(str6)
//    println(str5)
//    println(str5.sessionDescription.description)
//    println(str5.sessionDescription.type)





//    val obj = Json.decodeFromString<SignalingEvent.OnAnswer>(str)
//    println(obj)
}