package com.example.onboarding_presentation.secondary_screens.children

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.ChildrenAddScreen

@Composable
fun ChildrenScreen(
    viewModel: ChildrenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val childrenState by viewModel
        .childrenState
        .collectAsState()

    val childrenUpdateState by viewModel
        .childrenUpdateState
        .collectAsState()

    when (childrenUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    ChildrenAddScreen(
        isNextButtonEnabled = childrenState.children.choice.isNotBlank(),   /// todo see if instead of empty we do have some default value
        selectedOption = childrenState.children.choice,
        onSelect = {
            viewModel.updateChildren(
                childrenState.children
                    .copy(choice = it)
            )},
        onBackClick = onBackClick,
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

//BaseInputScreen(
//isNextButtonEnabled = childrenState.children.choice.isNotEmpty(),
//label1 = "What about family plans ?",
//inputContent = {
//    ChipsGroup(
//        choices = ChildrenChoices() ,
//        selectedOptionOrOptions = listOf(childrenState.children.choice),
//        onSelect = {
//            viewModel.updateChildren(
//                childrenState.children
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }
