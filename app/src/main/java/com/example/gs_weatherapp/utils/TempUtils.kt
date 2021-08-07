package com.example.gs_weatherapp.utils

import com.example.gs_weatherapp.model.Main
import java.math.RoundingMode
import java.text.DecimalFormat

fun Main.toCelsius() = apply {
    temp = (temp - Constants.KELVIN).roundOffDecimal()
    tempMin = (tempMin - Constants.KELVIN).roundOffDecimal()
    tempMax = (tempMax - Constants.KELVIN).roundOffDecimal()

}

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}