package ru.paragliding.igc4j.parser

class InvalidIGCFormatException(message: String, reason: Throwable?): Exception(message, reason) {
    constructor(message: String): this(message, null)
}