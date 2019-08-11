package com.bmco.cratesiounofficial.recyclers

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

/**
 * Created by bertuswisman on 20/11/2017.
 */

class CrateRecyclerAdapter(private val context: Context, private val crates: List<Crate>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        crateMaxVersion.text = "v" + crate.maxVersion!!

        holder.itemView.setOnClickListener { v ->
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

