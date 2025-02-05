package com.annyo.datastore.profile

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

sealed interface ProfileDataStore {

    companion object {
        const val name = "profile_datastore"
    }

    object ProfileKeys {
        val NAME = stringPreferencesKey("Name")
        val DOB_DAY = intPreferencesKey("Dob_Day")
        val DOB_MONTH = intPreferencesKey("Dob_Month")
        val DOB_YEAR = intPreferencesKey("Dob_Year")
        val WORKOUT = stringPreferencesKey("Workout")
        val PETS = stringPreferencesKey("pets")
        val CHILDREN = stringPreferencesKey("Children")
        val SMOKING = stringPreferencesKey("Smoking")
        val Drinking  = stringPreferencesKey("Drinking")
        val ETHNICITY = stringPreferencesKey("Ethnicity")
        val GENDER = stringPreferencesKey("Gender")
        val LANGUAGES = stringSetPreferencesKey("Languages")
        val INTERESTS = stringSetPreferencesKey("Interests")
        val SEXUALITY = stringPreferencesKey("Sexuality")
        val COLLEGE = stringPreferencesKey("College")
        val JOB = stringPreferencesKey("Job")
        val HEIGHT = stringPreferencesKey("Height")
        val LOCATION_LATITUDE = stringPreferencesKey("Location_Longitude")
        val LOCATION_LONGITUDE = stringPreferencesKey("Location_Latitude")
        val LOCATION_ADDRESS = stringPreferencesKey("Location_Address")
        val ZODIAC_SIGN = stringPreferencesKey("Zodiac_Sign")
        val PHOTO_1 = stringPreferencesKey("Photo_1")
        val PHOTO_2 = stringPreferencesKey("Photo_2")
        val RELIGION  = stringPreferencesKey("Religion")
        val PHOTO_3 = stringPreferencesKey("Photo_3")
        val PHOTO_4 = stringPreferencesKey("Photo_4")
        val PHOTO_5 = stringPreferencesKey("Photo_5")
        val PHOTO_6 = stringPreferencesKey("Photo_6")
        val ABOUT_ME = stringPreferencesKey("About_Me")
    }

    object VisibilityControlKeys {
        val AGE_VISIBILITY = booleanPreferencesKey("Age_Visibility")
        val CHILDREN_VISIBILITY = booleanPreferencesKey("Children_Visibility")
        val RELIGION_VISIBILITY = booleanPreferencesKey("Religion_Visibility")
        val COLLEGE_VISIBILITY = booleanPreferencesKey("College_Visibility")
        val GENDER_VISIBILITY =  booleanPreferencesKey("Gender_Visibility")
        val SEXUALITY_VISIBILITY =  booleanPreferencesKey("Sexuality_Visibility")
        val JOB_VISIBILITY = booleanPreferencesKey("Job_Visibility")
        val PETS_VISIBILITY = booleanPreferencesKey("Pets_Visibility")
        val LANGUAGES_VISIBILITY = booleanPreferencesKey("Languages_Visibility")
        val SMOKING_VISIBILITY = booleanPreferencesKey("Smoking_Visibility")
        val DRINKING_VISIBILITY = booleanPreferencesKey("Drinking_Visibility")
        val WORKOUT_VISIBILITY = booleanPreferencesKey("Workout_Visibility")
        val ETHNICITY_VISIBILITY = booleanPreferencesKey("Ethnicity_Visibility")
        val HEIGHT_VISIBILITY = booleanPreferencesKey("Height_Visibility")
        val ZODIAC_SIGN_VISIBILITY = booleanPreferencesKey("Zodiac_Sign_Visibility")
        val DISTANCE_VISIBILITY = booleanPreferencesKey("Distance_Visibility")
    }

    object EncryptionControlKeys {
        //// TODO
    }
}