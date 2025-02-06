package com.example.util

import com.example.exception.HttpErrors

sealed class Response<T>(val data: T? = null, val error : HttpErrors? = null) {
    class Success<T>(data: T?): Response<T>(data)
    class Error<T>(error : HttpErrors? = null): Response<T>(error = error)
}