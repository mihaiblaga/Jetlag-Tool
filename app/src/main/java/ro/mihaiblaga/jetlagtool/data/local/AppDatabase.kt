package ro.mihaiblaga.jetlagtool.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.maplibre.geojson.FeatureCollection
import ro.mihaiblaga.jetlagtool.data.converters.CategoryConverter
import ro.mihaiblaga.jetlagtool.data.converters.GameSizeConverter
import ro.mihaiblaga.jetlagtool.data.local.dao.AdministrativeDivisionDao
import ro.mihaiblaga.jetlagtool.data.local.dao.FeatureDao
import ro.mihaiblaga.jetlagtool.data.local.dao.QuestionDao
import ro.mihaiblaga.jetlagtool.data.local.entity.AdministrativeDivisionEntity
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity
import ro.mihaiblaga.jetlagtool.data.local.entity.QuestionEntity
import ro.mihaiblaga.jetlagtool.domain.model.Category
import ro.mihaiblaga.jetlagtool.domain.model.GameSize
import ro.mihaiblaga.jetlagtool.presentation.questions.ALL_GAME_MODES_ORDERED

@Database(
    entities = [
        AdministrativeDivisionEntity::class,
        FeatureEntity::class,
        QuestionEntity::class
    ],
    version = 1
)
@TypeConverters(
    GameSizeConverter::class,
    CategoryConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun administrativeDivisionDao(): AdministrativeDivisionDao
    abstract fun featureDao(): FeatureDao
    abstract fun questionDao(): QuestionDao

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
                            getInstance(context).loadQuestions()
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

    private fun loadQuestions() {
        var insertedQuestions = 0
        ALL_GAME_MODES_ORDERED.forEach { questionType ->
            var questionCategory = when (questionType.title) {
                "Matching" -> Category.MATCHING
                "Measuring" -> Category.MEASURING
                "Thermometer" -> Category.THERMOMETER
                "Radar" -> Category.RADAR
                "Tentacles" -> Category.TENTACLES
                "Photos" -> Category.PHOTOGRAPHIC
                else -> Category.MATCHING
            }
            questionType.categories.forEach { category ->
                var gameSizes = when (category.title) {
                    "Medium Games" -> listOf(GameSize.MEDIUM)
                    "Large Games" -> listOf(GameSize.LARGE)
                    "Medium & Large Games" -> listOf(GameSize.MEDIUM, GameSize.LARGE)
                    else -> listOf(GameSize.SMALL, GameSize.MEDIUM, GameSize.LARGE)
                }
                category.fields.forEach { field ->
                    Log.d("AppDatabase", "Adding question: $field")
                    databaseScope.launch {
                        questionDao().insert(
                            QuestionEntity(
                                category = questionCategory,
                                type = category.title,
                                gameSizes = gameSizes,
                                cost = questionType.cost,
                                time = questionType.time,
                                distance = category.distance,
                                text = field.label,
                                description = field.description
                            )
                        )
                        insertedQuestions++
                        Log.d("AppDatabase", "Inserted $insertedQuestions questions")
                    }
                }
            }
        }

    }
}