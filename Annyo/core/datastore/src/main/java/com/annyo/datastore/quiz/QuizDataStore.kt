package com.annyo.datastore.quiz

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

sealed interface QuizDataStore {

    companion object {
        const val name : String = "quiz_datastore"
    }

    object QuestionsKeys {
        val QUES1  = stringPreferencesKey("Ques_1")
        val QUES2  = stringPreferencesKey("Ques_2")
        val QUES3  = stringPreferencesKey("Ques_3")
        val QUES4  = stringPreferencesKey("Ques_4")
    }

    object OptionsKey {
        val Q1_OPTIONS  =  stringSetPreferencesKey("Q1_Options")
        val Q2_OPTIONS  =  stringSetPreferencesKey("Q2_Options")
        val Q3_OPTIONS  =  stringSetPreferencesKey("Q3_Options")
        val Q4_OPTIONS  =  stringSetPreferencesKey("Q4_Options")
    }

    object AnswerKey {
        val Q1_ANSWER = intPreferencesKey("Q1_Answer")
        val Q2_ANSWER = intPreferencesKey("Q2_Answer")
        val Q3_ANSWER = intPreferencesKey("Q3_Answer")
        val Q4_ANSWER = intPreferencesKey("Q4_Answer")
    }

    object MatchingCriteriaKey {
        val ANSWERS_HAS_TO_BE_CORRECT = intPreferencesKey("Answers_Has_To_Be_CORRECT")
    }

}