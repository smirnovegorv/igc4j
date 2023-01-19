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
)

data class TrackPoint(
    val timestampString: String,
    val timestamp: Long,
    val lat: Double,
    val lon: Double,

    val barometricAltitude: Double,
    val gpsAltitude: Double
)

data class IGCTrack (
    val metadata: IGCMetadata?,
    val points: List<TrackPoint>
)