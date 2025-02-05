package com.example.profile.repository

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.annyo.datastore.di.DataStoreQualifiers.ProfilePreferences
import com.annyo.datastore.profile.ProfileDataStore
import com.annyo.firebase_service.domain.use_case.SignedUserUseCases
import com.example.profile.model.AboutMe
import com.example.profile.model.Children
import com.example.profile.model.College
import com.example.profile.model.DateOfBirth
import com.example.profile.model.Drinking
import com.example.profile.model.Ethnicity
import com.example.profile.model.Gender
import com.example.profile.model.Height
import com.example.profile.model.Interests
import com.example.profile.model.Job
import com.example.profile.model.Languages
import com.example.profile.model.Location
import com.example.profile.model.Name
import com.example.profile.model.Pets
import com.example.profile.model.Photo
import com.example.profile.model.Profile
import com.example.profile.model.Religion
import com.example.profile.model.Sexuality
import com.example.profile.model.Smoking
import com.example.profile.model.Workout
import com.example.profile.model.ZodiacSign
import com.example.profile.remote.api.UserApi
import com.example.profile.remote.request.CreateProfileRequest.Companion.fromProfile
import com.example.profile.remote.request.UpdateAboutMeRequest.Companion.fromAboutMe
import com.example.profile.remote.request.UpdateChildrenRequest.Companion.fromChildren
import com.example.profile.remote.request.UpdateCollegeRequest.Companion.fromCollege
import com.example.profile.remote.request.UpdateDobRequest.Companion.fromDob
import com.example.profile.remote.request.UpdateDrinkingRequest.Companion.fromDrinking
import com.example.profile.remote.request.UpdateEthnicityRequest.Companion.fromEthnicity
import com.example.profile.remote.request.UpdateGenderRequest.Companion.fromGender
import com.example.profile.remote.request.UpdateHeightRequest.Companion.fromHeight
import com.example.profile.remote.request.UpdateInterestsRequest.Companion.fromHobbies
import com.example.profile.remote.request.UpdateJobRequest.Companion.fromJob
import com.example.profile.remote.request.UpdateLanguagesRequest.Companion.fromLanguages
import com.example.profile.remote.request.UpdateLocationRequest.Companion.fromLocation
import com.example.profile.remote.request.UpdateNameRequest.Companion.fromName
import com.example.profile.remote.request.UpdatePetsRequest.Companion.fromPets
import com.example.profile.remote.request.UpdatePhotoRequest.Companion.fromPhoto
import com.example.profile.remote.request.UpdateReligionRequest.Companion.fromReligion
import com.example.profile.remote.request.UpdateSexualityRequest.Companion.fromSexuality
import com.example.profile.remote.request.UpdateSmokingRequest.Companion.fromSmoking
import com.example.profile.remote.request.UpdateWorkoutRequest.Companion.fromWorkout
import com.example.profile.remote.request.UpdateZodiacSignRequest.Companion.fromZodiacSign
import com.example.profile.remote.response.SimpleUserApiResponse
import com.example.profile.util.ProfileModel
import com.example.utils.ErrorMessages.SERVER_NOT_REACHED
import com.example.utils.ErrorMessages.SOMETHING_WENT_WRONG
import com.example.utils.ErrorMessages.UPDATE_FAILED
import com.example.utils.ErrorMessages.UPLOAD_FAILED
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    @ProfilePreferences
    private val profileDatastore: DataStore<Preferences>,
    private val userApi: UserApi,
    private val signedUserUseCases: SignedUserUseCases, /// todo maybe we can not use cases in repository
    private val firebaseStorage: StorageReference     /// todo this is against the "D" of solid principles
) : ProfileRepository {

    override suspend fun createProfile(profile: Profile): GetBackBasic {

        return try {

            val response: SimpleUserApiResponse?

            val uid = signedUserUseCases.getUid()

            if (uid.isNullOrBlank())
                GetBack.Error(SOMETHING_WENT_WRONG)
            else {
                response = userApi.createProfile(
                    fromProfile(profile).copy(
                        uid = uid
                    )
                )

                if (response.isSuccessful)
                    GetBack.Success()
                else response.message?.let {       /// todo actually my plan is to directly throw my own custom http exception So if do that then we don't need to check here
                    GetBack.Error(it)
                } ?: GetBack.Error()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return GetBack.Error(SERVER_NOT_REACHED)
        } catch (e: HttpException) {
            e.printStackTrace()
            return GetBack.Error(SOMETHING_WENT_WRONG)
        }
    }

    override fun getProfile(): Flow<Profile> {

        return profileDatastore.data.map { preferences ->

            Profile(
                aboutMe = preferences[ProfileDataStore.ProfileKeys.ABOUT_ME]?.let {
                    AboutMe(it)
                } ?: AboutMe(""),

                name = preferences[ProfileDataStore.ProfileKeys.NAME]?.let {
                    Name(it)
                } ?: Name(""),

                dateOfBirth = DateOfBirth(
                    day = preferences[ProfileDataStore.ProfileKeys.DOB_DAY] ?: 0,
                    month = preferences[ProfileDataStore.ProfileKeys.DOB_MONTH] ?: 0,
                    year = preferences[ProfileDataStore.ProfileKeys.DOB_YEAR] ?: 0
                ),

                workout = Workout(
                    choice = preferences[ProfileDataStore.ProfileKeys.WORKOUT] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.WORKOUT_VISIBILITY]
                        ?: true
                ),

                pets = Pets(
                    choice = preferences[ProfileDataStore.ProfileKeys.PETS] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.PETS_VISIBILITY] ?: true
                ),

                children = Children(
                    choice = preferences[ProfileDataStore.ProfileKeys.CHILDREN] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.CHILDREN_VISIBILITY]
                        ?: true
                ),

                smoking = Smoking(
                    choice = preferences[ProfileDataStore.ProfileKeys.SMOKING] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.SMOKING_VISIBILITY]
                        ?: true
                ),

                drinking = Drinking(
                    choice = preferences[ProfileDataStore.ProfileKeys.Drinking] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.DRINKING_VISIBILITY]
                        ?: true
                ),

                ethnicity = Ethnicity(
                    choice = preferences[ProfileDataStore.ProfileKeys.ETHNICITY] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.ETHNICITY_VISIBILITY]
                        ?: true
                ),

                gender = Gender(
                    choice = preferences[ProfileDataStore.ProfileKeys.GENDER] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.GENDER_VISIBILITY]
                        ?: true
                ),

                sexuality = Sexuality(
                    choice = preferences[ProfileDataStore.ProfileKeys.SEXUALITY] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.SEXUALITY_VISIBILITY]
                        ?: true
                ),

                languages = Languages(
                    languages = preferences[ProfileDataStore.ProfileKeys.LANGUAGES]?.toList()
                        ?: emptyList(),
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.LANGUAGES_VISIBILITY]
                        ?: true
                ),

                interests = Interests(
                    interests = preferences[ProfileDataStore.ProfileKeys.INTERESTS]?.toList()
                        ?: emptyList(),
                ),

                college = College(
                    collegeName = preferences[ProfileDataStore.ProfileKeys.COLLEGE] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.COLLEGE_VISIBILITY]
                        ?: true
                ),

                job = Job(
                    jobTitle = preferences[ProfileDataStore.ProfileKeys.JOB] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.JOB_VISIBILITY] ?: true
                ),

                height = Height(
                    heightInFeet = preferences[ProfileDataStore.ProfileKeys.HEIGHT] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.HEIGHT_VISIBILITY]
                        ?: true
                ),

                location = Location(
                    latitude = preferences[ProfileDataStore.ProfileKeys.LOCATION_LATITUDE]
                        ?.toDouble() ?: 0.0,
                    longitude = preferences[ProfileDataStore.ProfileKeys.LOCATION_LATITUDE]
                        ?.toDouble() ?: 0.0,
                    address = preferences[ProfileDataStore.ProfileKeys.LOCATION_ADDRESS]
                        ?: ""
                ),

                religion = Religion(
                    choice = preferences[ProfileDataStore.ProfileKeys.RELIGION] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.RELIGION_VISIBILITY]
                        ?: true
                ),

                zodiacSign = ZodiacSign(
                    sign = preferences[ProfileDataStore.ProfileKeys.ZODIAC_SIGN] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.ZODIAC_SIGN_VISIBILITY]
                        ?: true
                ),

                photo1 = preferences[ProfileDataStore.ProfileKeys.PHOTO_1]?.let {
                    Photo(it, photoCount = 1)
                },
                photo2 = preferences[ProfileDataStore.ProfileKeys.PHOTO_2]?.let {
                    Photo(it, photoCount = 2)
                },
                photo3 = preferences[ProfileDataStore.ProfileKeys.PHOTO_3]?.let {
                    Photo(it, photoCount = 3)
                },
                photo4 = preferences[ProfileDataStore.ProfileKeys.PHOTO_4]?.let {
                    Photo(it, photoCount = 4)
                },
                photo5 = preferences[ProfileDataStore.ProfileKeys.PHOTO_5]?.let {
                    Photo(it, photoCount = 5)
                },
                photo6 = preferences[ProfileDataStore.ProfileKeys.PHOTO_6]?.let {
                    Photo(it, photoCount = 6)
                },
            )
        }
    }

    override  fun testProfile() = flow {

      val data  =   profileDatastore.data.map { preferences ->

            Profile(
                aboutMe = preferences[ProfileDataStore.ProfileKeys.ABOUT_ME]?.let {
                    AboutMe(it)
                } ?: AboutMe(""),

                name = preferences[ProfileDataStore.ProfileKeys.NAME]?.let {
                    Name(it)
                } ?: Name(""),

                dateOfBirth = DateOfBirth(
                    day = preferences[ProfileDataStore.ProfileKeys.DOB_DAY] ?: 0,
                    month = preferences[ProfileDataStore.ProfileKeys.DOB_MONTH] ?: 0,
                    year = preferences[ProfileDataStore.ProfileKeys.DOB_YEAR] ?: 0
                ),

                workout = Workout(
                    choice = preferences[ProfileDataStore.ProfileKeys.WORKOUT] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.WORKOUT_VISIBILITY]
                        ?: true
                ),

                pets = Pets(
                    choice = preferences[ProfileDataStore.ProfileKeys.PETS] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.PETS_VISIBILITY] ?: true
                ),

                children = Children(
                    choice = preferences[ProfileDataStore.ProfileKeys.CHILDREN] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.CHILDREN_VISIBILITY]
                        ?: true
                ),

                smoking = Smoking(
                    choice = preferences[ProfileDataStore.ProfileKeys.SMOKING] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.SMOKING_VISIBILITY]
                        ?: true
                ),

                drinking = Drinking(
                    choice = preferences[ProfileDataStore.ProfileKeys.Drinking] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.DRINKING_VISIBILITY]
                        ?: true
                ),

                ethnicity = Ethnicity(
                    choice = preferences[ProfileDataStore.ProfileKeys.ETHNICITY] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.ETHNICITY_VISIBILITY]
                        ?: true
                ),

                gender = Gender(
                    choice = preferences[ProfileDataStore.ProfileKeys.GENDER] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.GENDER_VISIBILITY]
                        ?: true
                ),

                sexuality = Sexuality(
                    choice = preferences[ProfileDataStore.ProfileKeys.SEXUALITY] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.SEXUALITY_VISIBILITY]
                        ?: true
                ),

                languages = Languages(
                    languages = preferences[ProfileDataStore.ProfileKeys.LANGUAGES]?.toList()
                        ?: emptyList(),
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.LANGUAGES_VISIBILITY]
                        ?: true
                ),

                interests = Interests(
                    interests = preferences[ProfileDataStore.ProfileKeys.INTERESTS]?.toList()
                        ?: emptyList(),
                ),

                college = College(
                    collegeName = preferences[ProfileDataStore.ProfileKeys.COLLEGE] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.COLLEGE_VISIBILITY]
                        ?: true
                ),

                job = Job(
                    jobTitle = preferences[ProfileDataStore.ProfileKeys.JOB] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.JOB_VISIBILITY] ?: true
                ),

                height = Height(
                    heightInFeet = preferences[ProfileDataStore.ProfileKeys.HEIGHT] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.HEIGHT_VISIBILITY]
                        ?: true
                ),

                location = Location(
                    latitude = preferences[ProfileDataStore.ProfileKeys.LOCATION_LATITUDE]
                        ?.toDouble() ?: 0.0,
                    longitude = preferences[ProfileDataStore.ProfileKeys.LOCATION_LATITUDE]
                        ?.toDouble() ?: 0.0,
                    address = preferences[ProfileDataStore.ProfileKeys.LOCATION_ADDRESS]
                        ?: ""
                ),

                religion = Religion(
                    choice = preferences[ProfileDataStore.ProfileKeys.ZODIAC_SIGN] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.ZODIAC_SIGN_VISIBILITY]
                        ?: true
                ),

                zodiacSign = ZodiacSign(
                    sign = preferences[ProfileDataStore.ProfileKeys.ZODIAC_SIGN] ?: "",
                    isVisibleInProfile =
                    preferences[ProfileDataStore.VisibilityControlKeys.ZODIAC_SIGN_VISIBILITY]
                        ?: true
                ),

                photo1 = preferences[ProfileDataStore.ProfileKeys.PHOTO_1]?.let {
                    Photo(it, photoCount = 1)
                },
                photo2 = preferences[ProfileDataStore.ProfileKeys.PHOTO_2]?.let {
                    Photo(it, photoCount = 2)
                },
                photo3 = preferences[ProfileDataStore.ProfileKeys.PHOTO_3]?.let {
                    Photo(it, photoCount = 3)
                },
                photo4 = preferences[ProfileDataStore.ProfileKeys.PHOTO_4]?.let {
                    Photo(it, photoCount = 4)
                },
                photo5 = preferences[ProfileDataStore.ProfileKeys.PHOTO_5]?.let {
                    Photo(it, photoCount = 5)
                },
                photo6 = preferences[ProfileDataStore.ProfileKeys.PHOTO_6]?.let {
                    Photo(it, photoCount = 6)
                },
            )
        }.catch {
          emit(GetBack.Error())
        }

        emit(GetBack.Success(data))
    }

    override suspend fun syncProfileWithServer(): GetBackBasic {

        return try {
            val response = userApi.getProfile()

            if (!response.isSuccessful)
                response.message?.let {
                    GetBack.Error(it)  /// todo decide if have show a message if refresh failed
                } ?: GetBack.Error()
            else {
                response.data?.apply {
                    updateProfileToDataStore(name.toName())
                    updateProfileToDataStore(gender.toGender())
                    updateProfileToDataStore(ethnicity.toEthnicity())
                    updateProfileToDataStore(religion.toReligion())
                    updateProfileToDataStore(aboutMe.toAboutMe())
                    updateProfileToDataStore(smoking.toSmoking())
                    updateProfileToDataStore(drinking.toDrinking())
                    updateProfileToDataStore(job.toJob())
                    updateProfileToDataStore(sexuality.toSexuality())
                    updateProfileToDataStore(children.toChildren())
                    updateProfileToDataStore(zodiacSign.toZodiacSign())
                    updateProfileToDataStore(workout.toWorkOut())
                    updateProfileToDataStore(languages.toLanguages())
                    updateProfileToDataStore(location.toLocation())
                    updateProfileToDataStore(interests.toHobbies())
                    updateProfileToDataStore(height.toHeight())
                    updateProfileToDataStore(dateOfBirth.toDateOfBirth())
                    updateProfileToDataStore(college.toCollege())
                    updateProfileToDataStore(pets.toPets())
                    photo1?.let { updateProfileToDataStore(it.toPhoto()) }
                    photo2?.let { updateProfileToDataStore(it.toPhoto()) }
                    photo3?.let { updateProfileToDataStore(it.toPhoto()) }
                    photo4?.let { updateProfileToDataStore(it.toPhoto()) }
                    photo5?.let { updateProfileToDataStore(it.toPhoto()) }
                    photo6?.let { updateProfileToDataStore(it.toPhoto()) }
                }
            }
            GetBack.Success()
        } catch (e: IOException) {
            e.printStackTrace()
            GetBack.Error(SERVER_NOT_REACHED)
        } catch (e: HttpException) {
            e.printStackTrace()
            GetBack.Error(SOMETHING_WENT_WRONG)
        }
    }

    override suspend fun updateProfileToDataStore(
        requestType: ProfileModel,
    ): GetBackBasic {
        try {
            return when (requestType) {
                is AboutMe -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.ABOUT_ME, requestType.aboutYou)
                    GetBack.Success()
                }
                is Children -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.CHILDREN, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.CHILDREN_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is College -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.COLLEGE, requestType.collegeName)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.COLLEGE_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is DateOfBirth -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.DOB_DAY, requestType.day)
                    updateToDatastore(ProfileDataStore.ProfileKeys.DOB_MONTH, requestType.month)
                    updateToDatastore(ProfileDataStore.ProfileKeys.DOB_YEAR, requestType.year)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.AGE_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Drinking -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.Drinking, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.DRINKING_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Ethnicity -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.ETHNICITY, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.ETHNICITY_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }

                is Religion -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.RELIGION, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.RELIGION_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }

                is Gender -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.GENDER, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.GENDER_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }

                is Sexuality -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.SEXUALITY, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.SEXUALITY_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Height -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.HEIGHT, requestType.heightInFeet)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.HEIGHT_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Interests -> {
                    updateToDatastore(
                        ProfileDataStore.ProfileKeys.INTERESTS,
                        requestType.interests.toSet()
                    )
                    GetBack.Success()
                }
                is Job -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.JOB, requestType.jobTitle)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.JOB_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Languages -> {
                    updateToDatastore(
                        ProfileDataStore.ProfileKeys.LANGUAGES,
                        requestType.languages.toSet()
                    )
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.LANGUAGES_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Location -> {
                    updateToDatastore(
                        ProfileDataStore.ProfileKeys.LOCATION_LATITUDE,
                        requestType.latitude.toString()
                    )
                    updateToDatastore(
                        ProfileDataStore.ProfileKeys.LOCATION_LONGITUDE,
                        requestType.longitude.toString()
                    )
                    updateToDatastore(
                        ProfileDataStore.ProfileKeys.LOCATION_ADDRESS,
                        requestType.address
                    )
                    GetBack.Success()
                }
                is Name -> {
                    profileDatastore.edit { preferences ->
                        preferences[ProfileDataStore.ProfileKeys.NAME] = requestType.name
                    }
                    GetBack.Success()
                }
                is Pets -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.PETS, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.PETS_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Smoking -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.SMOKING, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.SMOKING_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Workout -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.WORKOUT, requestType.choice)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.WORKOUT_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is ZodiacSign -> {
                    updateToDatastore(ProfileDataStore.ProfileKeys.ZODIAC_SIGN, requestType.sign)
                    updateToDatastore(
                        ProfileDataStore.VisibilityControlKeys.ZODIAC_SIGN_VISIBILITY,
                        requestType.isVisibleInProfile
                    )
                    GetBack.Success()
                }
                is Photo -> {
                    when (requestType.photoCount) {
                        1 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_1,
                                requestType.photoUrl
                            ) // todo caption
                        }
                        2 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_2,
                                requestType.photoUrl
                            )
                        }
                        3 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_3,
                                requestType.photoUrl
                            )
                        }
                        4 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_4,
                                requestType.photoUrl
                            )
                        }
                        5 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_5,
                                requestType.photoUrl
                            )
                        }
                        6 -> {
                            updateToDatastore(
                                ProfileDataStore.ProfileKeys.PHOTO_6,
                                requestType.photoUrl
                            )
                        }
                    }
                    GetBack.Success()
                }
                else -> {
                    GetBack.Error()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return GetBack.Error(UPDATE_FAILED)
        }
    }

    override suspend fun updateProfileToServer(
        requestType: ProfileModel,
    ): GetBackBasic {
        try {
            var response: SimpleUserApiResponse? = null

            when (requestType) {
                is AboutMe -> {
                    response = userApi.updateAboutMe(fromAboutMe(requestType))
                }
                is Children -> {
                    response = userApi.updateChildren(fromChildren(requestType))
                }
                is College -> {
                    response = userApi.updateCollege(fromCollege(requestType))
                }
                is DateOfBirth -> {
                    response = userApi.updateDateOfBirth(fromDob(requestType))
                }
                is Drinking -> {
                    response = userApi.updateDrinking(fromDrinking(requestType))
                }
                is Ethnicity -> {
                    response = userApi.updateEthnicity(fromEthnicity(requestType))
                }
                is Religion -> {
                    response = userApi.updateReligion(fromReligion(requestType))
                }
                is Gender -> {
                    response = userApi.updateGender(fromGender(requestType))
                }
                is Sexuality -> {
                    response = userApi.updateSexuality(fromSexuality(requestType))
                }
                is Height -> {
                    response = userApi.updateHeight(fromHeight(requestType))
                }
                is Interests -> {
                    response = userApi.updateInterests(fromHobbies(requestType))
                }
                is Job -> {
                    response = userApi.updateJob(fromJob(requestType))
                }
                is Languages -> {
                    response = userApi.updateLanguages(fromLanguages(requestType))
                }
                is Location -> {
                    response = userApi.updateLocation(fromLocation(requestType))
                }
                is Name -> {
                    response = userApi.updateName(fromName(requestType))
                }
                is Pets -> {
                    response = userApi.updatePets(fromPets(requestType))
                }
                is Photo -> {
                    response = userApi.updatePhoto(fromPhoto(requestType))
                }
                is Smoking -> {
                    response = userApi.updateSmoking(fromSmoking(requestType))
                }
                is Workout -> {
                    response = userApi.updateWorkout(fromWorkout(requestType))
                }
                is ZodiacSign -> {
                    response = userApi.updateZodiacSign(fromZodiacSign(requestType))
                }
                else -> {
                    GetBack.Error()
                }
            }
            return response?.let { res ->
                if (res.isSuccessful)
                    GetBack.Success()
                else
                    res.message?.let { message ->
                        GetBack.Error(message)
                    } ?: GetBack.Error()
            } ?: GetBack.Error()

        } catch (e: IOException) {
            e.printStackTrace()
            return GetBack.Error(SERVER_NOT_REACHED)
        } catch (e: HttpException) {
            e.printStackTrace()
            return GetBack.Error(SOMETHING_WENT_WRONG)
        }
    }

    override suspend fun uploadPhotoToCloud(
        photo: Photo
    ): GetBack<Uri> {

        var uri: Uri? = null
        val uid = signedUserUseCases.getUid()

        val photoRef = firebaseStorage.child(
            firebaseStorageDefaultPath + uid +
                    "/photos/" + "${photo.photoCount}.jpg"
        )

        return try {

            uid?.let {
                photoRef
                    .putFile(Uri.parse(photo.photoUrl))
                    .await()
                uri = getUpdatedPhotoUrl(photoRef)
            }?: return GetBack.Error(SOMETHING_WENT_WRONG)

            GetBack.Success(uri)

        } catch (e: StorageException) {
            e.printStackTrace()
            GetBack.Error(UPLOAD_FAILED)
        }
    }

    private suspend fun getUpdatedPhotoUrl(
        photoReference: StorageReference
    ) = photoReference.downloadUrl.await()

    private suspend fun <T> updateToDatastore(
        key: Preferences.Key<T>,
        value: T
    ) {
        profileDatastore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {
        const val firebaseStorageDefaultPath = "Annyo/Users/"
    }

}