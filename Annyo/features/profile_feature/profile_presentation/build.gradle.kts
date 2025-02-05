plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
    id("annyo.android.feature")
}

android {
    namespace = "com.example.profile_presentation"
}

dependencies {
    implementation(project(":core:domain:profile"))
    implementation(libs.androidx.test.ext)
}