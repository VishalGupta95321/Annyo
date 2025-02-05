package com.example.profile_presentation.secondary_screens.edit

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profile.model.AboutMe
import com.example.profile.model.Children
import com.example.profile.model.College
import com.example.profile.model.DateOfBirth.Companion.monthFromInt
import com.example.profile.model.DateOfBirth.Companion.monthFromString
import com.example.profile.model.Drinking
import com.example.profile.model.Ethnicity
import com.example.profile.model.Gender
import com.example.profile.model.Height
import com.example.profile.model.Job
import com.example.profile.model.Languages
import com.example.profile.model.Location
import com.example.profile.model.Name
import com.example.profile.model.Pets
import com.example.profile.model.Photo
import com.example.profile.model.Religion
import com.example.profile.model.Sexuality
import com.example.profile.model.Smoking
import com.example.profile.model.Workout
import com.example.profile.model.ZodiacSign
import com.example.profile_presentation.R
import com.example.profile_presentation.secondary_screens.edit.states.ProfileEditScreenState
import com.example.profile_presentation.secondary_screens.edit.states.ProfileState
import com.example.profile_presentation.secondary_screens.edit.states.ProfileUpdateState
import com.example.screens.AboutMeAddScreen
import com.example.screens.ChildrenAddScreen
import com.example.screens.CollegeAddScreen
import com.example.screens.DateOfBirthAddScreen
import com.example.screens.DrinkingAddScreen
import com.example.screens.EthnicityAddScreen
import com.example.screens.GenderAddScreen
import com.example.screens.HeightAddScreen
import com.example.screens.InterestsAddScreen
import com.example.screens.JobAddScreen
import com.example.screens.LanguagesAddScreen
import com.example.screens.LocationAddScreen
import com.example.screens.NameAddScreen
import com.example.screens.PetAddScreen
import com.example.screens.ReligionAddScreen
import com.example.screens.SexualityAddScreen
import com.example.screens.SmokingAddScreen
import com.example.screens.WorkoutAddScreen
import com.example.screens.ZodiacSignAddScreen
import com.example.ui.Chips
import com.example.ui.StandardButton
import com.example.ui.userInput.PhotoInput

@Composable
fun ProfileEditScreenRoute(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {

    val activity = LocalContext.current as Activity
    val scrollState = rememberScrollState()
    var isDoneButtonEnabled  by  remember{
        mutableStateOf(true)
    }

    val profileState by viewModel.profileState.collectAsState()
    val profileUpdateState by viewModel.profileUpdateState.collectAsState()
    val editScreensState by viewModel.editScreensState.collectAsState()

    val aboutMeTextFieldState by viewModel.aboutMeTextFieldState.collectAsState()
    val jobTextFieldState by viewModel.jobTextFieldState.collectAsState()
    val collegeNameTextFieldState by viewModel.collegeTextFieldState.collectAsState()
    val langSearchTextFieldState by viewModel.langSearchQuery.collectAsState()               ///// by using single view model even if write anything in the about me text filed the whole composable is recomposed
    val addInterestTextFieldState by viewModel.addInterestTextFieldState.collectAsState()

    val backToProfileEditScreen = {
        viewModel.onScreenStateChange(
            ProfileEditScreenState.NotEditing
        )
    }

    when(profileUpdateState){

        ProfileUpdateState.NotUpdating -> {
            isDoneButtonEnabled = true
        }
        is ProfileUpdateState.UpdateFailed -> {
            isDoneButtonEnabled = true
            //// show snack bar if you want
        }
        ProfileUpdateState.Updated -> {
            isDoneButtonEnabled = true
        }
        ProfileUpdateState.Updating -> {
            isDoneButtonEnabled = false
        }
    }
    
    when (val state = profileState) {

        is ProfileState.Error -> {

        }
        ProfileState.Loading -> {

        }
        is ProfileState.Success -> {

            state.profile.run {

                ProfileEditScreenAnimation(state = editScreensState) { targetState ->

                    when (targetState) {

                        ProfileEditScreenState.NotEditing -> {
                            ProfileEditScreen(
                                modifier = modifier,
                                scrollState = scrollState,
                                profileState = profileState,
                                onEditPhotoClick = { photoCount, uri ->
                                    viewModel.updateProfile(Photo(uri.toString(), photoCount))
                                },
                                onEditClick = { viewModel.onScreenStateChange(it) },
                                onBackClick = navigateUp
                            )
                        }

                        is ProfileEditScreenState.EditAboutMe -> {
                            AboutMeAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                text = aboutMeTextFieldState,
                                onValueChange = { viewModel.updateProfile(AboutMe(it)) },
                                onBackClick = backToProfileEditScreen, 
                                onDoneClick = backToProfileEditScreen,
                            )
                        }
                        is ProfileEditScreenState.EditChildren -> {
                            ChildrenAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = children.choice,
                                onSelect = { viewModel.updateProfile(Children(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditCollege -> {
                            CollegeAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                collegeName = collegeNameTextFieldState,
                                onValueChange = { viewModel.updateProfile(College(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditDOB -> {
                            DateOfBirthAddScreen(
                                day = dateOfBirth.day,
                                month = monthFromInt(dateOfBirth.month),
                                year = dateOfBirth.year,
                                onDaySelect = {
                                    viewModel.updateProfile(
                                        dateOfBirth
                                            .copy(day = it.toInt())
                                    )
                                },
                                onMonthSelect = {
                                    viewModel.updateProfile(
                                        dateOfBirth
                                            .copy(month = monthFromString(it))
                                    )
                                },
                                onYearSelect = {
                                    viewModel.updateProfile(
                                        dateOfBirth
                                            .copy(year = it.toInt())
                                    )
                                },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditDrinking -> {
                            DrinkingAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = drinking.choice,
                                onSelect = { viewModel.updateProfile(Drinking(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditEthnicity -> {
                            EthnicityAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = ethnicity.choice,
                                onSelect = { viewModel.updateProfile(Ethnicity(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditGender -> {
                            GenderAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = gender.choice,
                                onSelect = { viewModel.updateProfile(Gender(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditHeight -> {
                            HeightAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                heightInFeet = height.heightInFeet,
                                onSelect = { viewModel.updateProfile(Height(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditInterests -> {
                            InterestsAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                addTextFieldValue = addInterestTextFieldState,
                                onAddTextFieldValueChange = viewModel::onAddInterestTextChange,
                                selectedOptions = interests.interests,
                                onAddButtonCLick = { viewModel.updateProfile(
                                    interests.copy(
                                        interests = interests.interests + addInterestTextFieldState
                                    )
                                ) },
                                onSelect = {
                                    viewModel.updateProfile(
                                        interests.copy(
                                            interests = interests.interests + it
                                        )
                                    )
                                },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditJob -> {
                            JobAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                jobTitle = jobTextFieldState,
                                onValueChange = { viewModel.updateProfile(Job(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditLanguages -> {
                            LanguagesAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                searchQuery = langSearchTextFieldState,
                                onSearchQueryChange = { viewModel.onLangSearchQueryChange(it) },
                                selectedOptions = languages.languages, //// todo
                                onSelect = { language ->
                                    viewModel.updateProfile(
                                        Languages(
                                            languages = languages.languages
                                                .let { languages ->
                                                    if (languages.contains(language))
                                                        languages.filter { it != language }
                                                    else languages + language
                                                }
                                        )
                                    )
                                },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditLocation -> {
                            activity.LocationAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                address = location.address,
                                onAddressChange = {}, // todo
                                onGetLocation = {
                                    viewModel.updateProfile(
                                        Location(it.latitude, it.longitude,"")
                                    )
                                },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditName -> {
                            NameAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                name = name.name,
                                onValueChange = { viewModel.updateProfile(Name(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditPets -> {
                            PetAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = pets.choice,
                                onSelect = { viewModel.updateProfile(Pets(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditQUiz -> {
                            TODO("Add Quiz")
                        }
                        is ProfileEditScreenState.EditReligion -> {
                            ReligionAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = religion.choice,
                                onSelect = { viewModel.updateProfile(Religion(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditSexuality -> {
                            SexualityAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = sexuality.choice,
                                onSelect = { viewModel.updateProfile(Sexuality(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditSmoking -> {
                            SmokingAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = smoking.choice,
                                onSelect = { viewModel.updateProfile(Smoking(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditWorkout -> {
                            WorkoutAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = workout.choice,
                                onSelect = { viewModel.updateProfile(Workout(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                        is ProfileEditScreenState.EditZodiacSign -> {
                            ZodiacSignAddScreen(
                                isNextButtonEnabled = isDoneButtonEnabled,
                                selectedOption = zodiacSign.sign,
                                onSelect = { viewModel.updateProfile(ZodiacSign(it)) },
                                onBackClick = backToProfileEditScreen,
                                onDoneClick = backToProfileEditScreen
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    profileState: ProfileState,
    scrollState: ScrollState,
    onEditPhotoClick: (photoCount: Int, uri: Uri) -> Unit,
    onEditClick: (ProfileEditScreenState) -> Unit,
    onBackClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .then(modifier)
    ) {
        when (profileState) {
            is ProfileState.Error -> {

            }
            ProfileState.Loading -> {

            }
            is ProfileState.Success -> {
                profileState.profile.run {
                    PhotoInput(
                        photo1 = photo1?.photoUrl?.toUri(), photo2 = photo2?.photoUrl?.toUri(),
                        photo3 = photo3?.photoUrl?.toUri(), photo4 = photo4?.photoUrl?.toUri(),
                        photo5 = photo5?.photoUrl?.toUri(), photo6 = photo6?.photoUrl?.toUri(),
                        isOnboarding = false,
                        onGetImageUri = onEditPhotoClick
                    )

                    ProfileEditScreenAddQuizCard(onClick = { onEditClick(ProfileEditScreenState.EditQUiz) })

                    ProfileEditScreenHeading(text = "AboutMe")

                    ProfileEditScreenCard(
                        text = aboutMe.aboutYou,
                        onClick = { onEditClick(ProfileEditScreenState.EditAboutMe) }
                    )

                    ProfileEditScreenHeading(text = "College Name")
                    ProfileEditScreenCard(
                        text = college.collegeName,
                        onClick = { onEditClick(ProfileEditScreenState.EditCollege) }
                    )

                    ProfileEditScreenHeading(text = "Job")
                    ProfileEditScreenCard(
                        text = job.jobTitle,
                        onClick = { onEditClick(ProfileEditScreenState.EditJob) }
                    )

                    ProfileEditScreenHeading(text = "Basics")
                    ProfileEditScreenListItems(
                        icon = R.drawable.user_profile_icon,
                        title = "Name", body = name.name,
                        onCLick = { onEditClick(ProfileEditScreenState.EditName) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.gender,
                        title = "Gender", body = gender.choice,
                        visibility = gender.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditGender) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.birthday,
                        title = "Age", body = "20",   ///// todo
                        visibility = dateOfBirth.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditDOB) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.gender,
                        title = "Sexuality", body = sexuality.choice,
                        visibility = sexuality.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditSexuality) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.height_scale,
                        title = "Height", body = height.heightInFeet,
                        visibility = height.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditHeight) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.ethnicity_icon,
                        title = "Ethnicity", body = ethnicity.choice,
                        visibility = ethnicity.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditEthnicity) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.religion_icon,
                        title = "Religion", body = religion.choice,
                        visibility = religion.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditReligion) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.zodaic_sign_icon,
                        title = "Zodiac Sign", body = zodiacSign.sign,
                        visibility = zodiacSign.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditZodiacSign) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.location_icon,
                        title = "Location", body = location.address,
                        onCLick = { onEditClick(ProfileEditScreenState.EditLocation) }
                    )

                    ProfileEditScreenHeading(text = "LifeStyle")
                    ProfileEditScreenListItems(
                        icon = R.drawable.smoking_icon,
                        title = "Smoking", body = smoking.choice,
                        visibility = smoking.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditSmoking) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.drinking_icon,
                        title = "Drinking", body = drinking.choice,
                        visibility = drinking.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditDrinking) }
                    )

                    ProfileEditScreenListItems(
                        icon = R.drawable.workout_icon,
                        title = "Workout", body = workout.choice,
                        visibility = workout.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditWorkout) }
                    )


                    ProfileEditScreenListItems(
                        icon = R.drawable.pet_icon,
                        title = "Pet", body = pets.choice,
                        visibility = pets.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditPets) }
                    )


                    ProfileEditScreenListItems(
                        icon = R.drawable.family_icon,
                        title = "Children", body = children.choice,
                        visibility = children.isVisibleInProfile,
                        onCLick = { onEditClick(ProfileEditScreenState.EditChildren) }
                    )

                    ProfileEditScreenHeading(text = "Interests")

                    Row(
                        modifier = Modifier
                            .clickable { onEditClick(ProfileEditScreenState.EditInterests) }
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        interests.interests.forEach { interest ->
                            Chips(text = interest)
                        }
                    }

                    ProfileEditScreenHeading(text = "Languages")

                    Row(
                        modifier = Modifier
                            .clickable { onEditClick(ProfileEditScreenState.EditLanguages) }
                            .horizontalScroll(rememberScrollState())
                            .background(color = Color.Black) ////  todo remomve it
                            // .height(50.dp)  ////  todo remomve it
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        languages.languages.forEach { language ->
                            Chips(text = language)
                        }
                    }
                }
            }
        }
    }
    BackHandler { onBackClick() }
}


@Composable
fun ProfileEditScreenAddQuizCard(
    onClick: () -> Unit
) {

    val backgroundGradient = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.scrim
    )
    val borderGradient = listOf(
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primary,
    )

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = backgroundGradient
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .border(
                brush = Brush.linearGradient(borderGradient),
                width = 2.dp,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        StandardButton(onClick = onClick) {
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp),
                color = Color.White,
                text = "Add Quiz",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(Font(R.font.aclonica))
                )
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(4) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }) { }
                            .padding(13.dp)
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.tertiary,
                        painter = painterResource(id = R.drawable.ques_icon),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.tertiary,
                        text = (it + 1).toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}


@Composable
fun ProfileEditScreenCard(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp)
            .requiredHeightIn(min = 60.dp, max = 130.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(13.dp),
            overflow = TextOverflow.Ellipsis,
            text = text

        )
    }
}


@Composable
fun ProfileEditScreenHeading(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun ProfileEditScreenListItems(
    @DrawableRes
    icon: Int,
    title: String,
    body: String,
    visibility: Boolean = true,
    onCLick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { onCLick() }
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(9f)
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp),
                tint = MaterialTheme.colorScheme.primary,
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 10.sp
                    ),
                )
                Text(
                    text = body,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.sp
                    ),
                )
            }
        }
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(
                id = if (visibility)
                    R.drawable.visible_icon
                else com.example.ui.R.drawable.hidden
            ),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfileEditScreenAnimation(
    ///// TODO Add in core
    state: ProfileEditScreenState,
    content: @Composable (targetState: ProfileEditScreenState) -> Unit,
) {
    AnimatedContent(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        targetState = state,
        transitionSpec = {
            if (targetState != ProfileEditScreenState.NotEditing) {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300, easing = LinearOutSlowInEasing
                    )
                ) { width -> (width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> -width } + fadeOut()
            } else {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ) { width -> -(width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> (width * 2) } + fadeOut()
            }
        }
    ) { targetState ->
        content(targetState)
    }
}
//// todo i think its better  to create seperate viewmodel for each screen . But again if we do that than ints beteer to reuse the viewmodel of onboarding screen
//// todo get all the choices by using the  api