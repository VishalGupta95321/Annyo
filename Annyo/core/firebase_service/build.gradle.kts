
plugins {
    id("annyo.android.library")
    id("annyo.google.firebase")
    id("annyo.hilt.android")
}

android {
    namespace = "com.annyo.firebase_service"
}

dependencies {
    implementation(project(":core:utils"))
    api(libs.firebase.auth)
    api(libs.firebase.storage)
    api(libs.firebase.messaging)
    implementation(libs.google.service.auth)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.test.ext)
}