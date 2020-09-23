package com.hack.antisurveillance.domain.objects

enum class Themes {
    DAY,
    NIGHT
}

fun Int.getTheme(): Themes {
    return when (this) {
        0 -> Themes.DAY
        1 -> Themes.NIGHT
        else -> Themes.DAY
    }
}