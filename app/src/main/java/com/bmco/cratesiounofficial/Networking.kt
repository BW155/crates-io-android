package com.bmco.cratesiounofficial

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bmco.cratesiounofficial.Utility.ME
import com.bmco.cratesiounofficial.models.Crate
import com.bmco.cratesiounofficial.models.Dependency
import com.bmco.cratesiounofficial.models.User
import com.bmco.cratesiounofficial.models.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
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

        Fuel.get(Utility.getAbsoluteUrl(ME)).header("Authorization", token).response { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                try {
                    val jResult = JSONObject(String(it))
                    val objectMapper = ObjectMapper()
                    success_result.invoke(objectMapper.readValue(jResult.getJSONObject("user").toString(), User::class.java))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            error?.let {
                error_result.invoke("$it")
            }
        }
    }

    fun downloadImage(url: String,
                      success_result: (bitmap: Bitmap) -> Unit,
                      error_result: (error: String) -> Unit) {
        Fuel.get(url).response { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                success_result.invoke(bitmap)
            }
            error?.let {
                error_result.invoke("$it")
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
            bytes?.let {
                try {
                    val jResult = JSONObject(String(it))
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
            error?.let {
                error_result.invoke("$it")
            }
        }
    }

    fun getDependenciesForCrate(id: String,
                                version: String,
                                success_result: (dependencies: List<Dependency>) -> Unit,
                                error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.DEPENDENCIES, id, version))
        cachedDependencies[id + version]?.let {
            success_result.invoke(it)
        }

        Fuel.get(url).response { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                try {
                    val jResult = JSONObject(String(it))
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
            error?.let {
                error_result.invoke("$it")
            }
        }
    }

    fun getCrateById(id: String,
                     success_result: (crate: Crate) -> Unit,
                     error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.CRATE, id))

        Fuel.get(url).response { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                try {
                    val jResult = JSONObject(String(it))
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
                        versions[0].readme = readme
                        crate.versionList = versions
                        success_result.invoke(crate)
                    }, {err ->
                        error_result.invoke(err)
                        crate.versionList = versions
                        success_result.invoke(crate)
                    })
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            error?.let {
                error_result.invoke("$it")
            }
        }
    }

    private fun getReadme(id: String,
                          version: String,
                          success_result: (readme: String) -> Unit,
                          error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.README, id, version))
        cachedReadmes[id + version]?.let{
            success_result.invoke(it)
        }

        Fuel.get(url).response  { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                val str = String(it)
                cachedReadmes[id + version] = str
                success_result.invoke(str)
            }
            error?.let {
                error_result.invoke("$it")
            }
        }
    }

    fun getCratesByUserId(userId: Int,
                          success_result: (crates: List<Crate>) -> Unit,
                          error_result: (error: String) -> Unit) {
        val url = Utility.getAbsoluteUrl(String.format(Locale.US, Utility.CRATES_BY_USER_ID, userId))

        Fuel.get(url).response  { _, _, result ->
            val (bytes, error) = result
            bytes?.let {
                try {
                    val jResult = JSONObject(String(it))
                    val jsCrates = jResult.getJSONArray("crates")

                    val mapper = ObjectMapper()

                    val crates = ArrayList<Crate>()

                    for (i in 0 until jsCrates.length()) {
                        crates.add(mapper.readValue(jsCrates.getJSONObject(i).toString(), Crate::class.java))
                    }

                    success_result.invoke(crates)
                } catch (e: JSONException) {
                    error_result.invoke("$e")
                } catch (e: IOException) {
                    error_result.invoke("$e")
                }
            }
            error?.let {
                error_result.invoke("$it")
            }
        }
    }
}
