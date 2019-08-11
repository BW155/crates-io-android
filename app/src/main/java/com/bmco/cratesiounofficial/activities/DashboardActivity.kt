package com.bmco.cratesiounofficial.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.widget.TextView

import com.bmco.cratesiounofficial.FontFitTextView
import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.models.Crate
import com.bmco.cratesiounofficial.recyclers.CrateRecyclerAdapter
import com.loopj.android.http.AsyncHttpResponseHandler

import java.text.NumberFormat

import cz.msebera.android.httpclient.Header
import de.hdodenhof.circleimageview.CircleImageView

class DashboardActivity : AppCompatActivity() {

    private var profileUsername: TextView? = null
    private var crateCount: FontFitTextView? = null
    private var crateDownloads: FontFitTextView? = null
    private var profileImage: CircleImageView? = null
    private var myCrates: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        profileImage = findViewById(R.id.profile_image)
        profileUsername = findViewById(R.id.profile_username)
        crateCount = findViewById(R.id.crate_count)
        crateDownloads = findViewById(R.id.crate_downloads)

        myCrates = findViewById(R.id.my_crates)

        if (Utility.loadData<Any>("token", String::class.java) != null) {
            profileUsername!!.text = MainActivity.currentUser.login
            val thread = object : Thread() {
                override fun run() {
                    val thread = object : Thread() {
                        override fun run() {
                            val crates = Networking.getCratesByUserId(MainActivity.currentUser.id!!)
                            myCrates!!.post {
                                myCrates!!.layoutManager = LinearLayoutManager(this@DashboardActivity)
                                myCrates!!.adapter = CrateRecyclerAdapter(this@DashboardActivity, crates)
                                crateCount!!.text = NumberFormat.getNumberInstance().format(crates!!.size.toLong())
                                var downloads = 0
                                for (c in crates) {
                                    downloads += c.downloads
                                }
                                crateDownloads!!.text = NumberFormat.getNumberInstance().format(downloads.toLong())
                            }
                        }
                    }
                    thread.start()
                    if (MainActivity.avatar == null) {
                        Utility.getSSL(MainActivity.currentUser.avatar!!, object : AsyncHttpResponseHandler() {
                            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                                val bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.size)
                                profileImage!!.post { profileImage!!.setImageBitmap(bitmap) }
                            }

                            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {

                            }
                        })
                    } else {
                        profileImage!!.post { profileImage!!.setImageBitmap(MainActivity.avatar) }
                    }
                }
            }
            thread.start()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
