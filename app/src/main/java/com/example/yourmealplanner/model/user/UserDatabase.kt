package com.example.yourmealplanner.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserLoginDatabase : RoomDatabase() {

    abstract fun getUserLoginDAO(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserLoginDatabase? = null

        fun getDatabase(
            context: Context,
            coroutineScope: CoroutineScope
        ): UserLoginDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserLoginDatabase::class.java,
                    "user_login_database")
                    .addCallback(
                        UserLoginDatabaseCallback(
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


private class UserLoginDatabaseCallback (
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    @Volatile
    private var INSTANCE: UserLoginDatabase? = null

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
            scope.launch {
                val userLoginDao = database.getUserLoginDAO()
            }
        }
    }
}
