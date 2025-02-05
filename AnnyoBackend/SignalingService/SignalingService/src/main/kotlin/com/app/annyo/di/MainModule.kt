package com.app.annyo.di

import com.app.annyo.controller.SignallingController
import com.app.annyo.data.entity.User
import com.app.annyo.data.firebase_messaging.FirebaseMessagingClient
import com.app.annyo.data.firebase_messaging.FirebaseMessagingClientImpl
import com.app.annyo.data.repository.UsersRepository
import com.app.annyo.data.repository.UsersRepositoryImpl
import com.app.annyo.data.utils.DatabaseConstants
import com.app.annyo.data.utils.DatabaseConstants.SIGNALLING_DATABASE_NAME
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val MainModule  = module {

    single {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

        FirebaseApp.initializeApp(options) // TODO(Try removing the initialize)

        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging
    }

    single<FirebaseMessagingClient> {
        FirebaseMessagingClientImpl(get())
    }

    single<Json> {
      Json{ignoreUnknownKeys = true}
    }

    single<SignallingController> {
        SignallingController(get(),get())
    }

    single {
        val mongoClient = MongoClient.create()
        val database = mongoClient.getDatabase(SIGNALLING_DATABASE_NAME)
        database
    }

    single<UsersRepository>{
        UsersRepositoryImpl(get())
    }

}