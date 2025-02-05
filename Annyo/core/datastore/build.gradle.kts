plugins {
    id("annyo.android.library")
    id("annyo.hilt.android")
}

android {
    namespace = "com.annyo.datastore"  // correct the name like com.annyo.core.etc
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.androidx.test.ext)
}