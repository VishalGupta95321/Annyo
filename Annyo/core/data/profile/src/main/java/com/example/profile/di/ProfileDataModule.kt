package com.example.profile.di


import android.content.Context
import android.location.Geocoder
import com.example.profile.remote.api.UserApi
import com.example.profile.repository.ProfileRepository
import com.example.profile.repository.ProfileRepositoryImpl
import com.example.profile.util.GeocoderService
import com.example.profile.util.ReverseGeocoder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProfileDataModule {

    @Binds
    @Singleton
    fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl):ProfileRepository

    @Binds
    @Singleton
    fun bindReverseGeoCoder(geoCoder: ReverseGeocoder):GeocoderService

    companion object{
        @Provides
        @Singleton
        fun provideGeocoder(
            @ApplicationContext context: Context
        ): Geocoder {
            return Geocoder(context, Locale.getDefault())
        }


        @OptIn(ExperimentalSerializationApi::class)
        @Provides
        @Singleton
        fun provideUserApi(
            client:OkHttpClient,
        ):UserApi{
            return Retrofit.Builder()
                .baseUrl(UserApi.BASE_URL)
                .addConverterFactory((Json.asConverterFactory("application/json".toMediaType())))
                .client(client)
                .build()
                .create(UserApi::class.java)
        }
    }
}
