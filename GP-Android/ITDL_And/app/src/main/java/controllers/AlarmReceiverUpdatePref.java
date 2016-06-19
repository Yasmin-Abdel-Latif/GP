package controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Yasmin Abdel Latif on 6/18/2016.
 */
public class AlarmReceiverUpdatePref  extends BroadcastReceiver {

    private static final String TAG = "HELLO";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "l has received alarm intent.");
        Intent service1 = new Intent(context, AlarmServiceUpdatePref.class);
        context.startService(service1);
    }

}
