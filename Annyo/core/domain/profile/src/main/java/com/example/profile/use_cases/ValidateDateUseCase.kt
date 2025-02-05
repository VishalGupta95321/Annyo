package com.example.profile.use_cases

import kotlinx.datetime.LocalDate

class ValidateDateUseCase {
    operator fun invoke(
        year:Int,
        month:Int,
        day:Int
    ):Boolean{

        if (year==0)        ///// this is because when we giving year as 0 in local date it still giving true
            return false

        return try {
            LocalDate(year,month,day)
            true
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
            println("failed use case")
            false
        }
    }
}