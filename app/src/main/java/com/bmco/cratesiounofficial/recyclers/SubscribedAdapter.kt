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
import com.bmco.cratesiounofficial.Utility
import com.bmco.cratesiounofficial.models.Alert
import com.bmco.cratesiounofficial.models.Crate

import ru.dimorinny.floatingtextbutton.FloatingTextButton

/**
 * Created by Bertus on 25-5-2017.
 */

class SubscribedAdapter(private val context: Context, private val alerts: MutableList<Alert>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.subscribed_recycler_item, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val alert = alerts[position]
        val crate = alert.crate

        val fab = holder.itemView.findViewById<FloatingTextButton>(R.id.delete_button)
        val title = holder.itemView.findViewById<TextView>(R.id.crate_title)

        title.text = crate!!.name

        fab.setOnClickListener { v ->
            alerts.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            Utility.saveData("alerts", alerts)
        }

        holder.itemView.setOnClickListener { v ->
            val intent = Intent(context, CrateActivity::class.java)
            intent.putExtra("crate", crate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    private class CustomViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
