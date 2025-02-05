plugins {
    id("annyo.android.library")
}

android {
    namespace = "com.example.utils"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx)
}