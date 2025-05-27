package ro.mihaiblaga.jetlagtool.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "border_geometry")
data class BorderGeometry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val geometryType: String,
    val coordinatesJson: String
)