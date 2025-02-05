package com.app.auth_presentation.otp_login.country_code_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryCodeViewModel @Inject constructor():ViewModel() {

    var searchQuery  by mutableStateOf("")
        private set

    fun onQueryChange(query : String){
        searchQuery = query
    }
}