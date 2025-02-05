package com.app.annyo.test

import com.example.models.entity.User
import com.mongodb.client.model.CreateCollectionOptions
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


private const val DATABASE_NAME = "Test"

fun main(){

    /// creating an instance of Mongo client
    val mongoClient  = MongoClient.create()

    /// getting the database
    val database = mongoClient.getDatabase(DATABASE_NAME)

    ///
    val db2 = mongoClient.getDatabase("Heyy")


    val collection  = database.getCollection<User>("user")


    runBlocking {
//        db2.createCollection("heyy", CreateCollectionOptions())
//        val col2 = db2.getCollection<PaintOrder>("heyy")
//
//        col2.insertOne(PaintOrder(1,"eedwed",))
//
//        println(collection.find(eq("_id","111 ")).firstOrNull())
//
//        delay(100000)
//        val date  =Date()
//        //val a = Date()// LocalDateTime.now().toLocalDate()
    }
}

@Serializable
data class PaintOrder(
    @SerialName("_id") // Use instead of @BsonId
    @Contextual val id: ObjectId?,
    val color: String,
    val date: Date,
    val qty: Int,
    @SerialName("brand")
    val manufacturer: String = "Adfgbdse" // Use instead of @BsonProperty
)

data class Id(
    @BsonId
    val id:Int,
    val date:Date
)

suspend fun ttlTest(
    mongoClient:MongoClient
){
    val db = mongoClient.getDatabase("ttltest")
    val col = db.getCollection<ttltest>("ttltest")
    col.insertOne(ttltest("111",Date()))
    val index = col.createIndex(Indexes.ascending(ttltest::disconnectedAt.name), IndexOptions().expireAfter(10L,TimeUnit.SECONDS))
    /// the deleting thread runs every 60 seconds so the documents may not be deleted immediately
}

data class ttltest(
    @BsonId
    val id: String,
    val disconnectedAt: Date,
)




///  i think as of now i am going to create index on server side
///  use koltlin serialization for data format in collection
///  all the thimgs are in documentation
///  use upsert so you just have to use update() functions that will add document if does not exist.
///  intent tab was opened before
///  differece between serivce and controiller later in micro service
///  seems like service is just middle ware between repository and contorllers