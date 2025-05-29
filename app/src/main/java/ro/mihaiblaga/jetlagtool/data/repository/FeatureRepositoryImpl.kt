package ro.mihaiblaga.jetlagtool.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.maplibre.geojson.Feature
import ro.mihaiblaga.jetlagtool.data.converters.GeometryConverter
import ro.mihaiblaga.jetlagtool.data.local.dao.FeatureDao
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository

class FeatureRepositoryImpl(
    private val dao: FeatureDao
) : FeatureRepository {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun getFeatures(): List<Feature>? {
        return dao.getAllFeatures().stream().map(this::mapEntityToDomain).toList()
    }

    override suspend fun getFeatureById(id: Long): Feature? {
        return dao.getFeatureById(id)?.let { mapEntityToDomain(it) }
    }

    private fun mapEntityToDomain(entity: FeatureEntity): Feature {
        val gson = Gson()
        val properties = gson.fromJson(entity.propertiesJson, JsonObject::class.java)
        val feature = Feature.fromGeometry(
            GeometryConverter().fromGeometryEntity(entity),
            properties,
            entity.id.toString()
        )
        return feature
    }
}