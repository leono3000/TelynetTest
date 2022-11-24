package com.argel.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ClientsEntity::class], version = 1, exportSchema = false)
abstract class TelynetDatabase : RoomDatabase() {

    abstract fun clientsDao(): ClientsDao

    companion object {
        private var INSTANCE: TelynetDatabase? = null

        fun getInstance(context: Context): TelynetDatabase? {
            if (INSTANCE == null) {
                synchronized(TelynetDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TelynetDatabase::class.java, "telynet.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
