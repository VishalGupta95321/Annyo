package com.example.profile.di

import com.example.profile.repository.ProfileRepository
import com.example.profile.use_cases.*
import com.example.profile.use_cases.update_profile.*
import com.example.profile.util.GeocoderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileDomainModule {

    @Provides
    @Singleton
    fun provideUpdateProfileUseCases(
        repository: ProfileRepository,
        reverseGeoCoder: GeocoderService
    ):UpdateProfileUseCases{
        return UpdateProfileUseCases(
            updateName = UpdateNameUseCase(repository),
            updateGender = UpdateGenderUseCase(repository),
            updateSexuality = UpdateSexualityUseCase(repository),
            updateAboutMe = UpdateAboutMeUseCase(repository) ,
            updateCollege = UpdateCollegeUseCase(repository),
            updatePets = UpdatePetsUseCase(repository),
            updateDrinking = UpdateDrinkingUseCase(repository),
            updateSmoking = UpdateSmokingUseCase(repository),
            updateZodiac = UpdateZodiacSign(repository),
            updateChildren = UpdateChildrenUseCase(repository),
            updateEthnicity = UpdateEthnicityUseCase(repository),
            updateInterests = UpdateInterestsUseCase(repository),
            updateWorkout = UpdateWorkoutUseCase(repository),
            updateReligion = UpdateReligionUseCase(repository) ,
            updateJob = UpdateJobUseCase(repository),
            updatePhoto = UpdatePhotoUseCase(repository),
            updateLanguages = UpdateLanguageUseCase(repository),
            updateDateOfBirth = UpdateDateOfBirthUseCase(
                repository,ValidateDateUseCase()
            ),
            updateLocation = UpdateLocationUseCase(repository,
                CoordinatesToAddressUseCase(reverseGeoCoder)),
            updateHeight = UpdateHeightUseCase(repository)
        )
    }


    @Provides
    @Singleton
    fun provideProfileUseCases(
        updateProfileUseCases:UpdateProfileUseCases,
        repository: ProfileRepository,
        geocoder: GeocoderService
    ):ProfileUseCases {
        return ProfileUseCases(
            createProfile = CreateProfileUseCase(repository),
            updateProfile = updateProfileUseCases,
            getProfile = GetProfileUseCase(repository),
            uploadPhotoToCloud = UploadPhotoToCloudUseCase(repository),
            syncProfileWithServerUseCase = SyncProfileWithServerUseCase(repository),
            birthDateToAgeUseCase = BirthDateToAgeUseCase(),
            validateDateUseCase = ValidateDateUseCase(),
            testGet = TestGet(repository),
            coordinatesToAddressUseCase = CoordinatesToAddressUseCase(geocoder),
            getProfileCompletedPercUseCase = GetProfileCompletedPercUseCase()
        )
    }
}