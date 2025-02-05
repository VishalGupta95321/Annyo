plugins {
    id("annyo.android.library")
    id("annyo.android.library.compose")
    id("annyo.android.feature")
    id("kotlinx-serialization")
}

android {
    namespace = "com.app.call_data"
}

dependencies {
    implementation(project(":features:call_feature:call_domain"))
    implementation( project(":core:network"))
    implementation(project(":core:firebase_service"))
    implementation(files("libs/libwebrtc.aar"))
    implementation(libs.androidx.test.ext)

    // TODO(Scarlet library Yet to put in build logic or toml file )
    val scarletVersion = "0.1.12"

    implementation ("com.tinder.scarlet:scarlet:$scarletVersion")
    implementation ("com.tinder.scarlet:websocket-okhttp:$scarletVersion")
    implementation ("com.tinder.scarlet:lifecycle-android:$scarletVersion")
    implementation ("com.tinder.scarlet:message-adapter-gson:$scarletVersion")
    implementation ("com.tinder.scarlet:stream-adapter-coroutines:$scarletVersion")

}