plugins {
    id("annyo.android.library")
    id("annyo.android.feature")
}

android {
    namespace = "com.app.auth_domain"
}

dependencies {
    implementation(project(":core:firebase_service"))
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx)
}