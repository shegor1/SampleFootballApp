package com.shegor.samplefootballapp.persistantStorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shegor.samplefootballapp.models.League


@Database(entities = [League::class], version = 3, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {

    abstract val footballDao: FootballDao

    companion object {

        @Volatile
        private var INSTANCE: FootballDatabase? = null

        fun getInstance(context: Context): FootballDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FootballDatabase::class.java,
                    "football_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}