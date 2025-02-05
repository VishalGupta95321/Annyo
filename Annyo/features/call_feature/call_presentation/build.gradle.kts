plugins {
    id("annyo.android.library.compose")
    id("annyo.android.feature")
}

android {
    namespace = "com.app.call_presentation"
}

dependencies {
    implementation(project(":features:call_feature:call_domain"))
    implementation( project(":core:network"))
    implementation( project(":core:notification"))

    implementation(files("libs/libwebrtc.aar"))
}
