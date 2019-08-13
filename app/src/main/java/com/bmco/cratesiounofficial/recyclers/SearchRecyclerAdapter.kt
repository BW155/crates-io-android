package com.bmco.cratesiounofficial.recyclers

/**
 * Created by bertuswisman on 26/05/2017.
 */

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.bmco.cratesiounofficial.activities.CrateActivity
import com.bmco.cratesiounofficial.R
import com.bmco.cratesiounofficial.models.Crate

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Collections

/**
 * Created by Bertus on 25-5-2017.
 */

class SearchRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val crates: MutableList<Crate>

    init {
        crates = ArrayList()
    }

    fun tryAddCrate(crate: Crate) {
        for (internal in crates) {
            if (crate.id != null && internal.id != null) {
                if (internal.id == crate.id) {
                    return
                }
            }
        }
        crates.add(crate)
        this.notifyItemInserted(crates.indexOf(crate))
    }

    fun clearCrates() {
        crates.clear()
        this.notifyDataSetChanged()
    }

    fun sort(by: String) {
        when (by) {
            "Downloads High > Low" -> crates.sortWith(Comparator { crate, crate1 -> crate1.downloads.compareTo(crate.downloads) })
            "Downloads Low > High" -> crates.sortWith(Comparator { crate, crate1 -> crate.downloads.compareTo(crate1.downloads) })
        }
        this.notifyItemRangeChanged(0, crates.size)
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.crate_item, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crate = crates[position]

        val crateName = holder.itemView.findViewById<TextView>(R.id.crate_title)
        val crateDescription = holder.itemView.findViewById<TextView>(R.id.crate_description)
        val crateDownloads = holder.itemView.findViewById<TextView>(R.id.crate_downloads)
        val crateMaxVersion = holder.itemView.findViewById<TextView>(R.id.crate_max_version)

        crateName.text = crate.name
        crateDescription.text = crate.description
        val df = DecimalFormat("#,##0")
        crateDownloads.text = df.format(java.lang.Long.valueOf(crate.downloads.toLong()))
        crateMaxVersion.text = crate.maxVersion?.let { "v$it" } ?: run { "No Version" }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CrateActivity::class.java)
            intent.putExtra("crate", crate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return crates.size
    }

    private class CustomViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}

