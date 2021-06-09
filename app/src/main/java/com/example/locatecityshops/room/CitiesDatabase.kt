package com.example.locatecityshops.room

import android.content.Context
import androidx.room.*

@Database(entities = [CitiesEntity::class], version = 1, exportSchema = false)
@TypeConverters(CityListConverter::class)
abstract class CitiesDatabase: RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

    companion object{
        @Volatile
        private var INSTANCE: CitiesDatabase? = null

        fun getDatabase(context: Context): CitiesDatabase {
            val store = INSTANCE
            if (store != null) {
                return store
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitiesDatabase::class.java,
                    "cities_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}