package com.hack.antisurveillance.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = AntiSurveillanceDatabase.DATABASE_VERSION)
abstract class AntiSurveillanceDatabase : RoomDatabase() {

    abstract val locationDao: LocationDao

    companion object {
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "anti_surveillance_database"

        fun buildDatabase(context: Context): AntiSurveillanceDatabase {
            return Room.databaseBuilder(
                context,
                AntiSurveillanceDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}