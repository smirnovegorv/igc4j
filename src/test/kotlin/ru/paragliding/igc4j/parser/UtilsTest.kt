package ru.paragliding.igc4j.parser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun testParseTime() {
        Assertions.assertEquals(0, Utils.parseTimestamp("000000"))
        Assertions.assertEquals(4540000, Utils.parseTimestamp("011540"))
    }

    @Test
    fun testInvalidParseTime() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {Utils.parseTimestamp("12345")}
        Assertions.assertThrows(IllegalArgumentException::class.java) {Utils.parseTimestamp("avcx")}
    }

    @Test
    fun testParseLatitude() {
        Assertions.assertEquals(54.1186, Utils.parseLatitude("5407121N"), 0.001)
        Assertions.assertEquals(-12.057, Utils.parseLatitude("1203456S"), 0.001)

        Assertions.assertThrows(IllegalArgumentException::class.java) {Utils.parseLatitude("000000Z")}
    }

}