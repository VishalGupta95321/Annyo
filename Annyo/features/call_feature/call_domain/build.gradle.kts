plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
    id("annyo.android.feature")
}

android {
    namespace = "com.app.call_domain"
}

dependencies {
    implementation(libs.androidx.test.ext)
    implementation(files("libs/libwebrtc.aar"))
}