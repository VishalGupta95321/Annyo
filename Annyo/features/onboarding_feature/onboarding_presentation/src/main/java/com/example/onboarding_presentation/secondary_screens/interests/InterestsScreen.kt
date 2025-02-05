package com.example.onboarding_presentation.secondary_screens.interests

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Interests
import com.example.screens.InterestsAddScreen

@Composable
fun InterestsScreen(
    viewModel: InterestsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val interestsState by viewModel.interestsState.collectAsState()
    val interestsUpdateState by viewModel.interestsUpdateState.collectAsState()
    val addTextFieldState by viewModel.addTextFieldState.collectAsState()

    when (interestsUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    InterestsAddScreen(
        addTextFieldValue = addTextFieldState,
        onAddTextFieldValueChange = viewModel::onAddTextChange,
        selectedOptions = interestsState.interests.interests,
        onAddButtonCLick = { viewModel.updateNewInterests(addTextFieldState) },
        onSelect = { interest ->
            viewModel.updateInterests(
                Interests(          ///// todo see if its better to add this logic in thee view model
                    interests = interestsState.interests.interests
                        .let { interests ->
                            if (interests.contains(interest))
                                interests.filter { it != interest }
                            else interests + interest
                        }
                )
            )
        },
        onBackClick = onBackClick
    ) { onNextCLick() }
    BackHandler { onBackClick() }
}

//BaseInputScreen(
//label1 = "Add Your Interests.",
//inputContent = {
//    Column {
//        Row(
//            modifier = Modifier
//                .padding(horizontal = 10.dp, vertical = 20.dp)
//        ) {
//            StandardTextField(
//                modifier = Modifier.weight(7.5f),
//                value = addTextFieldState,
//                onValueChange = viewModel::onAddTextChange,
//                placeholderText = " Add interests"
//            )
//            StandardButton(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .weight(2.5f),
//                onClick = {
//                    viewModel.updateNewInterests(addTextFieldState)
//                }) {
//                Text(text = "Add")
//            }
//        }
//
//        ChipsGroup(
//            choices = InterestsChoices(
//                interestsState.interests.interests
//            ),
//            selectedOptionOrOptions = interestsState.interests.interests,
//            onSelect = { interest ->
//                viewModel.updateInterests(
//                    Interests(          ///// todo see if its better to add this logic in thee view model
//                        interests = interestsState.interests.interests
//                            .let { interests ->
//                                if (interests.contains(interest))
//                                    interests.filter { it != interest }
//                                else interests + interest
//                            }
//                    )
//                )
//            }
//        )
//    }
//},
//onBackClick = onBackClick
//) { onNextCLick() }