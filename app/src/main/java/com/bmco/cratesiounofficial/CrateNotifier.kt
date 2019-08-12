package com.bmco.cratesiounofficial

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationCompat
import android.util.Log

import com.bmco.cratesiounofficial.models.Alert
import com.bmco.cratesiounofficial.models.Crate
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

import java.text.DecimalFormat
import java.util.ArrayList

/**
 * Created by Bertus on 29-7-2017.
 */

/**
 * Creates an IntentService.  Invoked by your subclass's constructor.
 *
 */
class CrateNotifier : IntentService("CrateNotifier") {
    private var alertList: List<Alert>? = null

    override fun onHandleIntent(intent: Intent?) {
        Log.d("Crate Notifier", "handle intent")
        if (started) {
            return
        }
        started = true
        Utility.initSaveLoad(this)

        val thread = object : Thread() {
            override fun run() {
                while (true) {
                    Log.i("Crate Notifier", "check")
                    try {
                        val listType = object : TypeToken<ArrayList<Alert>>() {

                        }.type
                        alertList = Utility.loadData<List<Alert>>("alerts", listType)
                        if (alertList != null) {
                            for (i in alertList!!.indices) {
                                val alert = alertList!![i]
                                Networking.getCrateById(alert.crate!!.id!!, { crate ->
                                    if (alert.isDownloads) {
                                        if (crate.downloads > alert.crate!!.downloads) {
                                            alert(crate, alert.crate, AlertType.DOWNLOADS)
                                        }
                                    }
                                    if (alert.isVersion) {
                                        if (crate.maxVersion != alert.crate!!.maxVersion) {
                                            alert(crate, alert.crate, AlertType.VERSION)
                                        }
                                    }

                                    alertList!![i].crate = crate
                                }, {  })

                            }
                            Utility.saveData("alerts", alertList!!)
                        }
                    } catch (e: JsonSyntaxException) {
                        //ignore
                    }

                    try {
                        sleep((1000 * 60).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        thread.start()
    }

    private fun alert(crate: Crate?, oldCrate: Crate?, type: AlertType) {
        val title = type.getName(crate)
        val description = type.getDescription(crate, oldCrate)

        val notificationStyle = NotificationCompat.BigTextStyle()
        notificationStyle.bigText(description)

        val notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description).setVibrate(longArrayOf(1000, 1000, 1000))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .setStyle(notificationStyle)
                .build()

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(maxNotificationId, notification)
        if (maxNotificationId + 1 == Integer.MAX_VALUE) {
            maxNotificationId = 0
        }
        maxNotificationId++
    }

    enum class AlertType {
        DOWNLOADS, VERSION;

        internal fun getName(crate: Crate?): String {
            return when (this) {
                DOWNLOADS -> "Downloads changed: " + crate!!.name!!
                VERSION -> "Version changed: " + crate!!.name!!
            }
        }

        internal fun getDescription(crate: Crate?, oldCrate: Crate?): String {
            return when (this) {
                DOWNLOADS -> {
                    val df = DecimalFormat("#,##0")
                    val downloads = df.format(java.lang.Long.valueOf(crate!!.downloads.toLong()))
                    val oldDownloads = df.format(java.lang.Long.valueOf(oldCrate!!.downloads.toLong()))
                    crate.name + " now has " + downloads + " downloads was " + oldDownloads
                }
                VERSION -> "The version of " + crate!!.name + " changed to: " + crate.maxVersion + " was " + oldCrate!!.maxVersion
            }
        }
    }

    companion object {
        var maxNotificationId = 1

        var started = false
    }
}
