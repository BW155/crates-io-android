package com.bmco.cratesiounofficial.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.bmco.cratesiounofficial.CrateNotifier
import com.bmco.cratesiounofficial.Networking
import com.bmco.cratesiounofficial.NonSwipeableViewPager
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.fragments.SearchFragment
import com.bmco.cratesiounofficial.fragments.SummaryFragment
import com.bmco.cratesiounofficial.interfaces.OnCrateResult
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener
import com.bmco.cratesiounofficial.models.Crate
import com.bmco.cratesiounofficial.models.Summary
import com.bmco.cratesiounofficial.models.User
import com.loopj.android.http.AsyncHttpResponseHandler

import java.io.IOException
import java.text.DecimalFormat

import cz.msebera.android.httpclient.Header
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var searchView: SearchView? = null
    private var downloads: TextView? = null
    private var crates: TextView? = null
    private var profileUsername: TextView? = null
    private var summarySearchPager: NonSwipeableViewPager? = null
    private var profileSection: LinearLayout? = null
    private var profileImage: CircleImageView? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.InitSaveLoad(this)

        val i = Intent(this, CrateNotifier::class.java)
        startService(i)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)

        summarySearchPager = findViewById(R.id.summary_search_pager)
        summarySearchPager!!.adapter = SummarySearchPageAdapter(supportFragmentManager)

        downloads = header.findViewById(R.id.downloads)
        crates = header.findViewById(R.id.crates)
        profileSection = header.findViewById(R.id.profile_section)
        profileImage = header.findViewById(R.id.profile_image)
        profileUsername = header.findViewById(R.id.profile_username)

        profileSection!!.visibility = View.INVISIBLE

        SummaryFragment.listener.add(object : OnSummaryChangeListener {
            override fun summary(summary: Summary) {
                crates!!.post {
                    val df = DecimalFormat("#,##0")
                    crates!!.text = df.format(java.lang.Long.valueOf(summary.numCrates.toLong()))
                    downloads!!.text = df.format(java.lang.Long.valueOf(summary.numDownloads.toLong()))
                }
            }

            override fun downloadStarted() {

            }
        })
        menu = navigationView.menu
        loadNavProfile()
    }

    private fun loadNavProfile() {
        val login = menu!!.findItem(R.id.action_login)
        val profile = menu!!.findItem(R.id.action_dashboard)
        val logout = menu!!.findItem(R.id.logout_action)
        if (Utility.loadData<Any>("token", String::class.java) != null) {
            val thread = object : Thread() {
                override fun run() {
                    try {
                        Networking.getMe(Utility.loadData("token", String::class.java)!!, { user ->
                            currentUser = user
                            profileSection!!.post {
                                if (profileSection!!.visibility == View.INVISIBLE) {
                                    Toast.makeText(this@MainActivity, "Logged in as: " + user.login!!, Toast.LENGTH_LONG).show()
                                }
                                profileSection!!.visibility = View.VISIBLE
                                profileUsername!!.text = user.login
                                login.isVisible = false
                                profile.isVisible = true
                                logout.isVisible = true
                            }
                            Utility.getSSL(user.avatar!!, object : AsyncHttpResponseHandler() {
                                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                                    val bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.size)
                                    avatar = bitmap
                                    profileImage!!.post { profileImage!!.setImageBitmap(bitmap) }
                                }

                                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {

                                }
                            })
                            null
                        }, { error -> null })

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            thread.start()
        } else {
            profileSection!!.visibility = View.INVISIBLE
            logout.isVisible = false
            login.isVisible = true
            profile.isVisible = false
        }
    }

    private inner class SummarySearchPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return SummaryFragment()
                1 -> return SearchFragment()
                else -> return null
            }
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (summarySearchPager!!.currentItem == 0) {
                super.onBackPressed()
            } else {
                summarySearchPager!!.currentItem = summarySearchPager!!.currentItem - 1
                searchView!!.isIconified = true
                searchView!!.isIconified = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_search)
        searchView = item.actionView as SearchView
        if (searchView != null) {
            searchView!!.queryHint = resources.getString(R.string.query_hint)
            searchView!!.isIconified = true
            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    println("on search submit: $query")
                    val searchThread = object : Thread() {
                        override fun run() {
                            result!!.needsClear()
                            result!!.downloading()

                            Networking.searchCrate(query, 1, { crates ->
                                for (crate in crates) {
                                    result!!.onResult(crate)
                                }
                                searchView!!.post { searchView!!.clearFocus() }
                                null
                            }, { error -> null })
                        }
                    }
                    searchThread.start()
                    MainActivity.result!!.onResult(Crate())
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.length > 0) {
                        if (summarySearchPager!!.currentItem != 1) {
                            summarySearchPager!!.setCurrentItem(1, true)
                        }
                    }
                    return false
                }
            })
            searchView!!.setOnCloseListener {
                println("on search close")
                summarySearchPager!!.setCurrentItem(0, true)
                false
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.action_login) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 200)
        }

        if (id == R.id.logout_action) {
            Utility.saveData("token", Any())
            this.loadNavProfile()
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
        }

        if (id == R.id.action_dashboard) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        if (id == R.id.action_about) {
            val dialog = AlertDialog.Builder(this).create()
            dialog.setTitle("About")
            dialog.setMessage(resources.getString(R.string.about))
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OKE") { dialog1, which -> dialog1.dismiss() }
            dialog.show()
        }

        if (id == R.id.action_cargo) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://crates.io/"))
            startActivity(browserIntent)
        }

        if (id == R.id.action_subscribed) {
            val intent = Intent(this, SubscribedActivity::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 200) {
            this.loadNavProfile()
        }
    }

    companion object {

        var result: OnCrateResult? = null

        lateinit var avatar: Bitmap
        lateinit var currentUser: User
    }
}
