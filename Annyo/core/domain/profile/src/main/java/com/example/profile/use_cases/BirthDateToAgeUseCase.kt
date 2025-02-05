package com.example.profile.use_cases

import com.example.profile.model.DateOfBirth
import kotlinx.datetime.*

class BirthDateToAgeUseCase {
    operator fun invoke(
        date: DateOfBirth
    ): Int {
        val currentDate = Clock.System
            .now()
            .toLocalDateTime(
                TimeZone.currentSystemDefault()
            ).date
        val birthDate = LocalDate(date.year, date.month, date.day)

        return birthDate.yearsUntil(currentDate)
    }
}