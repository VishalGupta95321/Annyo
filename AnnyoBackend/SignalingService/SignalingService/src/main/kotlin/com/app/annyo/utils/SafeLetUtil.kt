package com.app.annyo.utils

suspend fun <T, U> safeLet(value1: T?, value2: U?, block: suspend (T, U) -> Unit) {
    if (value1 != null && value2 != null) {
        block(value1, value2)
    }
}

suspend fun <T, U, V> safeLet(value1: T?, value2: U?, value3: V?, block: suspend (T, U, V) -> Unit) {
    if (value1 != null && value2 != null && value3!=null) {
        block(value1, value2, value3)
    }
}