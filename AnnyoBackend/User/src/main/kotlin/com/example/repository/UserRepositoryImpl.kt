package com.example.repository

import com.example.exception.HttpErrors
import com.example.models.entity.*
import com.example.models.request.*
import com.example.models.responce.BasicResponse
import com.example.models.responce.ProfileResponse
import com.example.models.responce.UserApiResponse
import com.example.util.Response
import com.example.util.BasicUpdateRequestType
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.pos
import org.litote.kmongo.setValue

class UserRepositoryImpl(
    db: CoroutineDatabase
):UserRepository {


    private val users = db.getCollection<User>()


    override suspend fun createProfile(request: CreateProfileRequest): Response<BasicResponse> {

        val insertOneResult = users.insertOne(request.user)

        return if (insertOneResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else{
            Response.Error(error = HttpErrors.SomethingWentWrong)}
    }

    override suspend fun updateBasicProfile(uid:String,request: BasicUpdateRequest):  Response<BasicResponse>  {

        val updateResult: UpdateResult =   when(request.type){
            BasicUpdateRequestType.AboutChildren -> {
                users.updateOneById(id = uid , update = setValue(User::aboutChildren,request.data))
            }
            BasicUpdateRequestType.CollegeName -> {
                users.updateOneById(id = uid , update = setValue(User::collegeName,request.data))
            }
            BasicUpdateRequestType.Drinking -> {
                users.updateOneById(id = uid , update = setValue(User::drinking,request.data))
            }
            BasicUpdateRequestType.EmailId -> {
                users.updateOneById(id = uid , update = setValue(User::emailId,request.data))
            }
            BasicUpdateRequestType.Gender -> {
                users.updateOneById(id = uid , update = setValue(User::gender,request.data))
            }
            BasicUpdateRequestType.HeightInFeet -> {
                users.updateOneById(id = uid , update = setValue(User::height,request.data.toDouble()))
            }
            BasicUpdateRequestType.JobTitle -> {
                users.updateOneById(id = uid , update = setValue(User::jobTitle,request.data))
            }
            BasicUpdateRequestType.Religion -> {
                users.updateOneById(id = uid , update = setValue(User::religion,request.data))
            }
            BasicUpdateRequestType.Sexuality -> {
                users.updateOneById(id = uid , update = setValue(User::sexuality,request.data))
            }
            BasicUpdateRequestType.Smoking -> {
                users.updateOneById(id = uid , update = setValue(User::smoking,request.data))
            }
            BasicUpdateRequestType.WorkPlace -> {
                users.updateOneById(id = uid , update = setValue(User::workPlace,request.data))
            }
            BasicUpdateRequestType.Age -> {
                users.updateOneById(id = uid , update = setValue(User::age,request.data.toInt()))
            }
        }
        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun updateLocation(uid:String,request: UpdateLocationRequest):  Response<BasicResponse>  {
        val updateResult: UpdateResult = users.updateOneById(
            id = uid ,
            update = setValue(User::location,request.location
            ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun updateRecommendationPreferences(uid:String,request: UpdateRecommendationPreferencesRequest):  Response<BasicResponse>  {

        val updateResult = users.updateOneById(
            id = uid,
            update = setValue(User::recommendationPreferences,request.toRecommendationPreferences()
            ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun updatePhoto(uid:String,request: UpdatePhotoRequest): Response<BasicResponse> {

        val position = request.photoCount

        val updateResult = users.updateOneById(
            id = uid,
            update = setValue(User::photos.pos(position),request.toPhoto()
            ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun updateWrittenPrompt(uid:String,request: UpdateWrittenPromptRequest):  Response<BasicResponse>  {

        val position = request.promptCount
        val updateResult = users.updateOneById(
            id = uid,
            update = setValue(User::writtenPrompts.pos(position),request.toWrittenPrompt()
            ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun updateDob(uid: String, request: UpdateDobRequest):  Response<BasicResponse>  {
        val updateResult = users.updateOneById(
            id = uid, update = setValue(User::dob,request.dob
        ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)

    }

    override suspend fun updateName(uid: String, request: UpdateNameRequest):  Response<BasicResponse>  {
        val updateResult = users.updateOneById(
            id = uid, update = setValue(User::name,request.name
            ))

        return if (updateResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

    override suspend fun getUserProfile(uid: String): Response<ProfileResponse> {

        val userProfileResponse = users.findOneById(uid)?.toProfileResponse()

        return if (userProfileResponse!=null){
            Response.Success(userProfileResponse)
        } else Response.Error(error = HttpErrors.SomethingWentWrong)

    }

    override suspend fun getUserProfiles(cellId: String): Response<List<ProfileResponse>> {
//        val userProfilesResponse = users.find(User::uid eq "lkmdc").toList()  // list could  be empty
//           val dcsdcs =  userProfilesResponse//?.toProfileResponse()

//
//        return if (userProfileResponse!=null){
//            Response.Success(userProfileResponse)
//        } else Response.Error(error = Errors.SomethingWentWrong)
        TODO()
    }

    override suspend fun deleteUserProfile(uid: String):  Response<BasicResponse>  {
        val deleteResult = users.deleteOneById(uid)

        return if (deleteResult.wasAcknowledged()){
            Response.Success(BasicResponse())
        } else Response.Error(error = HttpErrors.SomethingWentWrong)
    }

}