package com.example.plugins

import com.example.repository.UserRepository
import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userRepository: UserRepository by inject()

    routing {
        createProfile(userRepository)
        updateProfile(userRepository)
        getProfile(userRepository)
        getProfilesByCellId(userRepository)
        deleteProfile(userRepository)
    }
}
