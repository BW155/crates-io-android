package com.bmco.cratesiounofficial

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by Bertus on 30-7-2017.
 */

class AutoStart : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CrateNotifier", "starting...")
        val i = Intent(context, CrateNotifier::class.java)
        context.startService(i)
        Log.d("CrateNotifier", "started")
    }
}
