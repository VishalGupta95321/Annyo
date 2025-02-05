plugins {
    `kotlin-dsl`
}

group = "com.app.annyo"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose"){
            id = "annyo.android.application.compose"
            implementationClass = "AndroidAppComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "annyo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "annyo.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "annyo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "annyo.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("hiltAndroid") {
            id = "annyo.hilt.android"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("firebase"){
            id = "annyo.google.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }
    }
}
