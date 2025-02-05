plugins {
    id("annyo.android.library")
    id("annyo.hilt.android")
}

android {
    namespace = "com.app.core.domain.profile"  /// fix this package name all over the project
}

dependencies {
    implementation(project(":core:utils"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx)
}