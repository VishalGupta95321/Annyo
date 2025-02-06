package com.example.di

import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {

    single{
        val mongoClient = KMongo.createClient().coroutine
        mongoClient.getDatabase("Test")  // todo only for test
    }
    single<UserRepository>{
        UserRepositoryImpl(get())
    }

}
