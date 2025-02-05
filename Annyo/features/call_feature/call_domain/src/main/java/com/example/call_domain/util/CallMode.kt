package com.example.call_domain.util

sealed interface CallMode {
    data object VideoCall : CallMode
    data object AudioCall : CallMode
}