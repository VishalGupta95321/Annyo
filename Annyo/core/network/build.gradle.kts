plugins {
    id("annyo.android.library")
    id("annyo.hilt.android")
    id("kotlinx-serialization")
}

android {
    namespace = "com.annyo.network"
}

dependencies {
    api(libs.kotlinx.serialization.json)
    api(libs.retrofit.core)
    api(libs.okhttp.logging)
    api(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.coroutines.android)
}