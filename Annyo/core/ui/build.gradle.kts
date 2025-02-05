plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
}

android {
    namespace = "com.example.ui"
}

dependencies {



    implementation(project(":core:utils"))

    implementation(libs.google.play.services) // Location Services
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)

    implementation("io.coil-kt:coil-gif:2.2.2")


    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.lifecycle.runtimeCompose)
    api(libs.androidx.lifecycle.viewModelCompose)
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.compose.constraintlayout)
    api(libs.accompanist.pagerlayout)
    api(libs.accompanist.flowlayout)
    
}  