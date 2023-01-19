package ru.paragliding.igc4j.parser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class IGCParserTest {

    @Test
    fun testRead2Points() {
        val track = IGCParser.parse(javaClass.classLoader.getResourceAsStream("2points.igc"))

        Assertions.assertNotNull(track.metadata)

        Assertions.assertEquals("Somnuek", track.metadata!!.pilotName)
        Assertions.assertEquals("180123", track.metadata!!.date)
        Assertions.assertNotNull(track.metadata!!.manufacturerInfo)
        Assertions.assertEquals(track.metadata!!.manufacturerInfo!!.manufacturerCode, "XSX")
        Assertions.assertEquals(track.metadata!!.manufacturerInfo!!.deviceCode, "004")
        Assertions.assertEquals(track.metadata!!.manufacturerInfo!!.extension, "SKYTRAXX")

        Assertions.assertEquals(2, track.points.size)
        Assertions.assertEquals("053726", track.points[0].timestampString)
        Assertions.assertEquals(1082246000, track.points[0].timestamp)
        Assertions.assertEquals(Utils.degreesToDecimal(16, 56.53), track.points[0].lat)
        Assertions.assertEquals(Utils.degreesToDecimal(101, 7.754), track.points[0].lon)

        Assertions.assertEquals("053810", track.points[1].timestampString)
    }
}