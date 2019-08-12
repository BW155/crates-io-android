package com.bmco.cratesiounofficial.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmco.cratesiounofficial.FontFitTextView
import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.recyclers.CrateRecyclerAdapter
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat

class DashboardActivity : AppCompatActivity() {

    private var profileUsername: TextView? = null
    private var crateCount: FontFitTextView? = null
    private var crateDownloads: FontFitTextView? = null
    private var profileImage: CircleImageView? = null
    private var myCrates: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        title = "Dashboard"

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        profileImage = findViewById(R.id.profile_image)
        profileUsername = findViewById(R.id.profile_username)
        crateCount = findViewById(R.id.crate_count)
        crateDownloads = findViewById(R.id.crate_downloads)

        myCrates = findViewById(R.id.my_crates)

        if (Utility.loadData<String?>("token", String::class.java) != null) {
            profileUsername!!.text = MainActivity.currentUser.login
            Networking.getCratesByUserId(MainActivity.currentUser.id!!, {crates ->
                myCrates!!.post {
                    myCrates!!.layoutManager = LinearLayoutManager(this@DashboardActivity)
                    myCrates!!.adapter = CrateRecyclerAdapter(this@DashboardActivity, crates)
                    crateCount!!.text = NumberFormat.getNumberInstance().format(crates.size.toLong())
                    var downloads = 0
                    for (c in crates) {
                        downloads += c.downloads
                    }
                    crateDownloads!!.text = NumberFormat.getNumberInstance().format(downloads.toLong())
                }
            }, { })
            profileImage!!.setImageBitmap(MainActivity.avatar)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
