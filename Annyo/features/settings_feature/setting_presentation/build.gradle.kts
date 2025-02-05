plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
    id("annyo.android.feature")

}

android {
    namespace = "com.app.setting_presentation"
}

dependencies {
    implementation(libs.androidx.test.ext)
}