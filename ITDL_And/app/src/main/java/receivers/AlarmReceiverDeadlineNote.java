package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import services.AlarmServiceDeadlineNote;

/**
 * Created by Yasmin Abdel Latif on 6/20/2016.
 */
public class AlarmReceiverDeadlineNote extends BroadcastReceiver {

    private static final String TAG = "HELLO";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "l has received alarm intent.");
        int alarmID = intent.getIntExtra("alarmID",0);
        int noteID = intent.getIntExtra("noteID",0);
        int alarmType = intent.getIntExtra("alarmType",0);
        Intent service1 = new Intent(context, AlarmServiceDeadlineNote.class);
        service1.putExtra("alarmID", alarmID);
        service1.putExtra("noteID", noteID);
        service1.putExtra("alarmType", alarmType);
        context.startService(service1);
    }
}
