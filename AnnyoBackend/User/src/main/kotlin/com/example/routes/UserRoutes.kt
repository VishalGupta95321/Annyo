package com.example.routes

import com.example.models.request.*
import com.example.models.responce.ProfileResponse
import com.example.models.responce.UserApiResponse
import com.example.repository.UserRepository
import com.example.util.QueryParameters
import com.example.util.generateHttpResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createProfile(userRepository: UserRepository){

    post("/create") {

        val createProfileRequest = runCatching {
             call.receive<CreateProfileRequest>()
        }.getOrElse {null} ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val response = generateHttpResponse(
            userRepository.createProfile(createProfileRequest)
        )

        call.respond(response.code,response.body?:"")
    }
}

fun Route.updateProfile(userRepository: UserRepository){

    route("/update") {

        patch("/basic") {

            val uid = call.parameters[QueryParameters.UID] ?: return@patch

            val basicUpdateRequest = runCatching {
                call.receive<BasicUpdateRequest>()
            }.getOrElse {null} ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }
           val response = generateHttpResponse(
               userRepository.updateBasicProfile(uid,basicUpdateRequest)
           )
            call.respond(response.code,response.body?:"")
        }


        patch("/dob") {

            val uid = call.parameters[QueryParameters.UID] ?: return@patch

            val updateDobRequest = runCatching {
                call.receive<UpdateDobRequest>()
            }.getOrElse {null} ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }
            val response = generateHttpResponse(
                userRepository.updateDob(uid,updateDobRequest)
            )

            call.respond(response.code,response.body?:"")
        }


        patch("/location") {

            val uid = call.parameters[QueryParameters.UID] ?: return@patch

            val updateLocationRequest = runCatching {
                call.receive<UpdateLocationRequest>()
            }.getOrElse {null} ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }

            val response = generateHttpResponse(
                userRepository.updateLocation(uid,updateLocationRequest)
            )

            call.respond(response.code,response.body?:"")
        }

        patch("/preferences") {

            val uid = call.parameters[QueryParameters.UID] ?: return@patch

            val updateRecommendationPreferencesRequest = runCatching {
                call.receive<UpdateRecommendationPreferencesRequest>()
            }.getOrElse {null} ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }

            val response  = generateHttpResponse(
                userRepository.updateRecommendationPreferences(uid,updateRecommendationPreferencesRequest)
            )

            call.respond(response.code,response.body?:"")
        }

        patch("/photo") {

            val uid = call.parameters[QueryParameters.UID] ?: return@patch

            val updatePhotoRequest = runCatching {
                call.receive<UpdatePhotoRequest>()
            }.getOrElse {null} ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }

            val response  = generateHttpResponse(
                userRepository.updatePhoto(uid,updatePhotoRequest)
            )

            call.respond(response.code,response.body?:"")
        }

        route("/prompt") {

            patch("/written") {

                val uid = call.parameters[QueryParameters.UID] ?: return@patch

                val updateWrittenPromptRequest = runCatching {
                    call.receive<UpdateWrittenPromptRequest>()
                }.getOrElse {null} ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@patch
                }

                val response = generateHttpResponse(
                    userRepository.updateWrittenPrompt(uid,updateWrittenPromptRequest)
                )

                call.respond(response.code,response.body?:"")
            }
        }
    }
}

fun Route.getProfile(userRepository: UserRepository){
    get("/profile") {

        val uid = call.parameters[QueryParameters.UID] ?: return@get

        val response  = generateHttpResponse(
            userRepository.getUserProfile(uid)
        )
        call.respond(response.code,response.body?:"")
    }
}

fun Route.getProfilesByCellId(userRepository: UserRepository){
    get("/profiles/{s2CellId}") {

        val s2cellId = call.parameters[QueryParameters.S2_CELL_ID] ?: return@get

        val response = generateHttpResponse(
            userRepository.getUserProfiles(s2cellId)
        )
        call.respond(response.code,response.body?:"")
    }
}

fun Route.deleteProfile(userRepository: UserRepository){
    delete("/delete"){
        val uid = call.parameters[QueryParameters.UID] ?: return@delete

        val response = generateHttpResponse(
        userRepository.deleteUserProfile(uid)
        )

        call.respond(response.code,UserApiResponse(data = response.body))
    }

}
