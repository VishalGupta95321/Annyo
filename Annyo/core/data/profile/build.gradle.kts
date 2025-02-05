plugins {
    id("annyo.android.library")
    id("annyo.hilt.android")
    id("kotlinx-serialization")
}

android {
    namespace = "com.app.core.data.profile"  /// fix this package name all over the project
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:firebase_service"))
    implementation(project(":core:domain:profile"))
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx)
}