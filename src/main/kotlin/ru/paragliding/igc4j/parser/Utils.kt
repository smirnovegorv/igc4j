package ru.paragliding.igc4j.parser

object Utils {

    fun degreesToDecimal(degrees: Int, minutes: Double): Double {
        return degrees + (minutes * 60.0) / 3600
    }

    /**
     * Parses latitude strings in DDMMmmmN/S format to decimal
     */
    fun parseLatitude(latitude: String): Double {
        if (latitude.length != 8) {
            throw IllegalArgumentException("Latitude string shall have 8 symbols")
        }
        try {
            val degrees = latitude.substring(0, 2).toInt()
            val minutes = (latitude.substring(2,4) + "." + latitude.substring(4, 7)).toDouble()
            val rz = degreesToDecimal(degrees, minutes)
            val sign = latitude.substring(7, 8)
            return when (sign) {
                "N" -> rz
                "S" -> -rz
                else -> throw IllegalArgumentException("Latitude value shall end with N or S, not with $sign")
            }
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Failed to parse digits in latitude", ex)
        }
    }

    /**
     * Parses longitude strings in DDDMMmmmE/W format to decimal
     */
    fun parseLongitude(longitude: String): Double {
        if (longitude.length != 9) {
            throw IllegalArgumentException("Longitude string shall have 8 symbols")
        }
        try {
            val degrees = longitude.substring(0, 3).toInt()
            val minutes = (longitude.substring(3,5) + "." + longitude.substring(5, 8)).toDouble()
            val rz = degreesToDecimal(degrees, minutes)
            val sign = longitude.substring(8, 9)
            return when (sign) {
                "E" -> rz
                "W" -> -rz
                else -> throw IllegalArgumentException("Longitude value shall end with E or W, not with $sign")
            }
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Failed to parse digits in longitude", ex)
        }
    }

    /**
     * Parses HHmmss timestamps to longs (number of milliseconds since start of day)
     */
    fun parseTimestamp(timestampString: String): Long {
        if (timestampString.length != 6) {
            throw IllegalArgumentException("IGC time shall be 6 digits long, but is '$timestampString'")
        }
        try {
            val hours = timestampString.substring(0, 2).toInt()
            val minutes = timestampString.substring(2, 4).toInt()
            val seconds = timestampString.substring(4, 6).toInt()

            return (seconds + minutes * 60 + hours * 3600) * 1000L
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Failed to parse, invalid symbols in time string", ex)
        }
    }
}