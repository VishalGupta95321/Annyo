package com.example

import com.example.di.mainModule
import com.example.models.entity.*
import com.example.models.request.CreateProfileRequest
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

import org.koin.ktor.plugin.Koin


fun main() {

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    install(Koin) {
        modules(mainModule)
    }
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()

    val repo : UserRepository by inject()
    GlobalScope.launch {

        repo.createProfile(
            CreateProfileRequest(
                User(
                    name = Name("acscas","Ascsa"),
                    collegeName = "knejcankcd",
                    jobTitle = "alskmcmklsc",
                    workPlace = "dlnckkdc",
                    height = 0.0,
                    age = 20,
                    gender = "aclnkcdn",
                    religion = "alccd",
                    dob = Dob(1,2,3),
                    recommendationPreferences = RecommendationPreferences("lnkaccda","acca",2,3,9.0,"adnjc","lmacs","a sclk","ac;smk",0.0),
                    aboutChildren = "acmklac",
                    photos = listOf(Photo("ljn","cakj")) ,
                    writtenPrompts = listOf(WrittenPrompt("Asclnj","dac",)),
                    sexuality = "acdcdcv",
                    location = Location("ASca","ACS"),
                    smoking = "AScas",
                    drinking = "as;mkcaa",
                    emailId = "ASCm;lal;cm;ca",
                    uid = "Asck ;ca "
                )
            )
        )
    }
    /////////////////      TEST
}
