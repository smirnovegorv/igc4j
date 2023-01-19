package ru.paragliding.igc4j.model

data class ManufacturerInfo(
    val manufacturerCode: String,
    val deviceCode: String,
    val extension: String?
)

data class IGCMetadata(
    var manufacturerInfo: ManufacturerInfo?,
    var date: String?,
    var pilotName: String?,
    var gliderNumber: String?,
    var fixAccuracy: String?
)

enum class FixValidity {
    /**
     * Valid 3d fix with GPS altitude available
     */
    A,

    /**
     * 2d fix: no GPS altitude
     */
    V
}

data class IGCTrackPoint(
    val timestampString: String,
    val timestamp: Long,
    val lat: Double,
    val lon: Double,

    val validity: FixValidity,
    val barometricAltitude: Int,
    val gpsAltitude: Int
)

data class IGCTrack (
    val metadata: IGCMetadata?,
    val points: List<IGCTrackPoint>
)