package com.example.yourmealplanner.model.recipe

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yourmealplanner.model.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Recipe::class, User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDAO(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(
            context: Context,
            coroutineScope: CoroutineScope
        ): RecipeDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database")
                    .addCallback(
                        RecipeDatabaseCallback(
                            coroutineScope
                        )
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


private class RecipeDatabaseCallback (
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    @Volatile
    private var INSTANCE: RecipeDatabase? = null

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
            scope.launch {
                val recipeDao = database.getRecipeDAO()
            }
        }
    }
}

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}