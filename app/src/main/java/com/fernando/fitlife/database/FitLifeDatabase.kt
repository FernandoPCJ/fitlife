package com.fernando.fitlife.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fernando.fitlife.model.Treino

@Database(entities = [Treino::class], version = 2, exportSchema = false)
abstract class FitLifeDatabase : RoomDatabase() {

    abstract fun treinoDao(): TreinoDao

    companion object {
        @Volatile
        private var INSTANCE: FitLifeDatabase? = null

        fun getDatabase(context: Context): FitLifeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitLifeDatabase::class.java,
                    "fitlife_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}