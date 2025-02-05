package com.example.onboarding_presentation.primary_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingAboutMe
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingChildren
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingCollege
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingDOB
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingDrinking
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingEthnicity
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingGender
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingHeight
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingInterests
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingJob
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingLanguages
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingLocation
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingName
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingPets
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingPhotos
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingReligion
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingSexuality
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingSmoking
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingWorkout
import com.example.onboarding_presentation.primary_screen.OnboardingUiState.OnboardingZodiacSign
import com.example.onboarding_presentation.secondary_screens.about_me.AboutMeScreen
import com.example.onboarding_presentation.secondary_screens.children.ChildrenScreen
import com.example.onboarding_presentation.secondary_screens.college.CollegeScreen
import com.example.onboarding_presentation.secondary_screens.date_of_birth.DateOfBirthScreen
import com.example.onboarding_presentation.secondary_screens.drinking.DrinkingScreen
import com.example.onboarding_presentation.secondary_screens.ethnicity.EthnicityScreen
import com.example.onboarding_presentation.secondary_screens.gender.GenderScreen
import com.example.onboarding_presentation.secondary_screens.height.HeightScreen
import com.example.onboarding_presentation.secondary_screens.interests.InterestsScreen
import com.example.onboarding_presentation.secondary_screens.job.JobScreen
import com.example.onboarding_presentation.secondary_screens.languages.LanguagesScreen
import com.example.onboarding_presentation.secondary_screens.location.LocationScreen
import com.example.onboarding_presentation.secondary_screens.name.NameScreen
import com.example.onboarding_presentation.secondary_screens.pets.PetsScreen
import com.example.onboarding_presentation.secondary_screens.photos.PhotosScreen
import com.example.onboarding_presentation.secondary_screens.religion.ReligionScreen
import com.example.onboarding_presentation.secondary_screens.sexuality.SexualityScreen
import com.example.onboarding_presentation.secondary_screens.smoking.SmokingScreen
import com.example.onboarding_presentation.secondary_screens.workout.WorkoutScreen
import com.example.onboarding_presentation.secondary_screens.zodiac_sign.ZodiacSignScreen

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    val onboardingState by  viewModel.onboardingUiState.collectAsState()
    val profileUpdateState by viewModel.profileUploadState.collectAsState()

    when(profileUpdateState){
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    OnBoardingAnimation(
        onBoardingUiState = onboardingState
    ) { targetState ->
        when (targetState) {
            OnboardingAboutMe -> AboutMeScreen(  // todo where i left
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingLanguages) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingPhotos) }
            )
            OnboardingChildren -> ChildrenScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingLocation) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingSmoking) }
            )
            OnboardingCollege -> CollegeScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingZodiacSign) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingJob) }
            )
            OnboardingDOB -> DateOfBirthScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingSexuality) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingHeight) }
            )
            OnboardingDrinking -> DrinkingScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingSmoking) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingWorkout) }
            )
            OnboardingEthnicity -> EthnicityScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingHeight) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingReligion) }
            )
            OnboardingGender -> GenderScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingName) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingSexuality) }
            )
            OnboardingHeight -> HeightScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingDOB) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingEthnicity) }
            )
            OnboardingInterests -> InterestsScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingPets) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingLanguages) }
            )
            OnboardingJob -> JobScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingCollege) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingLocation) }
            )
            OnboardingLanguages -> LanguagesScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingInterests) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingAboutMe) }
            )
            OnboardingLocation -> LocationScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingJob) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingChildren) }
            )
            OnboardingName -> NameScreen(
                onBackClick = {}, //// todo Dialog of cancelling the  process and exit from the app
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingGender) }
            )
            OnboardingPets -> PetsScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingWorkout) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingInterests) }
            )
            OnboardingPhotos -> PhotosScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingAboutMe) },
                onNextCLick = { viewModel.createProfile() }
            )
            OnboardingReligion -> ReligionScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingEthnicity) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingZodiacSign) }
            )
            OnboardingSexuality -> SexualityScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingGender) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingDOB) }
            )
            OnboardingSmoking -> SmokingScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingChildren) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingDrinking) }
            )
            OnboardingWorkout -> WorkoutScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingDrinking) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingPets) }
            )
            OnboardingZodiacSign -> ZodiacSignScreen(
                onBackClick = { viewModel.onOnboardingUiStateChange(OnboardingReligion) },
                onNextCLick = { viewModel.onOnboardingUiStateChange(OnboardingCollege) }
            )
        }
    }
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardingAnimation(
    onBoardingUiState: OnboardingUiState,
    content: @Composable (targetState: OnboardingUiState) -> Unit,
) {
    AnimatedContent(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        targetState = onBoardingUiState,
        transitionSpec = {
            if (targetState.screenOrder > initialState.screenOrder) {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 500, easing = LinearOutSlowInEasing
                    )
                ) { width -> (width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> -width } + fadeOut()
            } else {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ) { width -> -(width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> (width * 2) } + fadeOut()
            }
        }, label = ""
    ) { targetState ->
        content(targetState)
    }
}
