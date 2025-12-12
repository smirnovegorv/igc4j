package ru.paragliding.igc4j.model

data class ManufacturerInfo(
    /**
     * Mandatory 3-symbol manufacturer code
     */
    val manufacturerCode: String,

    /**
     * Mandatory 3-symbol device code
     */
    val deviceCode: String,

    /**
     * Arbitrary optional string with device info (e.g. model number)
     */
    val extension: String?
)

data class IGCMetadata(
    /**
     * Information about tracking device manufacturer from A record
     */
    var manufacturerInfo: ManufacturerInfo? = null,

    /**
     * Date of flight in ddMMYY format
     */
    var date: String? = null,

    /**
     * Arbitrary string from HFPLTPILOTINCHARGE record
     */
    var pilotName: String? = null,

    /**
     * Arbitrary aircraft registration code string from HFGIDGLIDERID record
     */
    var gliderNumber: String? = null,

    /**
     * Arbitrary glider name string from HFGTYGLIDERTYPE record
     */
    var gliderType: String? = null,

    /**
     * Any free-text description of the class of this glider, e.g. Standard, 15m, Open
     */
    var competitionClass: String? = null,

    /**
     * Global GPS accuracy of the device, in meters, from HFFXA record
     */
    var fixAccuracy: String? = null
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
    /**
     * Timestamp in HHmmss format
     */
    val timestampString: String,

    /**
     * timestampString converted to millis (number of millis from the start of the day)
     */
    val timestamp: Long,

    /**
     * Point latitude
     */
    val lat: Double,

    /**
     * Point longitude
     */
    val lon: Double,

    /**
     * Shows if this point has valid GPS coordinates
     */
    val validity: FixValidity,

    /**
     * If barometric altimeter is present - this will contain altitude in meters relative to ICAO ISA 1013.25 HPa sea level datum
     * If no altimeter is present, will be zero
     */
    val barometricAltitude: Int,

    /**
     * Altitude in meters relative to WGS84 ellipsoid based on GPS data, only valid if validity flag is set to A
     */
    val gpsAltitude: Int
)

data class IGCTrack (
    val metadata: IGCMetadata?,
    val points: List<IGCTrackPoint>
)