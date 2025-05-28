package ro.mihaiblaga.jetlagtool.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema

@Entity(tableName = DatabaseSchema.TABLE_BORDER_GEOMETRY)
data class BorderGeometry(
    @ColumnInfo(name = COLUMN_GEOMETRY_TYPE)
    val geometryType: String,

    @ColumnInfo(name = COLUMN_COORDINATES_JSON)
    val coordinatesJson: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0
) {
    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_GEOMETRY_TYPE = "geometryType"
        const val COLUMN_COORDINATES_JSON = "coordinatesJson"
    }
}