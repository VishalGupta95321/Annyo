plugins {
    id("annyo.android.application")
    id("annyo.android.application.compose")
    id("annyo.hilt.android")
}

android {
    namespace  = "com.app.annyo"

    defaultConfig {
        applicationId = "com.app.annyo"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    //    implementation(project(":features:auth_feature:auth_presentation"))
//    implementation(project(":features:auth_feature:auth_domain"))
//    implementation(project(":features:auth_feature:auth_data"))
//    implementation(project(":features:onboarding_feature:onboarding_presentation"))
//    implementation(project(":features:profile_feature:profile_presentation"))
    implementation(project(":features:call_feature:call_data"))
    implementation(project(":features:call_feature:call_domain"))
    implementation(project(":features:call_feature:call_presentation"))


    implementation(project(":core:firebase_service"))
    implementation(project(":core:datastore"))
    implementation(project(":core:utils"))
    implementation(project(":core:notification"))
    implementation(project(":core:ui"))
//    implementation(project(":core:domain:profile"))
//    implementation(project(":core:data:profile"))

    implementation(libs.kotlinx.datetime)

    // implementation(libs.firebase.crashlytics)


    implementation(libs.coil.kt)
    implementation("io.coil-kt:coil-gif:2.2.2")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose) // for navigation
    implementation(libs.androidx.navigation.compose) //  for navigation
    implementation(libs.androidx.compose.material3.windowSizeClass)
}