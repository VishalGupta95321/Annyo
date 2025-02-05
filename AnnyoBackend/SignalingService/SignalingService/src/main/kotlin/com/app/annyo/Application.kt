package com.app.annyo

import com.app.annyo.di.MainModule
import com.app.annyo.data.entity.User
import com.app.annyo.plugins.*
import com.app.annyo.data.entity.CallSession
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import java.util.concurrent.ConcurrentHashMap



val users = ConcurrentHashMap<String, User>()  /// both will go in data folder
val callSessions = ConcurrentHashMap<String, CallSession>() // TODO(Use database)
/// TODO(provide all the above them using Koin)


fun main() {

    embeddedServer(Netty, port = 8085, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(MainModule)
    }
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}
