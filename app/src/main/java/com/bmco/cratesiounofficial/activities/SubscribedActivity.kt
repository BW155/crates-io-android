package com.bmco.cratesiounofficial.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.models.Alert
import com.bmco.cratesiounofficial.recyclers.SubscribedAdapter
import com.google.gson.reflect.TypeToken
import java.util.*

class SubscribedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribed)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "Subscribed"

        val recyclerView = findViewById<RecyclerView>(R.id.subscribed_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val listType = object : TypeToken<ArrayList<Alert>>() {

        }.type
        val alerts = Utility.loadData<MutableList<Alert>>("alerts", listType)

        val emptyView = findViewById<TextView>(R.id.empty_view)
        if (alerts == null || alerts.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.adapter = SubscribedAdapter(this, alerts)
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
