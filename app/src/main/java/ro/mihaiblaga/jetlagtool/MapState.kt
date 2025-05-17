package ro.mihaiblaga.jetlagtool

data class MapState(
    val latitude: Double = 15.0,
    val longitude: Double = 15.0,
    val zoomLevel: Double = 15.0,
    val location: String = ""
)