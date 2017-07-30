package com.bmco.cratesiounofficial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Bertus on 30-7-2017.
 */

public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CrateNotifier", "starting...");
        Intent i = new Intent(context, CrateNotifier.class);
        context.startService(i);
        Log.d("CrateNotifier", "started");
    }
}
