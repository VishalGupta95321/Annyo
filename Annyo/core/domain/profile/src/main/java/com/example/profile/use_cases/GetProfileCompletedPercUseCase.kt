package com.example.profile.use_cases

import com.example.profile.model.Profile
import com.example.profile.model.isCompleted

class GetProfileCompletedPercUseCase {

    operator fun invoke(profile: Profile): Int {

        var completedProfilePercentage = 0

        profile.isCompleted(
            aboutMe = { isCompleted ->
                if (isCompleted)
                    completedProfilePercentage += 10
            },
            interests = { isCompleted ->
                if (isCompleted)
                    completedProfilePercentage += 10
            },
            languages = { isCompleted ->
                if (isCompleted)
                    completedProfilePercentage += 10
            },
            job = { isCompleted ->
                if (isCompleted)
                    completedProfilePercentage += 10
            },
            college = { isCompleted ->
                if (isCompleted)
                    completedProfilePercentage += 10
            },
            photos = { P1, P2, P3, P4, P5, P6 ->
                var addedPhotosPerc = 0
                if (P1) addedPhotosPerc+=10
                if (P2) addedPhotosPerc+=10
                if (P3) addedPhotosPerc+=10
                if (P4) addedPhotosPerc+=10
                if (P5) addedPhotosPerc+=10
                if (P6) addedPhotosPerc+=10

                completedProfilePercentage +=
                    if (addedPhotosPerc<40)
                    addedPhotosPerc
                    else 40
            }
        )
        return completedProfilePercentage
    }
}


//// adjust this use case acc. to your need
