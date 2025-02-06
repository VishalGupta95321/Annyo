package com.example.exception

import io.ktor.http.*

sealed class HttpErrors(val code: HttpStatusCode) {
    object SomethingWentWrong:HttpErrors(HttpStatusCode(415,"SomethingWentWrong"))
}