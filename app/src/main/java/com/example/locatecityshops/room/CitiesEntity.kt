package com.example.locatecityshops.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.locatecityshops.model.City
import com.google.gson.Gson
@Entity(tableName = "cities_table")
data class CitiesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "cities")
    val cities: List<City>
)

class CityListConverter {

    @TypeConverter
    fun fromString(value: String): List<City> = Gson().fromJson(value, Array<City>::class.java).toList()

    @TypeConverter
    fun fromCityList(cityList: List<City>?): String? = Gson().toJson(cityList)
}