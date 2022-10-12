package com.example.news.data.db.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.news.data.db.entities.News
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class AppTypeConverters(private val jsonParser: JsonParser) {

    /*@TypeConverter
    fun fromJsonElement(jsonElement: JsonElement): String = jsonElement.toString()

    @TypeConverter
    fun stringToJsonElement(string: String): JsonElement = string.toJsonElement()*/

    @TypeConverter
    fun toMeaningJson(meaning: List<News>): String {
        return jsonParser.toJson(meaning, object : TypeToken<ArrayList<News>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<News> {
        return jsonParser.fromJson<ArrayList<News>>(
            json,
            object : TypeToken<ArrayList<News>>() {}.type
        ) ?: emptyList()
    }

    /*@TypeConverter
    fun stringToSource(string: String?): LoginResult = Gson().fromJson(string, LoginResult::class.java)

    @TypeConverter
    fun sourceToString(list: LoginResult?): String? = Gson().toJson(list)*/

}
