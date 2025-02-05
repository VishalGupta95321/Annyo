plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
    id("annyo.android.feature")
}

android {
    namespace = "com.app.auth_presentation"
}

dependencies {
    implementation(project(":features:auth_feature:auth_domain"))
    implementation(project(":core:firebase_service"))
    implementation(libs.androidx.test.ext)
}