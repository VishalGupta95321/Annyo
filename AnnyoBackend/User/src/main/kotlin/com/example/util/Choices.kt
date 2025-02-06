package com.example.util

sealed interface Choices {

    enum class Gender(val text : String):Choices{
        MAN("Man"),
        WOMAN("Woman"),
        NON_BINARY("Non Binary")
    }
    enum class Religion(val text : String):Choices{
        AGNOSTIC("Agnostic"),
        ATHEIST("Atheist"),
        BUDDHIST("Buddhist"),
        CATHOLIC("Catholic"),
        CHRISTIAN("Christian"),
        HINDU("Hindu"),
        JEWISH("Jewish"),
        SIKH("Sikh"),
        OTHER("Other"),
        PREFER_NOT_TO_SAY("Prefer not to say")
    }

    enum class AboutChildren(val text : String):Choices{
        HAVE_CHILDREN("Have Children"),
        DON_NOT_HAVE_CHILDREN("Don't Have Children"),
        OPEN_TO_CHILDREN("Open to Children"),
        NOT_SURE_YET("Not Sure Yet"),
        PREFER_NOT_TO_SAY("Prefer not to say")
    }

    enum class Sexuality(val text : String):Choices{
        STRAIGHT("Straight"),
        GAY("Gay"),
        LESBIAN("Lesbian"),
        BISEXUAL("Bisexual"),
        NOT_MENTIONED("Not Mentioned"),
        PREFER_NOT_TO_SAY("Prefer not to say")
    }

    enum class SmokingDrinking(val text : String):Choices{
        YES("Yes"),
        NO("No"),
        SOMETIMES("Sometimes"),
        PREFER_NOT_TO_SAY("Prefer not to say")
    }
}