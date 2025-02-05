package com.app.annyo.data.repository

import com.app.annyo.data.entity.User
import com.app.annyo.data.model.request.UpdateUserRequest
import com.app.annyo.data.utils.DatabaseConstants.USERS_COLLECTION
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull

class UsersRepositoryImpl(
    private val database: MongoDatabase
) : UsersRepository {

    private val users = database.getCollection<User>(USERS_COLLECTION)

    override suspend fun addUser(uid: String): GetBackBasic {
        return try {
            users.insertOne(User(id = uid)).wasAcknowledged().let { wasAcknowledged ->
                if (wasAcknowledged)
                    GetBack.Success()
                else
                    GetBack.Error()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GetBack.Error()
        }
    }

    override suspend fun getUserById(uid: String): GetBack<User> {
        val filters = Filters.eq(User::id.name, uid)
        val user = users.find(filters).firstOrNull()

        return if (user == null)
            GetBack.Error()
        else
            GetBack.Success(user)

    }

    override suspend fun updateUserById(request: UpdateUserRequest): GetBackBasic {
        when (request) {
            is UpdateUserRequest.UpdateUserCurrentSessionId -> {
                val updates = Updates.set(
                    User::currentCallSessionId.name, request.currentCallSessionId
                )
                val filters = Filters.eq(User::id.name, request.uid)

                return try {
                    users.updateOne(filters, updates).wasAcknowledged().let { wasAcknowledged ->
                        if (wasAcknowledged)
                            GetBack.Success()
                        else
                            GetBack.Error()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    GetBack.Error()
                }
            }
        }
    }
}