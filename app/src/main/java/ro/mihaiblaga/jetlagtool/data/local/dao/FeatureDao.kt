package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity

@Dao
interface FeatureDao {
    @Insert
    suspend fun insert(featureEntity: FeatureEntity): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_FEATURE} WHERE id = :id")
    suspend fun getFeatureById(id: Long): FeatureEntity?

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_FEATURE}")
    suspend fun getAllFeatures(): List<FeatureEntity>

}