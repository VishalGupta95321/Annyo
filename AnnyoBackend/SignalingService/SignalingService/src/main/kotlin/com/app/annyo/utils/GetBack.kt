package com.example.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

typealias GetBackBasic  = GetBack<Nothing>

sealed interface GetBack<out T> {
    data class Success<T>(val data: T? = null) : GetBack<T>
    data class Error(val message : String? = null) : GetBack<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<GetBack<T>> {
    return this
        .map<T, GetBack<T>> {
            GetBack.Success(it)
        }
        .catch { emit(GetBack.Error(it.message)) }
}
