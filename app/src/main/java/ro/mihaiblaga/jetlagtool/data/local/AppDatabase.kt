package ro.mihaiblaga.jetlagtool.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.maplibre.geojson.FeatureCollection
import ro.mihaiblaga.jetlagtool.data.local.dao.AdministrativeDivisionDao
import ro.mihaiblaga.jetlagtool.data.local.dao.FeatureDao
import ro.mihaiblaga.jetlagtool.data.local.entity.AdministrativeDivisionEntity
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity

@Database(
    entities = [
        AdministrativeDivisionEntity::class,
        FeatureEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun administrativeDivisionDao(): AdministrativeDivisionDao
    abstract fun featureDao(): FeatureDao

    private val databaseScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "Sample.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).loadData(context, "cluj-regions-2016.geojson")
                        }
                    }
                })
                .build()
    }

    private fun loadData(
        context: Context,
        fileName: String
    ) {
        lateinit var featureCollection: FeatureCollection

        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        featureCollection = FeatureCollection.fromJson(jsonString)

        for (feature in featureCollection.features()!!) {
            val geometry = FeatureEntity(
                type = feature.geometry()!!.type(),
                coordinatesJson = feature.geometry()!!.toJson(),
                propertiesJson = feature.properties().toString()
            )
            databaseScope.launch {
                val featureId = featureDao().insert(geometry)
                val administrativeDivisionEntity = AdministrativeDivisionEntity(
                    level = 5,
                    type = "test",
                    name = feature.properties()?.get("Name").toString(),
                    featureId = featureId,
                    parentId = null
                )
                Log.d(
                    "AppDatabase",
                    "Adding administrative division: $administrativeDivisionEntity"
                )
                administrativeDivisionDao().insert(administrativeDivisionEntity)
            }
        }
    }
}
