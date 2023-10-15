package com.narify.chickycart.util

import java.math.RoundingMode
import java.text.DecimalFormat

fun roundUpToSinglePrecision(number: Double): Float {
    return DecimalFormat("#.#")
        .apply { roundingMode = RoundingMode.CEILING }
        .format(number)
        .toFloat()
}
