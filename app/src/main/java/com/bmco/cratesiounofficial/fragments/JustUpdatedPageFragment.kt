package com.bmco.cratesiounofficial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.interfaces.OnSummaryChangeListener
import com.bmco.cratesiounofficial.models.Summary
import com.bmco.cratesiounofficial.recyclers.CrateRecyclerAdapter

import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Created by Bertus on 25-5-2017.
 */

class JustUpdatedPageFragment : Fragment() {
    private var itemList: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.trending_page, container, false)
        progressBar = view.findViewById(R.id.progress)

        itemList = view.findViewById(R.id.recycler)
        itemList!!.layoutManager = LinearLayoutManager(itemList!!.context)
        SummaryFragment.listener.add(object : OnSummaryChangeListener {
            override fun summary(summary: Summary) {
                refreshSummary(summary)
            }

            override fun downloadStarted() {
                progressBar!!.post { progressBar!!.visibility = VISIBLE }
            }
        })
        return view
    }

    private fun refreshSummary(summary: Summary) {
        itemList!!.post {
            val adapter = CrateRecyclerAdapter(itemList!!.context, summary.justUpdated)
            itemList!!.adapter = adapter
            progressBar!!.visibility = GONE
        }
    }
}
