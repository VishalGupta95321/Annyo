package com.example.profile.model

data class Profile(
    val aboutMe: AboutMe,
    val name : Name,
    val dateOfBirth: DateOfBirth,
    val workout: Workout,
    val pets: Pets,
    val children: Children,
    val smoking: Smoking,
    val drinking: Drinking,
    val ethnicity: Ethnicity,
    val religion: Religion,
    val gender: Gender,
    val sexuality: Sexuality,
    val languages: Languages,
    val interests: Interests,
    val college: College,
    val job: Job,
    val height: Height,
    val location: Location,
    val zodiacSign: ZodiacSign,
    val photo1: Photo? = null,
    val photo2: Photo? = null,
    val photo3: Photo? = null,
    val photo4: Photo? = null,
    val photo5: Photo? = null,
    val photo6: Photo? = null,
)

fun Profile.isCompleted(
    name: (isCompleted:Boolean) -> Unit = {},
    aboutMe: (isCompleted:Boolean) -> Unit = {},
    dateOfBirth: (isCompleted:Boolean) -> Unit = {},
    workout: (isCompleted:Boolean) -> Unit = {},
    pets: (isCompleted:Boolean) -> Unit = {},
    children: (isCompleted:Boolean) -> Unit = {},
    smoking: (isCompleted:Boolean) -> Unit = {},
    drinking: (isCompleted:Boolean) -> Unit = {},
    ethnicity: (isCompleted:Boolean) -> Unit = {},
    religion: (isCompleted:Boolean) -> Unit= {} ,
    gender: (isCompleted:Boolean) -> Unit = {},
    sexuality: (isCompleted:Boolean) -> Unit = {},
    languages: (isCompleted:Boolean) -> Unit = {},
    interests: (isCompleted:Boolean) -> Unit = {},
    college: (isCompleted:Boolean) -> Unit = {},
    job: (isCompleted:Boolean) -> Unit = {},
    height: (isCompleted:Boolean) -> Unit = {},
    location: (isCompleted:Boolean) -> Unit = {},
    zodiacSign: (isCompleted:Boolean) -> Unit = {},
    photos: (
        photo1:Boolean,
        photo2:Boolean,
        photo3:Boolean,
        photo4:Boolean,
        photo5:Boolean,
        photo6:Boolean
    ) -> Unit = { _, _, _, _, _, _ -> },
){
    name(this.name.name.isNotBlank())
    gender(this.gender.choice.isNotBlank())
    sexuality(this.sexuality.choice.isNotBlank())
    dateOfBirth(this.dateOfBirth.day!=0)
    height(this.height.heightInFeet.isNotBlank())
    ethnicity(this.ethnicity.choice.isNotBlank())
    religion(this.religion.choice.isNotBlank())
    zodiacSign(this.zodiacSign.sign.isNotBlank())
    college(this.college.collegeName.isNotBlank())
    job(this.job.jobTitle.isNotBlank())
    location(this.location.address.isNotBlank())
    children(this.children.choice.isNotBlank())
    smoking(this.smoking.choice.isNotBlank())
    drinking(this.drinking.choice.isNotBlank())
    workout(this.workout.choice.isNotBlank())
    pets(this.pets.choice.isNotBlank())
    interests(this.interests.interests.isNotEmpty())
    languages(this.languages.languages.isNotEmpty())
    aboutMe(this.aboutMe.aboutYou.isNotBlank())
    this.run {
        photos(
            photo1!=null,
            photo2!=null,
            photo3!=null,
            photo4!=null,
            photo5!=null,
            photo6!=null
        )
    }
}
