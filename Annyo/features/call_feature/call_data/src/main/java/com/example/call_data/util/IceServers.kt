package com.example.call_data.util

sealed class IceServers(val uri : String ){

    object DefaultGoogleStunServer:IceServers(
        "stun:stun.l.google.com:19302"
    )
    // TODO Add the server Url after Server Hosting
    object CoturnStunTurnServer:IceServers(
        "stun:stun.l.google.com:19302"
    )
}