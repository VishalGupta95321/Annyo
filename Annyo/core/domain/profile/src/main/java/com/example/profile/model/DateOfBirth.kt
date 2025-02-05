package com.example.profile.model

import com.example.profile.util.ProfileModel

data class DateOfBirth(
    val day : Int,
    val month : Int,
    val year : Int,
    val isVisibleInProfile: Boolean = true
):ProfileModel(){
    companion object{
        fun monthFromString(month:String):Int{
            return when(month){
                "January" -> 1
                "February" -> 2
                "March" -> 3
                "April" -> 4
                "May" -> 5
                "June" -> 6
                "July" -> 7
                "August" -> 8
                "September" -> 9
                "October" -> 10
                "November" -> 11
                "December" -> 12
                else -> 0
            }
        }

        fun monthFromInt(
            month:Int
        ):String{
            return when(month){
                1 -> "January"
                2 -> "February"
                3 -> "March"
                4 -> "April"
                5 -> "May"
                6 -> "June"
                7 ->"July"
                8 -> "August"
                9 -> "September"
                10 -> "October"
                11 -> "November"
                12  -> "December"
                else -> "Month"
            }
        }
    }
}