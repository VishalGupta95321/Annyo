package com.annyo.datastore.setttings

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed interface SettingsDataStore {

    companion object {
        const val name : String = "settings_datastore"
    }

    object DatingPreferencesKeys{
        val Looking_FOR = stringPreferencesKey("Looking_For")
        val MAXIMUM_DISTANCE_IN_MILES = intPreferencesKey("Maximum_Distance_In_Miles")
        val MINIMUM_AGE = intPreferencesKey("Maximum_Age")
        val MAXIMUM_AGE = intPreferencesKey("Minimum_Age")
        val MINIMUM_HEIGHT = intPreferencesKey("Maximum_Height")
        val MAXIMUM_HEIGHT = intPreferencesKey("Minimum_Height")
    }

    object AccountManageKeys{
        // todo  ex. logout , change email , ph no. , delete account , pause acc etc..
    }
}