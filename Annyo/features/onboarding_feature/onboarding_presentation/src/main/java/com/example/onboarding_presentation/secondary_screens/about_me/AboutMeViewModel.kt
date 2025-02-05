package com.example.onboarding_presentation.secondary_screens.about_me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.onboarding_presentation.ProfileUpdateState.*
import com.example.profile.model.AboutMe
import com.example.profile.use_cases.ProfileUseCases
import com.example.utils.GetBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@HiltViewModel
class AboutMeViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private var _aboutMeState = MutableStateFlow(AboutMeUiState())
    val aboutMeState: StateFlow<AboutMeUiState> = _aboutMeState.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = AboutMeUiState()
    )

    private val _aboutMeUpdateState = MutableStateFlow<ProfileUpdateState>(NotUpdating)
    val aboutMeUpdateState: StateFlow<ProfileUpdateState> = _aboutMeUpdateState
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue =NotUpdating
        )

    private var job: Job? = null

    fun updateAboutMe(aboutMe: AboutMe) {

        _aboutMeState.value = _aboutMeState.value
            .copy(aboutMe = aboutMe)

        job?.cancel()

        job = viewModelScope.launch {

            profileUseCases
                .updateProfile
                .updateAboutMe(aboutMe, false)
                .also { _aboutMeUpdateState
                    .value = Updating
                    when (it) {
                        is GetBack.Error -> {
                            _aboutMeUpdateState
                                .value = UpdateFailed()
                        }
                        is GetBack.Success -> {
                            _aboutMeUpdateState
                                .value = Updated
                        }
                    }
                }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileUseCases
                .getProfile()
                .onStart {
                    _aboutMeState
                        .value = _aboutMeState.value
                        .copy(isLoading = true)
                }.first()
                .also {
                    _aboutMeState
                        .value = _aboutMeState.value
                        .copy(
                            aboutMe = it.aboutMe,
                            isLoading = false
                        )
                }
        }
    }
    init { getProfile() }
}


//    private val _aboutMeUpdateState = MutableStateFlow<UpdateState>(UpdateState.None)
//    val aboutMeUpdateState: StateFlow<UpdateState> = _aboutMeUpdateState
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileSubscribed(5_000),
//            initialValue = UpdateState.None
//        )

//    fun updateAboutMe() {
//        viewModelScope.launch {
//            aboutMeState.value.aboutMe?.let {
//                _aboutMeUpdateState.value = UpdateState.Updating
//                profileUseCases.updateProfile
//                    .updateAboutMe( it, false).also { update->
//                        when (update) {
//                            is GetBack.Error -> {
//                                _aboutMeUpdateState.value = UpdateState.UpdateFailed
//                            }
//                            is GetBack.Success -> {
//                                _aboutMeUpdateState.value = UpdateState.Updated
//                            }
//                        }
//                    }
//            }
//        }
//    }

//    private fun getProfile(): Flow<AboutMeState> = profileUseCases
//        .getProfile()
//        .onStart { AboutMeState(isLoading = true) }
//        .asResult()
//        .map {
//
//            when (it) {
//                is GetBack.Error -> {
//                    AboutMeState.Error(it.message)
//                }
//                is GetBack.Success -> {
//                    it.data?.let { profile ->
//                        if (
//                            profile.aboutMe.aboutYou.isEmpty()
//                        ) AboutMeState.NotSpecified
//                        else AboutMeState.Success(
//                            profile.aboutMe
//                        )
//                    } ?: AboutMeState.NotSpecified
//                }
//            }
//        }

//    val aboutMeState: StateFlow<AboutMeState> = getProfile()
//        .stateIn(
//        scope = viewModelScope,
//        started = WhileSubscribed(5_000),
//        initialValue = AboutMeState.Loading
//    )

//    private fun getProfile(): Flow<AboutMeState> = profileUseCases
//        .getProfile()
//        .onStart { AboutMeState.Loading }
//        .asResult()
//        .map {
//
//            when (it) {
//                is GetBack.Error -> {
//                    AboutMeState.Error(it.message)
//                }
//                is GetBack.Success -> {
//                    it.data?.let { profile ->
//                        if (
//                            profile.aboutMe.aboutYou.isEmpty()
//                        ) AboutMeState.NotSpecified
//                        else AboutMeState.Success(
//                            profile.aboutMe
//                        )
//                    } ?: AboutMeState.NotSpecified
//                }
//            }
//        }


//    private suspend fun syncProfile(): AboutMeState {
//        profileUseCases.syncProfileWithServerUseCase().also {
//            return when (it) {
//                is GetBack.Error -> {
//                    AboutMeState.Error(it.message)
//                }
//                is GetBack.Success -> {
//                    AboutMeState.Success
//                }
//            }
//        }
//    }
//}

//
//private fun getProfile(): Flow<AboutMeState> = profileUseCases
//    .getProfile()
//    .onStart { AboutMeState.Loading }
//    .asResult()
//    .map {
//        when (it) {
//            is GetBack.Error -> {
//                AboutMeState.Error(it.message)
//            }
//            is GetBack.Success -> {
//                it.data?.let { profile ->
//                    if (
//                        profile.aboutMe.aboutYou.isEmpty()
//                    ) AboutMeState.NotSpecified
//                    else AboutMeState.Success(
//                        profile.aboutMe
//                    )
//                } ?: AboutMeState.NotSpecified
//            }
//        }
//    }

//
//    private suspend fun getProfile() : Flow<AboutMeState>{
//
//        val dwds = profileUseCases.testGet().map {
//                when(it){
//                    is GetBack.Error -> {
//                        println("entered errr")
//                        AboutMeState.Error(it.message)
//                    }
//                    is GetBack.Success -> {
//                        println("entered succcess")
//                        it.data?.collect{ profile->
//                            println("entered data"+profile.aboutMe)
//                            AboutMeState.Success(profile.aboutMe)
//                        }
//                        println(" afteer  collect")
//                        AboutMeState.NotSpecified
//                    }
//                }
//            }
//
//        return dwds
//    }
//         profileUseCases.testGet().also {
//             when(it){
//                is GetBack.Error -> {
//                    return AboutMeState.Error(it.message)
//                }
//                is GetBack.Success -> {
//                    AboutMeState.NotSpecified
//                    it.data?.collect{ profile ->
//                        if (profile.aboutMe.aboutYou.isNotEmpty())
//                            AboutMeState.NotSpecified
//                        else AboutMeState.Success(profile.aboutMe)
//                    }
//                    //AboutMeState.NotSpecified
//                }
//            }
//        }
// }
//        println("entered fow")
//
//}


//    val aboutMeState: StateFlow<AboutMeUiState> = getProfile()
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileSubscribed(5_000),
//            initialValue = AboutMeUiState()
//        )

//    var _about by mutableStateOf(AboutMe())
//        private set

//private fun geetProfile() {
//    val j = profileUseCases
//        .getProfile()
//        .onStart {
////                    AboutMeUiState(isLoading = true)
////                    println("starete")
//        }
//        .asResult()
//        .map {
//            when (it) {
//                is GetBack.Error -> {
//                    AboutMe()
//                }
//                is GetBack.Success -> {
//                    it.data?.aboutMe ?: AboutMe()
////                        _about = _about.copy(aboutYou = it.data?.aboutMe?.aboutYou?:"")
////                        AboutMeUiState(
////                            aboutMe = it.data?.aboutMe,
////                            isLoading = false
////                        )
//                }
//            }
//        }
//    viewModelScope.launch(Dispatchers.Main.immediate) {
//
//        j.first().also {
//            this@AboutMeViewModel._aboutMeState.value = it
//        }
//    }
//}
/// todo about process death and saveStateHandle used in  now in  android topic view model and mentioned in phillip video




