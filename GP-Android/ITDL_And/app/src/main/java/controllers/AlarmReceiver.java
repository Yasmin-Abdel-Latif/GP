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
        int alarmID = intent.getIntExtra("alarmID",0);
        Intent service1 = new Intent(context, AlarmService.class);
        service1.putExtra("alarmID", alarmID);
        context.startService(service1);
    }

}
