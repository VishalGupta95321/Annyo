package com.example.onboarding_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.onboarding_presentation.primary_screen.OnboardingScreen

const val onboardingGraph = "onboarding_graph"


fun NavController.onboardingNavGraph(navOptions: NavOptions? = null) {
    this.navigate(OnBoardingScreens.OnBoardingScreen.route)
}

fun NavGraphBuilder.onBoardingNavGraph() {
    navigation(
        route = onboardingGraph,
        startDestination = OnBoardingScreens.OnBoardingScreen.route
    ) {
        composable(route = OnBoardingScreens.OnBoardingScreen.route) {
            OnboardingScreen()
        }
    }
}


sealed class OnBoardingScreens(val route: String) {
    object OnBoardingScreen : OnBoardingScreens("onBoardingScreen")
}