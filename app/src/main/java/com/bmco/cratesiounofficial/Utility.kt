package com.bmco.cratesiounofficial

import android.content.Context
import android.content.SharedPreferences
import com.bmco.cratesiounofficial.models.Crate
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

/**
 * Created by Bertus on 25-5-2017.
 */

object Utility {
    private const val BASE_URL = "https://www.crates.io/"
    var SUMMARY = "api/v1/summary"
    var SEARCH = "api/v1/crates?page=%d&per_page=100&q=%s&sort=&_=%d" /// where 1: page number, 2: query, 3: sort type
    var DEPENDENCIES = "api/v1/crates/%s/%s/dependencies" /// where 1: crate id
    var CRATE = "api/v1/crates/%s" /// where 1: crate id
    var README = "api/v1/crates/%s/%s/readme" /// where 1: crate id, 2: version
    var ME = "api/v1/me"
    var CRATES_BY_USER_ID = "api/v1/crates?user_id=%d" /// where 1: user id;

    private lateinit var settings: SharedPreferences

    fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }

    fun initSaveLoad(context: Context) {
        settings = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    fun <T>saveData(key: String, value: T) {
        val data = Gson().toJson(value)
        settings.edit().putString(key, data).apply()
    }

    @Throws(JsonSyntaxException::class)
    fun <T> loadData(key: String, type: Type): T? {
        return Gson().fromJson(settings.getString(key, ""), type) as? T
    }

    fun filterCrates(crates: List<Crate>): List<Crate> {
        return crates.filter { crate -> !crate.id.isNullOrEmpty() && !crate.maxVersion.isNullOrEmpty() }
    }
}
