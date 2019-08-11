package com.bmco.cratesiounofficial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.TrendingPageAdapter
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener
import com.bmco.cratesiounofficial.models.Summary
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.tabs.TabLayout
import java.util.*

/**
 * Created by bertuswisman on 26/05/2017.
 */

class SummaryFragment : Fragment() {
    private var mTrendingPager: ViewPager? = null

    private var swipeRefresh: SwipeRefreshLayout? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.summary, container, false)

        mTrendingPager = view.findViewById(R.id.trending_pager)
        mTrendingPager!!.adapter = TrendingPageAdapter(fragmentManager!!)

        // Give the TabLayout the ViewPager
        val tabLayout = view.findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(mTrendingPager)

        for (l in listener) {
            l.downloadStarted()
        }

        refreshSummary()

        swipeRefresh = view.findViewById(R.id.refresher)
        swipeRefresh!!.setOnRefreshListener {
            refreshSummary()
        }

        mTrendingPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, v: Float, i1: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE)
            }
        })
        mTrendingPager!!.offscreenPageLimit = 2

        return view
    }

    private fun refreshSummary() {
        Fuel.get(Utility.getAbsoluteUrl(Utility.SUMMARY))
                .response { _, _, result ->
                    val (bytes, error) = result
                    if (bytes != null) {
                        println(String(bytes))
                        summary = ObjectMapper().readValue(String(bytes), Summary::class.java)
                        swipeRefresh!!.post { swipeRefresh!!.isRefreshing = false }
                        for (l in listener) {
                            l.summary(summary)
                        }
                    }
                    if (error != null) {
                        mTrendingPager!!.post {
                            Toast.makeText(mTrendingPager!!.context, "Can't load summary: $error", Toast.LENGTH_LONG).show()
                            println(error)
                        }
                    }
                }
    }

    private fun enableDisableSwipeRefresh(enable: Boolean) {
        if (swipeRefresh != null) {
            swipeRefresh!!.isEnabled = enable
        }
    }

    companion object {
        lateinit var summary: Summary
        var listener: MutableList<OnSummaryChangeListener> = ArrayList()
    }
}
