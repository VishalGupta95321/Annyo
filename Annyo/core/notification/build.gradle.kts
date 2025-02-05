plugins {
    id("annyo.android.library")
    id("annyo.hilt.android")
}

android {
    namespace = "com.annyo.core.notification"
}

dependencies {
    implementation(project(":core:firebase_service"))
    implementation(libs.androidx.core.ktx)
}