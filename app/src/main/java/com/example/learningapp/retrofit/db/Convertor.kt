package com.example.learningapp.retrofit.db

import androidx.room.TypeConverter
import com.example.learningapp.retrofit.models.Dob
import com.example.learningapp.retrofit.models.Location
import com.example.learningapp.retrofit.models.Name
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

class Convertor {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromStringToDate(time: Long?): Date? {
        return time?.let { Date(it) }
    }

    @TypeConverter
    fun fromDobToString(dob: Dob?): String? {
        return dob?.let { moshi.adapter(Dob::class.java).toJson(it) }
    }

    @TypeConverter
    fun toDobFromString(dobString: String?): Dob? {
        return dobString?.let { moshi.adapter(Dob::class.java).fromJson(it) }
    }

    @TypeConverter
    fun fromLocationToString(location: Location?): String? {
        return location?.let { moshi.adapter(Location::class.java).toJson(it) }
    }

    @TypeConverter
    fun toLocationFromString(locationString: String?): Location? {
        return locationString?.let { moshi.adapter(Location::class.java).fromJson(it) }
    }

    @TypeConverter
    fun fromNameToString(name: Name?): String? {
        return name?.let { moshi.adapter(Name::class.java).toJson(it) }
    }

    @TypeConverter
    fun toNameFromString(nameString: String?): Name? {
        return nameString?.let { moshi.adapter(Name::class.java).fromJson(it) }
    }
}
