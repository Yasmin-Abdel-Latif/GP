package controllers;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.itdl_and.facebook.login.EditDeadlineProgressActivity;
import com.itdl_and.facebook.login.HabitActivity;
import com.itdl_and.facebook.login.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import model.LocalDataBase;
import model.NoteEntity;
import model.OrdinaryNoteEntity;

/**
 * Created by Yasmin Abdel Latif on 6/20/2016.
 */
public class AlarmServiceDeadlineNote extends IntentService {
    private static final String TAG = "HELLO";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    Context mContext;

    public AlarmServiceDeadlineNote() {
        super("AlarmServiceDeadlineNote");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Alarm Service has started.");

        int alarmID = intent.getIntExtra("alarmID", 0);
        int noteID = intent.getIntExtra("noteID", 0);
        int alarmType = intent.getIntExtra("alarmType", 0);

        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        Cursor cur = ld.GetNoteById(noteID);
        if (!cur.moveToFirst())
            return;
        cur.moveToFirst();
        String noteTitle = cur.getString(cur.getColumnIndex("deadLineTitle"));
        String text = "you have 1 hour till deadline";

        cur.close();
        Log.i(TAG, noteID + "");
        Log.i(TAG, alarmID + "");
        if (alarmType == 1) {
            text = "be careful you just passed 50 percent of task time, Update progress!";
        }
        Context context = MyApplication.getAppContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(MyApplication.getAppContext(), EditDeadlineProgressActivity.class);
        mIntent.putExtra("noteID", noteID);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker(noteTitle)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentTitle(text)
                .setContentText(text);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(alarmID, builder.build());

    }
}
