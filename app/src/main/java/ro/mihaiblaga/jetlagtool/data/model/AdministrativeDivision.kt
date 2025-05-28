package ro.mihaiblaga.jetlagtool.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema

@Entity(
    tableName = DatabaseSchema.TABLE_ADMINISTRATIVE_DIVISION,
    foreignKeys = [ForeignKey(
        entity = BorderGeometry::class,
        parentColumns = ["id"],
        childColumns = ["geometryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdministrativeDivision(
    @ColumnInfo(name = COLUMN_LEVEL)
    val level: Int,

    @ColumnInfo(name = COLUMN_TYPE)
    val type: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_GEOMETRY_ID)
    val geometryId: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0
) {
    companion object {
        const val COLUMN_LEVEL = "level"
        const val COLUMN_TYPE = "type"
        const val COLUMN_NAME = "name"
        const val COLUMN_GEOMETRY_ID = "geometryId"
        const val COLUMN_ID = "id"
    }
}
