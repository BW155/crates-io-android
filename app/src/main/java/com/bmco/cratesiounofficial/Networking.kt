package com.bmco.cratesiounofficial

import com.bmco.cratesiounofficial.Utility.ME
import com.bmco.cratesiounofficial.models.Crate
import com.bmco.cratesiounofficial.models.Dependency
import com.bmco.cratesiounofficial.models.User
import com.bmco.cratesiounofficial.models.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by Bertus on 25-5-2017.
 */

object Networking {

    private val cachedReadmes = HashMap<String, String>()
    private val cachedDependencies = HashMap<String, List<Dependency>>()

    @Throws(IOException::class)
    fun getMe(token: String,
              success_result: (user: User) -> Unit,
              error_result: (error: String) -> Unit) {

        Fuel.get(Utility.getAbsoluteUrl(ME)).response { request, response, result ->
            val (bytes, error) = result
            if (bytes != null) {
                try {
                    val jResult = JSONObject(String(bytes))
                    val objectMapper = ObjectMapper()
                    success_result.invoke(objectMapper.readValue(jResult.getJSONObject("user").toString(), User::class.java))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            if (error != null) {
                error_result.invoke(error.toString())
            }
        }
    }

    fun searchCrate(query: String,
                    page: Int,
                    success_result: (crates: List<Crate>) -> Unit,
                    error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.SEARCH, page, query, Date().time))

        Fuel.get(url).response { _, _, result ->
            val (bytes, error) = result
            if (bytes != null) {
                try {
                    val jResult = JSONObject(String(bytes))
                    val crates = jResult.getJSONArray("crates")

                    val mapper = ObjectMapper()
                    val crateList = ArrayList<Crate>()

                    for (i in 0 until crates.length()) {
                        val jsonCrate = crates.getJSONObject(i).toString()
                        val crate = mapper.readValue(jsonCrate, Crate::class.java)
                        crate.getDependencies(null)
                        crateList.add(crate)
                    }
                    success_result.invoke(crateList)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (error != null) {
                error_result.invoke(error.toString())
            }
        }
    }

    fun getDependenciesForCrate(id: String,
                                version: String,
                                success_result: (dependencies: List<Dependency>) -> Unit,
                                error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.DEPENDENCIES, id, version))
        if (cachedDependencies[id + version] != null) {
            success_result.invoke(cachedDependencies[id + version]!!)
        }

        Fuel.get(url).response { request, response, result ->
            val (bytes, error) = result
            if (bytes != null) {
                try {
                    val jResult = JSONObject(String(bytes))
                    val dependencies = jResult.getJSONArray("dependencies")

                    val mapper = ObjectMapper()
                    val dependencyList = ArrayList<Dependency>()

                    for (i in 0 until dependencies.length()) {
                        val jsonDependency = dependencies.getJSONObject(i).toString()
                        val dependency = mapper.readValue(jsonDependency, Dependency::class.java)
                        dependencyList.add(dependency)
                    }
                    cachedDependencies[id + version] = dependencyList
                    success_result.invoke(dependencyList)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (error != null) {
                error_result.invoke(error.toString())
            }
        }
    }

    fun getCrateById(id: String,
                     success_result: (crate: Crate) -> Unit,
                     error_result: (error: String) -> Unit) {
        val url = String.format(Locale.US, Utility.CRATE, id)

        Fuel.get(Utility.getAbsoluteUrl(url)).response { request, response, result ->
            val (bytes, error) = result
            if (bytes != null) {
                try {
                    val jResult = JSONObject(String(bytes))
                    val jsCrate = jResult.getJSONObject("crate")
                    val jsVersions = jResult.getJSONArray("versions")

                    val mapper = ObjectMapper()

                    val crate = mapper.readValue(jsCrate.toString(), Crate::class.java)
                    val versions = ArrayList<Version>()
                    for (i in 0 until jsVersions.length()) {
                        val version = mapper.readValue(jsVersions.getJSONObject(i).toString(), Version::class.java)
                        versions.add(version)
                    }
                    getReadme(id, versions[0].num!!, {readme ->
                        println("README: $readme")
                        versions[0].readme = readme
                        crate.versionList = versions
                        success_result.invoke(crate)
                    }, {error ->
                        error_result.invoke(error)
                    })
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (error != null) {
                error_result.invoke(error.toString())
            }
        }
    }

    private fun getReadme(id: String,
                          version: String,
                          success_result: (readme: String) -> Unit,
                          error_result: (error: String) -> Unit) {
        if (cachedReadmes[id + version] != null) {
            success_result.invoke(cachedReadmes[id + version]!!)
        }
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.README, id, version))

        Fuel.get(url).response  { _, _, result ->
            val (bytes, error) = result
            if (bytes != null) {
                cachedReadmes[id + version] = String(bytes)
                success_result.invoke(String(bytes))
            }
            if (error != null) {
                error_result.invoke(error.toString())
            }
        }
    }

    fun getCratesByUserId(userId: Int): List<Crate>? {
        val url = String.format(Locale.US, Utility.CRATES_BY_USER_ID, userId)

        val result = arrayOfNulls<String>(1)
        Utility.getSSL(Utility.getAbsoluteUrl(url), object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                if (responseBody.isNotEmpty()) {
                    result[0] = String(responseBody)
                } else {
                    result[0] = "ERROR"
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                if (responseBody.isNotEmpty()) {
                    result[0] = String(responseBody)
                } else {
                    result[0] = "ERROR"
                }
            }
        })

        while (result[0] == null) {
            //ignore
        }

        try {
            val jResult = JSONObject(result[0])
            val jsCrates = jResult.getJSONArray("crates")

            val mapper = ObjectMapper()

            val crates = ArrayList<Crate>()

            for (i in 0 until jsCrates.length()) {
                crates.add(mapper.readValue(jsCrates.getJSONObject(i).toString(), Crate::class.java))
            }

            return crates
        } catch (e: JSONException) {
            return null
        } catch (e: IOException) {
            return null
        }

    }
}
