package com.example.call_presentation

import androidx.compose.runtime.Composable
//import com.example.call_data.web_socket.test_serialization
import com.example.ui.StandardButton
//import kotlinx.serialization.Serializable

@Composable
fun Call(onClick:()->Unit) {

    StandardButton(onClick = {
        onClick()
//        val a  = SignalingEvent.OnAnswer("d")
//        val b  = a as SignalingEvent
//        when(b){
//            is SignalingEvent.OnAnswer -> println("From On Answer")
//            else-> "Eo=efefriokfnwed"
//        }
//       // @Serializable
//        data class c (val a: String)
//
//        val d = c("jhsd")
//        println("B"+ b)
//        val str = Json.encodeToString(a)
//        val str1 = Json.parseToJsonElement(str).jsonObject
//        val str2 = str1["sessionDescription"]
//        println(str2)
//        println(str1)
//        val obj = Json.decodeFromString<SignalingEvent.OnAnswer>(str)
//        println(obj)

       // test_serialization()
    }

    ) {

    }
}

//sealed interface SignalingEvent {
//
//    @Serializable
//    data class OnOffer (
//        val sessionDescription: String
//    ):SignalingEvent
//
//    @Serializable
//    data class OnAnswer (
//        val sessionDescription:String
//    ):SignalingEvent
//
//    data class OnIceCandidate (
//        val candidate :String
//    ):SignalingEvent
//
//}

