package com.bmco.cratesiounofficial.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView

import com.bmco.cratesiounofficial.activities.MainActivity
import com.bmco.cratesiounofficial.interfaces.OnCrateResult
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.recyclers.SearchRecyclerAdapter
import com.bmco.cratesiounofficial.models.Crate

/**
 * Created by bertuswisman on 26/05/2017.
 */

class SearchFragment : Fragment() {
    private var itemList: RecyclerView? = null
    private var adapter: SearchRecyclerAdapter? = null
    private var progressBar: ProgressBar? = null
    private var sortSpinner: Spinner? = null

    private val sortables = arrayOf("Downloads High > Low", "Downloads Low > High")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sort_crates_layout, container, false)
        itemList = view.findViewById(R.id.recycler)
        progressBar = view.findViewById(R.id.progress)
        sortSpinner = view.findViewById(R.id.sort_spinner)

        val sortAdapter = ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.sortables, R.layout.sortable_item_layout)
        sortSpinner!!.adapter = sortAdapter

        sortSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                adapter!!.sort(sortables[i])
                if (adapterView.getChildAt(0) != null) {
                    (adapterView.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        adapter = SearchRecyclerAdapter(itemList!!.context)
        MainActivity.result = object : OnCrateResult {
            override fun onResult(crate: Crate) {
                itemList!!.post {
                    progressBar!!.post { progressBar!!.visibility = View.GONE }
                    adapter!!.tryAddCrate(crate)
                    adapter!!.sort(sortables[sortSpinner!!.selectedItemPosition])
                }
            }

            override fun needsClear() {
                itemList!!.post { adapter!!.clearCrates() }
            }

            override fun downloading() {
                progressBar!!.post { progressBar!!.visibility = View.VISIBLE }
            }
        }
        itemList!!.layoutManager = LinearLayoutManager(itemList!!.context)
        itemList!!.adapter = adapter

        return view
    }
}
