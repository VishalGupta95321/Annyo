plugins {
    id("annyo.android.library")
    id("annyo.android.feature")
}

android {
    namespace = "com.app.auth_data"
}

dependencies {
    implementation(project(":core:firebase_service"))
    implementation(project(":features:auth_feature:auth_domain"))
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx) // don't know if needed
}