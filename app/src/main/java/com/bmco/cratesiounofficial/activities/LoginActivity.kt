package com.bmco.cratesiounofficial.activities

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.models.User

import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private var apiToken: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        apiToken = findViewById(R.id.api_token)
        val confirmButton = findViewById<Button>(R.id.confirm_button)

        confirmButton.setOnClickListener { view ->
            try {
                Utility.saveData("token", apiToken!!.text.toString().trim { it <= ' ' })
                Networking.getMe(apiToken!!.text.toString().trim { it <= ' ' }, { user ->
                    apiToken!!.post { this.finish() }
                }, { })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
