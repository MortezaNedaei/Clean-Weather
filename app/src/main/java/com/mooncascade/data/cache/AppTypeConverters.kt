package com.mooncascade.data.cache

import android.util.Log
import androidx.room.TypeConverter
import com.mooncascade.common.extensions.TAG
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject
import java.util.*


object AppTypeConverters {

    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun longToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun stringToJsonObject(string: String?): JSONObject? {
        if (string == null) {
            return null
        }
        return try {
            JSONObject(string)
        } catch (e: JSONException) {
            Log.e(TAG, "Could not convert string to JSONObject", e)
            JSONObject()
        }
    }

    @TypeConverter
    fun jsonObjectToString(jsonObject: JSONObject?): String? {
        return jsonObject?.toString()
    }

    @TypeConverter
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map { it2 ->
                try {
                    it2.toInt()
                } catch (ex: NumberFormatException) {
                    Log.e(ex.message, "Cannot convert $it2 to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }

    @TypeConverter
    fun stringToMap(value: String): Map<String, String>? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun mapToString(value: Map<String, String>?): String? {
        return if (value == null) null else Json.encodeToString(value)
    }

}
