package com.annyo.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Auth interceptor ")
    }
}