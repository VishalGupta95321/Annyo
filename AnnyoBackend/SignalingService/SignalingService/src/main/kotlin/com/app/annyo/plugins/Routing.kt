package com.app.annyo.plugins

import com.app.annyo.controller.SignallingController
import com.app.annyo.routes.signallingWebSocketRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject


fun Application.configureRouting() {
    val signallingController: SignallingController by inject()
    val json: Json by inject()

//    val mm : FirebaseMessagingClient by inject()
//    GlobalScope.launch {
//        mm.sendDataMessage("chJ8HieJRVWpBISjNHC7b9:APA91bGkLez0mT6GPHcq_jBt_hyi8P02Ea4R0wHOefjOd83aNu4slUJMA6i2J41EMu6mVIm1NVrllD4nf1dgAw-5UojtlHw50TvQKaukEaPoS4bgHKbHpwAwhqi9W2Gf84mPPuSP0ySJ",MessageDataType.Call(" qsqqs"))
//        mm.sendDataMessage("c1M6EX1kTWK1RnmO8L8cGP:APA91bHpYBKrj1j7etp38p5z8D4pQ6xMyqUhLuq34pLcdjjE4TvimBv6ohmZVTTamNSzcYS95Wzb7jNEWbgxyHTL_bRTLCUF0cFRj5tB2WU7Ex-kzVxic5TrGqqTM3OxN7cs82cV719X",MessageDataType.Call("3"))
//
//    } /// fixme
    install(Routing) {
        signallingWebSocketRoute(signallingController,json)
    }
}