package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import services.AlarmServiceGetNearestStore;

/**
 * Created by Yasmin Abdel Latif on 6/18/2016.
 */
public class AlarmReceiverGetNearestStore extends BroadcastReceiver {

    private static final String TAG = "HELLO";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "l has received alarm intent.");
        int alarmID = intent.getIntExtra("alarmID",0);
        Intent service1 = new Intent(context, AlarmServiceGetNearestStore.class);
        service1.putExtra("alarmID", alarmID);
        context.startService(service1);
    }

}
