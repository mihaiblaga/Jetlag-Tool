package ro.mihaiblaga.jetlagtool.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema

@Entity(tableName = DatabaseSchema.TABLE_FEATURE)
data class FeatureEntity(
    @ColumnInfo(name = COLUMN_TYPE)
    val type: String = "Feature",

    @ColumnInfo(name = COLUMN_COORDINATES_JSON)
    val coordinatesJson: String?,

    @ColumnInfo(name = COLUMN_PROPERTIES_JSON)
    val propertiesJson: String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0
) {
    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COORDINATES_JSON = "coordinatesJson"
        const val COLUMN_PROPERTIES_JSON = "propertiesJson"
    }
}