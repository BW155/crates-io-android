package com.bmco.cratesiounofficial;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bmco.cratesiounofficial.models.Alert;
import com.bmco.cratesiounofficial.models.Crate;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bertus on 29-7-2017.
 */

public class CrateNotifier extends IntentService {
    private List<Alert> alertList;
    public static int maxNotificationId = 1;

    public static boolean started = false;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public CrateNotifier() {
        super("CrateNotifier");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Crate Notifier", "handle intent");
        if (started) {
            return;
        }
        started = true;
        Utility.InitSaveLoad(this);

        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    Log.i("Crate Notifier", "check");
                    try {
                        Type listType = new TypeToken<ArrayList<Alert>>(){}.getType();
                        alertList = Utility.loadData("alerts", listType);
                        if (alertList != null) {
                            for (int i = 0; i < alertList.size(); i++) {
                                Alert alert = alertList.get(i);
                                Crate newCrate = CratesIONetworking.getCrateById(alert.getCrate().getId());
                                if (newCrate != null) {
                                    if (alert.isDownloads()) {
                                        if (newCrate.getDownloads() > alert.getCrate().getDownloads()) {
                                            alert(newCrate, alert.getCrate(), AlertType.DOWNLOADS);
                                        }
                                    }
                                    if (alert.isVersion()) {
                                        if (!newCrate.getMaxVersion().equals(alert.getCrate().getMaxVersion())) {
                                            alert(newCrate, alert.getCrate(), AlertType.VERSION);
                                        }
                                    }

                                    alertList.get(i).setCrate(newCrate);
                                }
                            }
                            Utility.saveData("alerts", alertList);
                        }
                    } catch (JsonSyntaxException e) {
                        //ignore
                    }
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void alert(Crate crate, Crate oldCrate, AlertType type) {
        String title = type.getName(crate);
        String description = type.getDescription(crate, oldCrate);

        NotificationCompat.BigTextStyle notiStyle = new
                NotificationCompat.BigTextStyle();
        notiStyle.bigText(description);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description).setVibrate(new long[] { 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .setStyle(notiStyle)
                .build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(maxNotificationId, notification);
        if (maxNotificationId + 1 == Integer.MAX_VALUE) {
            maxNotificationId = 0;
        }
        maxNotificationId++;
    }

    public enum AlertType {
        DOWNLOADS, VERSION;

        String getName(Crate crate) {
            switch (this) {
                case DOWNLOADS:
                    return "Downloads changed: " + crate.getName();
                case VERSION:
                    return "Version changed: " + crate.getName();
                default:
                    return "";
            }
        }

        String getDescription(Crate crate, Crate oldCrate) {
            switch (this) {
                case DOWNLOADS:
                    DecimalFormat df = new DecimalFormat("#,##0");
                    String downloads = df.format(Long.valueOf(crate.getDownloads()));
                    String oldDownloads = df.format(Long.valueOf(oldCrate.getDownloads()));
                    return crate.getName() + " now has " + downloads + " downloads was " + oldDownloads;
                case VERSION:
                    return "The version of " + crate.getName() + " changed to: " + crate.getMaxVersion() + " was " + oldCrate.getMaxVersion();
                default:
                    return "";
            }
        }
    }
}
