package com.hack.antisurveillance.domain.objects

import java.util.*

enum class Languages {
    RUSSIAN,
    ENGLISH
}

fun Int.lang(): Languages = when (this) {
    0 -> Languages.RUSSIAN
    1 -> Languages.ENGLISH
    else -> throw IllegalArgumentException("Unknown lang original position")
}

fun Int.getLocale(): Locale {
    return when (this) {
        0 -> Locale("ru", "RU")
        1 -> Locale("en", "US")
        else -> Locale("ru", "RU")
    }
}

fun Languages.getLocale(): Locale {
    return when (this) {
        Languages.RUSSIAN -> Locale("ru", "RU")
        Languages.ENGLISH -> Locale("en", "US")
    }
}