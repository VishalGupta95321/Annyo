package com.example.onboarding_presentation.secondary_screens.languages

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.Languages
import com.example.screens.LanguagesAddScreen

@Composable
fun LanguagesScreen(
    viewModel: LanguagesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val languagesState by viewModel
        .languagesState
        .collectAsState()

    val languagesUpdateState by viewModel
        .languagesUpdateState
        .collectAsState()

    val query by viewModel.query.collectAsState()

    when (languagesUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    when (val response = languagesState) {
        LanguagesUiState.Error -> {}
        LanguagesUiState.Loading -> {}
        is LanguagesUiState.Success -> {
            LanguagesAddScreen(
                searchQuery = query,
                onSearchQueryChange = viewModel::onQueryChange,
                selectedOptions = response.languages.languages,
                onSelect = { language ->
                    viewModel.updateLanguages(
                        Languages(
                            languages = response.languages.languages
                                .let { languages ->
                                    if (languages.contains(language))
                                        languages.filter { it != language }
                                    else languages + language
                                }
                        )
                    )
                },
                onBackClick = onBackClick
            ) {onNextCLick()}
        }
    }
    BackHandler { onBackClick() }
}

//
//BaseInputScreen(
//label1 = "Add You Languages.",
//inputContent = {
//    Column {
//        StandardTextField(
//            modifier = Modifier
//                .padding(
//                    horizontal = 10.dp, vertical = 10.dp
//                )
//                .fillMaxWidth(0.8f),
//            value = query ?: "",
//            onValueChange = viewModel::onQueryChange,
//            placeholderText = "Search language"
//        )
//        when (val response = languagesState) {
//            LanguagesUiState.Error -> {}
//            LanguagesUiState.Loading -> {}
//            is LanguagesUiState.Success -> {
//
//                ChipsGroup(
//                    choices = LanguagesChoices(query),
//                    selectedOptionOrOptions = response.languages.languages,
//                    onSelect = { language ->
//                        viewModel.updateLanguages(
//                            Languages(
//                                languages = response.languages.languages
//                                    .let { languages ->
//                                        if (languages.contains(language))
//                                            languages.filter { it != language }
//                                        else languages + language
//                                    }
//                            )
//                        )
//                    })
//            }
//        }
//    }
//},
//onBackClick = onBackClick
//) { onNextCLick() }