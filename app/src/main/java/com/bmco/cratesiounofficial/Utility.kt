package com.bmco.cratesiounofficial

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.loopj.android.http.AsyncHttpResponseHandler
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

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

    private var settings: SharedPreferences? = null

    fun getSSL(url: String, responseHandler: AsyncHttpResponseHandler) {
        try {
            val page = URL(url) // Process the URL far enough to find the right handler
            val urlConnection = page.openConnection() as HttpURLConnection
            val token = loadData<String>("token", String::class.java)
            if (token != null) {
                urlConnection.setRequestProperty("Authorization", token)
            }
            urlConnection.useCaches = false // Don't look at possibly cached data
            // Read it all and print it out
            val stream = urlConnection.inputStream
            val bytes = IOUtils.toByteArray(stream)
            val code = urlConnection.responseCode
            if (code in 200..399) {
                responseHandler.sendSuccessMessage(code, null, bytes)
            } else {
                responseHandler.sendFailureMessage(code, null, bytes, IOException())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            responseHandler.sendFailureMessage(0, null, ByteArray(1), e)
        }

    }

    fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }

    fun initSaveLoad(context: Context) {
        settings = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    fun <T>saveData(key: String, value: T) {
        val data = Gson().toJson(value)
        settings!!.edit().putString(key, data).apply()
    }

    @Throws(JsonSyntaxException::class)
    fun <T> loadData(key: String, type: Type): T? {
        return Gson().fromJson<Any>(settings!!.getString(key, ""), type) as? T
    }
}
