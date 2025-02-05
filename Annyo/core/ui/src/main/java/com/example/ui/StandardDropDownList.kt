package com.example.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    isAlwaysExpanded : Boolean = false,
    title: String,
    options: DropDownListOptions,
    listHeight: Dp,
    onSelect: (String) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(isAlwaysExpanded)
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
          //  .verticalScroll(rememberScrollState())
            .requiredWidthIn(min = 80.dp)
            .then(modifier)
    ) {
        StandardButton(
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(35.dp))
                .fillMaxWidth()
                .height(50.dp),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = {
                isExpanded = !isExpanded
            }) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = ImageVector.vectorResource(
                        id = if (isExpanded)
                            R.drawable.arrow_up_icon
                        else R.drawable.arrow_down_icon
                    ),
                    contentDescription = null
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(30.dp)
                )
                .clip(RoundedCornerShape(30.dp))
                .animateDropdownList(isExpanded, listHeight)
                .verticalScroll(rememberScrollState())
        ) {
            options.getChoices().forEach {
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable {
                            onSelect(it)
                            isExpanded = !isExpanded
                        },
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    text = it,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

fun Modifier.animateDropdownList(
    isExpanded: Boolean,
    maxHeight: Dp,
): Modifier = composed {

    val height by animateDpAsState(
        targetValue = if (!isExpanded) 0.dp else maxHeight,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    height(height)
}

interface DropDownListOptions {
    fun getChoices(): List<String>
}

interface ChipGroupChoices {
    fun getChoices(): List<String>
}

class HeightOptions : DropDownListOptions {
    override fun getChoices(): List<String> {
        return getHeightsInFeet()
    }

    private fun getHeightsInFeet(): List<String> {
        val heightsInFeet: MutableList<String> = mutableListOf()
        var feet = 5
        var inch = 0
        repeat(25) {
            heightsInFeet.add("$feet'$inch\"")
            inch++
            if (inch > 11) {
                feet++
                inch = 0
            }
        }
        return heightsInFeet
    }
}

class DaysOfMonthOptions : DropDownListOptions {
    override fun getChoices(): List<String> {
        return getDaysOfMonth()
    }

    private fun getDaysOfMonth(): List<String> {
        var days: List<String> = listOf()
        for (day in (1..31)) {
            days = days + day.toString()
        }
        return days.toList()
    }
}


class MonthsOptions : DropDownListOptions {
    override fun getChoices(): List<String> {
        return listOf(
            "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"
        )
    }
}

class YearOptions : DropDownListOptions {
    override fun getChoices(): List<String> {
        return getYears()
    }

    private fun getYears(): List<String> {
        var years: List<String> = listOf()
        for (year in (1910..2023)) {
            years = years + year.toString()
        }
        return years
    }
}


class ReligionChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Agnostic", "Atheist", "Catholic", "Buddhist", "Christian", "Hindu",
            "Muslim", "Sikh", "Spiritual"
        ).sorted() + "Others / Prefer Not To Say"
    }
}

class EthnicityChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "American Indian", "American African", "Indian", "Asian",
            "Middle Eastern", "Latin", "Black/African", "White/Caucasian",
        ).sorted() + "Others / Prefer Not To Say"

    }
}

class PetsChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Dog", "Cat", "Reptile", "Birds",
            "Fish"
        ).sorted() + "Other/Prefer Not To Say"
    }
}

class ChildrenChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Have Children", "Open to Children", "Don't Want Children",
            "Not Sure"
        ).sorted() + "Prefer Not To Say"
    }
}

class ZodiacSignChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Aries",
            "Taurus",
            "Gemini",
            "Cancer",
            "Leo",
            "Virgo",
            "Libra",
            "Scorpio",
            "Sagittarius",
            "Capricorn",
            "Aquarius",
            "Pisces"
        ).sorted() + "Prefer Not To Say"
    }
}

class GenderChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Man", "Woman", "Other"
        )
    }
}

class SexualityChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Straight","Gay","Lesbian","Bisexual","Asexual","DemiSexual",
            "PanSexual","Queer","Bicurious","Aromantic"
        )
    }
}

class DrinkingChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "Yes", "No", "Socially", "Often"
        ).sorted() + "Prefer Not To Say"
    }
}

class SmokingChoices : ChipGroupChoices {

    override fun getChoices(): List<String> {
        return listOf(
            "Yes", "No", "Socially", "Often"
        ).sorted() + "Prefer Not To Say"
    }
}

class WorkoutChoices : ChipGroupChoices {
    override fun getChoices(): List<String> {
        return listOf(
            "EveryDay", "SomeTimes", "Never",
            "Often"
        )
    }
}

class InterestsChoices(
     newInterests : List<String> = listOf()
) : ChipGroupChoices {

    val interests = newInterests + listOf(
        "English", "Mandarin Chinese", "Hindi", "Spanish",
        "French", "Arabic", "Bengali", "Russian", "Portuguese",
        "Indonesian", "Urdu", "Japanese", "German", "Punjabi",
        "Javanese", "Wu Chinese", "Telugu", "Turkish", "Korean", "Marathi"
    ).sorted()

    override fun getChoices(): List<String> {
        return interests.distinct()
    }
}

class LanguagesChoices(
    private val query : String?
) : ChipGroupChoices {

    override fun getChoices(): List<String> {
        val choices  = listOf(
            "English", "Mandarin Chinese", "Hindi", "Spanish",
            "French", "Arabic", "Bengali", "Russian", "Portuguese",
            "Indonesian", "Urdu", "Japanese", "German", "Punjabi",
            "Javanese", "Wu Chinese", "Telugu", "Turkish", "Korean", "Marathi",
            "English", "Mandarin Chinese", "Hindi", "Spanish",
            "French", "Arabic", "Bengali", "Russian", "Portuguese",
            "Indonesian", "Urdu", "Japanese", "German", "Punjabi",
            "Javanese", "Wu Chinese", "Telugu", "Turkish", "Korean", "Marathi",
        ).sorted()

        return if (query == null)
            choices
        else
            choices.filter {
                it.contains(query, true)
            }
    }
}