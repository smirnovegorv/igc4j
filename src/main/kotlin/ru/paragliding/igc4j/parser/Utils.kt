package ru.paragliding.igc4j.parser

object Utils {

    fun degreesToDecimal(degrees: Int, minutes: Double): Double {
        return degrees + (minutes * 60.0) / 3600
    }
}