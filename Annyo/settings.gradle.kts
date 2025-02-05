pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Annyo"
include(":app")
include(":core:signal_service")
include(":features:auth_feature:auth_presentation")
include(":features:auth_feature:auth_data")
include(":features:auth_feature:auth_domain")
include(":features:onboarding_feature:onboarding_presentation")
include(":core:utils")
include(":core:firebase_service")
include(":core:ui")
include(":features:profile_feature:profile_presentation")
include(":features:like_feature:like_presentation")
include(":features:notification_feature:notification_presentation")
include(":features:matches_feature:matches_presentation")
include(":features:chat_feature:chat_presentation")
include(":features:membership_feature:membership_presentation")
include(":features:setting_feature:setting_presentation")
include(":features:settings_feature:setting_presentation")
include(":features:find_match_feature:find_match_presentation")
include(":features:quiz_feature:quiz_presentation")
include(":core:datastore")
include(":core:network")
include(":core:data:profile")
include(":core:data:settings")
include(":core:data:membership")
include(":core:data:quiz")
include(":core:domain:profile")
include(":core:domain:setting")
include(":core:domain:membership")
include(":features:call_feature:call_data")
include(":features:call_feature:call_presentation")
include(":features:call_feature:call_domain")
include(":core:notification")
