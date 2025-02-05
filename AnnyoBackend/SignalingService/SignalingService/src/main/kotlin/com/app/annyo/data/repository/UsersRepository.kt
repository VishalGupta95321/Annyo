package com.app.annyo.data.repository

import com.app.annyo.data.entity.User
import com.app.annyo.data.model.request.UpdateUserRequest
import com.example.utils.GetBack
import com.example.utils.GetBackBasic

interface UsersRepository {
    suspend fun addUser(uid: String):GetBackBasic
    suspend fun getUserById(uid: String):GetBack<User>
    suspend fun updateUserById(request: UpdateUserRequest):GetBackBasic
}