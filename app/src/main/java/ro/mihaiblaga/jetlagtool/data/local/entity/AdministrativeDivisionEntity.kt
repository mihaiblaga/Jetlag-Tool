package ro.mihaiblaga.jetlagtool.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema

@Entity(
    tableName = DatabaseSchema.TABLE_ADMINISTRATIVE_DIVISION,
    foreignKeys = [ForeignKey(
        entity = FeatureEntity::class,
        parentColumns = ["id"],
        childColumns = ["featureId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdministrativeDivisionEntity(
    @ColumnInfo(name = COLUMN_LEVEL)
    val level: Int,

    @ColumnInfo(name = COLUMN_PARENT_ID)
    val parentId: Long?,

    @ColumnInfo(name = COLUMN_TYPE)
    val type: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_FEATURE_ID)
    val featureId: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0
) {
    companion object {
        const val COLUMN_LEVEL = "level"
        const val COLUMN_TYPE = "type"
        const val COLUMN_NAME = "name"
        const val COLUMN_FEATURE_ID = "featureId"
        const val COLUMN_ID = "id"
        const val COLUMN_PARENT_ID = "parentId"
    }
}
