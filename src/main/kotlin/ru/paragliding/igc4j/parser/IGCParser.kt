package ru.paragliding.igc4j.parser

import ru.paragliding.igc4j.model.*
import java.io.*

/**
 * Class that parses IGC streams and files
 */
object IGCParser {

    @Throws(InvalidIGCFormatException::class)
    fun parse(igcFile: File): IGCTrack {
        return parse(FileInputStream(igcFile))
    }

    @Throws(InvalidIGCFormatException::class)
    fun parse(igcText: String): IGCTrack {
        return parse(StringBufferInputStream(igcText))
    }

    @Throws(InvalidIGCFormatException::class)
    fun parse(igcStream: InputStream): IGCTrack {
        val points = mutableListOf<IGCTrackPoint>()
        val metadata = IGCMetadata()
        var hasMetadata = false
        BufferedReader(InputStreamReader(igcStream)).use { br ->
            br.lines().filter { !it.isNullOrBlank() }.forEach {
                when (it[0]) {
                    'A' -> {
                        readARecord(metadata, it)
                        hasMetadata = true
                    }
                    'H' -> {
                        readHRecord(metadata, it)
                        hasMetadata = true
                    }
                    'B' -> points.add(readBRecord(it))
                }
            }
        }


        return IGCTrack(if (hasMetadata) metadata else null, points)
    }

    /**
     * Reads optional A record that contains device metadata
     */
    private fun readARecord(metadata: IGCMetadata, record: String) {
        if (record.length < 7) {
            throw InvalidIGCFormatException("A record must have at least 7 symbols as 'AXXXYYY', but it is '$record'")
        }

        metadata.manufacturerInfo = ManufacturerInfo(
            record.substring(1, 4),
            record.substring(4, 7),
            if (record.length > 7) record.substring(7, record.length).trim() else null
        )
    }

    /**
     * Reads a subset of metadata records (pilot, glider information) that start with H
     * Some records seem to have different variants depending on logging device, e.g. date may be HFDTE and HFDTEDATE
     */
    private fun readHRecord(metadata: IGCMetadata, record: String) {
        if (record.startsWith("HFDTEDATE:")) {
            if (record.length < 16) {
                throw InvalidIGCFormatException("HFDTEDATE record must have 6 date digits, but it is $record")
            }
            metadata.date = record.substring(10, 16)
        } else if (record.startsWith("HFDTE")) {
            if (record.length < 11) {
                throw InvalidIGCFormatException("HFDTE record must have 6 date digits, but it is $record")
            }
            metadata.date = record.substring(5, 11)
        } else if (record.startsWith("HFPLTPILOTINCHARGE:")) {
            metadata.pilotName = record.substring(19, record.length).trim()
        } else if (record.startsWith("HFPLTPILOT:")) {
            metadata.pilotName = record.substring(11, record.length).trim()
        } else if (record.startsWith("HFCIDCOMPETITIONID:")) {
            metadata.gliderNumber = record.substring(19, record.length).trim()
        } else if (record.startsWith("HFFXA")) {
            if (record.length < 8) {
                throw InvalidIGCFormatException("HFFXA record must have 3 precision digits, but it is $record")
            }
        } else if (record.startsWith("HFGTYGLIDERTYPE:")) {
            metadata.gliderType = record.substring(16, record.length).trim()
        }  else if (record.startsWith("HFCCL")) {
            metadata.competitionClass = record.substring(22, record.length).trim()
        }
    }

    /**
     * Reads B records that contain actual track points
     */
    private fun readBRecord(record: String): IGCTrackPoint {
        if (record.length < 35) {
            throw InvalidIGCFormatException("B record must be at least 35 characters long, but it is $record")
        }

        val timestampString = record.substring(1, 7)
        val timestamp = try {
            Utils.parseTimestamp(timestampString)
        } catch (ex: IllegalArgumentException) {
            throw InvalidIGCFormatException("Failed to parse timestamp '$timestampString' in B record", ex)
        }
        val latitudeString = record.substring(7, 15)
        val latitude = try {
            Utils.parseLatitude(latitudeString)
        } catch (ex: IllegalArgumentException) {
            throw InvalidIGCFormatException("Failed to parse latitude '$latitudeString'", ex)
        }
        val longitudeString = record.substring(15, 24)
        val longitude = try {
            Utils.parseLongitude(longitudeString)
        } catch (ex: IllegalArgumentException) {
            throw InvalidIGCFormatException("Failed to parse longitude '$longitudeString'", ex)
        }

        val validityString = record.substring(24, 25)
        val validity = when (validityString) {
            "A" -> FixValidity.A
            "V" -> FixValidity.V
            else -> throw InvalidIGCFormatException("Fix validity shall be either A or V but is $validityString")

        }

        val pressureAltString = record.substring(25, 30)
        val pressureAlt = try {
            pressureAltString.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidIGCFormatException("Failed to parse pressure altitude string $pressureAltString", ex)
        }

        val gpsAltString = record.substring(30, 35)
        val gpsAlt = try {
            gpsAltString.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidIGCFormatException("Failed to parse pressure altitude string $gpsAltString", ex)
        }
        return IGCTrackPoint(
            timestampString,
            timestamp,
            latitude,
            longitude,
            validity,
            pressureAlt,
            gpsAlt
        )
    }

}