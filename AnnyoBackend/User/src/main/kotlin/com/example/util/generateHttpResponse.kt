package com.example.util

import com.example.models.responce.HttpResponse
import com.example.models.responce.UserApiResponse
import io.ktor.http.*

fun <T>generateHttpResponse(response: Response<T>):HttpResponse<UserApiResponse<T>>{
    return when(response){
        is Response.Error -> {
           HttpResponse(
               code = response.error!!.code,
           )
        }
        is Response.Success -> {
                HttpResponse(
                    code = HttpStatusCode.OK,
                    body = UserApiResponse(response.data)
                )
        }
    }
}



//Informational responses (100 – 199)
//Successful responses (200 – 299)
//Redirection messages (300 – 399)
//Client error responses (400 – 499)
//Server error responses (500 – 599)