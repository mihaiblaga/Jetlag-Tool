package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity

@Dao
interface FeatureDao {
    @Insert
    fun insert(featureEntity: FeatureEntity): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_FEATURE} WHERE id = :id")
    fun getBorderGeometryById(id: Long): FeatureEntity?

}