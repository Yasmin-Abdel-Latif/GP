package controllers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "HELLO";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "l has received alarm intent.");
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);
    }

}
