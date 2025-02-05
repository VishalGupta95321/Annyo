package com.annyo.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.annyo.datastore.di.DataStoreQualifiers.MembershipPreferences
import com.annyo.datastore.di.DataStoreQualifiers.ProfilePreferences
import com.annyo.datastore.di.DataStoreQualifiers.QuizPreferences
import com.annyo.datastore.di.DataStoreQualifiers.SettingsPreferences
import com.annyo.datastore.membership.MembershipDataStore
import com.annyo.datastore.profile.ProfileDataStore
import com.annyo.datastore.quiz.QuizDataStore
import com.annyo.datastore.setttings.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {


    @ProfilePreferences
    @Provides
    fun provideProfileDataStore(
        @ApplicationContext appContext: Context
    ) = PreferenceDataStoreFactory.create(
        produceFile = {
            appContext.preferencesDataStoreFile(
                ProfileDataStore.name
            )
        }
    )

    @Provides
    @MembershipPreferences
    fun provideMembershipDataStore(
        @ApplicationContext appContext: Context
    ) = PreferenceDataStoreFactory.create(
        produceFile = {
            appContext.preferencesDataStoreFile(
                MembershipDataStore.name
            )
        }
    )

    @Provides
    @SettingsPreferences
    fun provideSettingsDataStore(
        @ApplicationContext appContext: Context
    ) = PreferenceDataStoreFactory.create(
        produceFile = {
            appContext.preferencesDataStoreFile(
                SettingsDataStore.name
            )
        }
    )

  //  @Singleton   CoroutineDispatchers don’t need to be scoped to the SingletonComponent.
    @Provides
    @QuizPreferences
    fun provideQuizDataStore(
        @ApplicationContext appContext: Context
    ) = PreferenceDataStoreFactory.create(
        produceFile = {
            appContext.preferencesDataStoreFile(
                QuizDataStore.name
            )
        }
    )

}

interface DataStoreQualifiers {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ProfilePreferences

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MembershipPreferences

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SettingsPreferences

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class QuizPreferences

}