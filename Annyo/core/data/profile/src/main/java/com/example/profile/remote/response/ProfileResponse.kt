package com.example.profile.remote.response

import com.example.profile.model.Profile

data class ProfileResponse (
    val aboutMe: AboutMeResponse,
    val name : NameResponse,
    val dateOfBirth: DateOfBirthResponse,
    val workout: WorkoutResponse,
    val pets: PetsResponse,
    val children: ChildrenResponse,
    val smoking: SmokingResponse,
    val drinking: DrinkingResponse,
    val ethnicity: EthnicityResponse,
    val religion : ReligionResponse,
    val gender: GenderResponse,
    val sexuality: SexualityResponse,
    val languages: LanguagesResponse,
    val interests: InterestsResponse,
    val college: CollegeResponse,
    val job: JobResponse,
    val height: HeightResponse,
    val location: LocationResponse,
    val zodiacSign: ZodiacSignResponse,
    val photo1 : PhotoResponse?,
    val photo2 : PhotoResponse?,
    val photo3 : PhotoResponse?,
    val photo4 : PhotoResponse?,
    val photo5 : PhotoResponse?,
    val photo6 : PhotoResponse?,
){
    fun toProfile()  = Profile(
        aboutMe = aboutMe.toAboutMe(),
        name = name.toName(),
        dateOfBirth = dateOfBirth.toDateOfBirth(),
        workout = workout.toWorkOut(),
        pets = pets.toPets(),
        religion = religion.toReligion(),
        children = children.toChildren(),
        smoking = smoking.toSmoking(),
        drinking = drinking.toDrinking(),
        ethnicity = ethnicity.toEthnicity(),
        gender = gender.toGender(),
        sexuality = sexuality.toSexuality(),
        languages = languages.toLanguages(),
        interests = interests.toHobbies(),
        college = college.toCollege(),
        job = job.toJob(),
        height = height.toHeight(),
        location = location.toLocation(),
        zodiacSign = zodiacSign.toZodiacSign(),
        photo1 = photo1?.toPhoto(),
        photo2 = photo2?.toPhoto(),
        photo3 = photo1?.toPhoto(),
        photo4 = photo2?.toPhoto(),
        photo5 = photo1?.toPhoto(),
        photo6 = photo2?.toPhoto(),
    )
}


//todo(i should fix this all the request and response classes are the same , means a lot of code duplication)