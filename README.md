# IGC4J

A simple JVM-based parser for IGC file format.

IGC is widely used by GPS tracking devices and apps used in paragliding, hanggliding and other soaring flight sports.

Format description can be found [here](https://xp-soaring.github.io/igc_file_format/igc_format_2008.html)

This library supports only a simple subset of IGC records:
* Flight timestamp
* Pilot name
* Glider id
* Device manufacturer info
* List of track points

If you need other IGC records to be parsed - let me know via issues section!

## Usage

### Configuring Dependency

Jar file with igc4j is uploaded to GitHub package registry.

Add this to your `build.gradle` file:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/smirnovegorv/igc4j")
    }
}
```

After that, you will be able to add igc4j to your dependency list

```groovy
implementation("ru.paragliding:igc4j:1.0.0")
```

More instructions on using GitHub package repository, including Kotlin script examples, are available
at [GitHub documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)

### Parsing IGC

Parsing IGC file is pretty straightforward:

```java
File igcFile = ...;
IGCTrack track = IGCParser.parse(igcFile);

// We can see some track metadata, since all fields are optional be sure to check for NULLs
String pilotName = track.metadata.pilotName;

// We can iterate over track points
for (IGCTrackPoint point: track.points) {
    System.out.println("Point coordinates: " + point.lat + ", " + point.lon + ", " + point.gpsAltitude);    
}
```

Each track may contain some metadata values (pilot name, gps tracker device info), all of them are optional
and may be included or not depending on tracker device firmware. And it contains a list of points.

Each point has following properties:

* `timestampString` - HHmmss format as provided in IGC file
* `timestamp` - same as long milliseconds count since start of the day
* `lat, lon` - 2d coordinates
* `validity`. Special flag, `A` means this is a valid 3d point, `V` means 2d only (no GPS altitude)
* `barometricAltitude` - height in meters above 1013.25 HPa sea level datum. May be set to zero if track was recorded on a device without barometric altimeter
* `gpsAltitude` - altitude in meters above WGS84 ellipsoid, present only if `validity` is set to `A` 

## License

Project is licensed under Apache 2.0 license. See [license file](/LICENSE)
