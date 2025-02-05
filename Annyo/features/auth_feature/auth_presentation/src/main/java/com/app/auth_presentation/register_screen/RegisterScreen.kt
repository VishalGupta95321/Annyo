package com.app.auth_presentation.register_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import com.annyo.firebase_service.util.GoogleAuthResultContract
import com.app.auth_presentation.R
import com.app.auth_presentation.register_screen.utils.LayoutIdRegisterScreen
import com.app.auth_presentation.register_screen.utils.RegisterScreenEvents
import com.example.ui.AppLogo
import com.example.ui.LogoSize
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToOtpLoginScreen: () -> Unit,
    navigateUp: () -> Unit
) {

    val loginState = viewModel.registerLoginState

    val authResultLauncher = rememberLauncherForActivityResult( // todo: must be done in activity
        contract = GoogleAuthResultContract()
    ) { task ->
        try {
            val account = task?.result
            if (account!=null){
                viewModel.onEvent(
                    RegisterScreenEvents.LoginUsingGoogle(
                        GoogleAuthProvider.getCredential(account.idToken,null)
                    )
                )
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    RegisterScreenMotionLayout(
        onGoogleSignInClick = {
          authResultLauncher.launch(1)
        },
        onPhoneSignInClick = {
            navigateToOtpLoginScreen() /// todo as of now everything related to navigation is just for testing. Just ignore things related to naviagtion like parametesrs in viemodel; or navigate functions called in composables
        }
    )

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun RegisterScreenMotionLayout(
    onGoogleSignInClick: () -> Unit,
    onPhoneSignInClick: () -> Unit
) {

    val context = LocalContext.current

//    val isAuthenticated by remember {
//        mutableStateOf(false)
//    }

    val animationProgress by animateFloatAsState(
        targetValue =  1f ,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    val motionScene = remember {
        context
            .resources.openRawResource(
                R.raw.register_screen_motion_scene
            )
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = animationProgress,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .layoutId(LayoutIdRegisterScreen.Container.id)
        ) {}

      //  if (isAuthenticated)
            BackgroundImageRegisterScreen(
                layoutId = LayoutIdRegisterScreen.BackgroundImage,
                drawable = R.drawable.auth_screen_background
            )

        AppLogoRegisterScreen(
            layoutId = LayoutIdRegisterScreen.Logo,
            logoSize = //if (isAuthenticated)
                LogoSize.SMALL
            //else
             //   LogoSize.NORMAL
        )

       // if (isAuthenticated)
            LoginButtonRegisterScreen(
                layoutId = LayoutIdRegisterScreen.GoogleLoginButton,
                text = "continue with Google",
                logo = R.drawable.google_logo
            ) { onGoogleSignInClick() }

      //  if (isAuthenticated)
            LoginButtonRegisterScreen(
                layoutId = LayoutIdRegisterScreen.PhoneLoginButton,
                text = "continue with Phone number",
                logo = R.drawable.phone_icon
            ) { onPhoneSignInClick() }
    }
}


@Composable
fun LoginButtonRegisterScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdRegisterScreen,
    text: String,
    @DrawableRes logo: Int,
    onCLick: () -> Unit
) {
    Button(
        modifier = Modifier
            .layoutId(layoutId.id)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(35.dp),
        onClick = onCLick
    ) {
        when (layoutId) {
            LayoutIdRegisterScreen.PhoneLoginButton -> {
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp),
                    imageVector = ImageVector.vectorResource(id = logo),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
            LayoutIdRegisterScreen.GoogleLoginButton -> {
                Image(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp),
                    painter = painterResource(id = logo),
                    contentDescription = null,
                )
            }
            else -> {}
        }
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.primaryContainer),
            color = MaterialTheme.colorScheme.primaryContainer,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun AppLogoRegisterScreen(
    modifier: Modifier = Modifier,
    logoSize: LogoSize,
    layoutId: LayoutIdRegisterScreen,
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .layoutId(layoutId.id)
            .then(modifier)
    ) {
        AppLogo(
            logoSize = logoSize
        )
    }
}

@Composable
fun BackgroundImageRegisterScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdRegisterScreen,
    @DrawableRes drawable: Int
) {
    Image(
        modifier = Modifier
            .layoutId(layoutId.id)
            .fillMaxSize()
            .padding(top = 30.dp)
            .then(modifier),
        painter = painterResource(id = drawable),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

