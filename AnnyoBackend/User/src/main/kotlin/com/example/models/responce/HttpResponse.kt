package com.example.models.responce

import io.ktor.http.*

data class HttpResponse<T>(
    val code: HttpStatusCode,
    val body: T? = null,
)
