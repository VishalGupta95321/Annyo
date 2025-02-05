package com.app.annyo.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*

fun Application.configureMonitoring() {
    install(CallLogging) {
      //  level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
