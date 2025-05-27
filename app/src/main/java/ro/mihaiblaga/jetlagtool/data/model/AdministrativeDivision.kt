package ro.mihaiblaga.jetlagtool.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "administrative_borders",
    foreignKeys = [ForeignKey(
        entity = BorderGeometry::class,
        parentColumns = ["id"],
        childColumns = ["geometryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdministrativeDivision(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val level: Int,
    val type: String,
    val name: String,

    val geometryId: Long
)
