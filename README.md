# IGC4J

A simple JVM-based parser for IGC file format.

IGC is widely used by GPS tracking devices and apps used in paragliding, hanggliding and other soaring flight sports.

Format description can be found [here](https://xp-soaring.github.io/igc_file_format/igc_format_2008.html)

This library supports only a simpel subset of IGC records:
* Flight timestamp
* Pilot name
* Glider id
* Device manufacturer info
* List of track points

If you need other IGC records to be parsed - let me know via issues section!
