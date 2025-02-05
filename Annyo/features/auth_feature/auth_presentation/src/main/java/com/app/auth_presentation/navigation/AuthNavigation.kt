package com.app.auth_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.auth_presentation.otp_login.otp_login_screen.OtpLogInScreen
import com.app.auth_presentation.register_screen.RegisterScreen

const val authGraph = "auth_graph"


fun NavController.navigateToAuthGraph(navOptions: NavOptions? = null){
    this.navigate(authGraph,navOptions)
}

fun NavGraphBuilder.authNavGraph(
    navigateToOnboarding : () -> Unit ,
    navigateToOtpLoginScreen : () -> Unit ,
    navigateUp : () -> Unit
){

    navigation(
        route = authGraph,
        startDestination = AuthScreens.RegisterScreen.route,
    ){
        composable(route = AuthScreens.RegisterScreen.route){
            RegisterScreen(
                navigateToOnboarding = navigateToOnboarding,
                navigateToOtpLoginScreen = navigateToOtpLoginScreen,
                navigateUp = navigateUp
            )
        }
        composable(route = AuthScreens.OtpLoginScreen.route){
            OtpLogInScreen(
                navigateToOnboarding = navigateToOnboarding,
                navigateUp = navigateUp ,
            )
        }
    }
}

sealed class AuthScreens(
    val route : String
){
    object RegisterScreen : AuthScreens("register_screen")
    object OtpLoginScreen : AuthScreens("otp_login_screen")
}