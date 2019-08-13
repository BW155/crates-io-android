package com.bmco.cratesiounofficial.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private var apiToken: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "Login"

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        apiToken = findViewById(R.id.api_token)
        val confirmButton = findViewById<Button>(R.id.confirm_button)

        confirmButton.setOnClickListener {
            try {
                Utility.saveData("token", apiToken!!.text.toString().trim { it <= ' ' })
                Networking.getMe(apiToken!!.text.toString().trim { it <= ' ' }, {
                    apiToken!!.post { this.finish() }
                }, { Toast.makeText(confirmButton.context, "Token Invalid", Toast.LENGTH_LONG).show() })
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
