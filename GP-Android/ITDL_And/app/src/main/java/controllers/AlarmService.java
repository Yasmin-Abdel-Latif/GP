package controllers;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.itdl_and.facebook.login.HabitActivity;
import com.itdl_and.facebook.login.HomeActivity;
import com.itdl_and.facebook.login.MainActivity;
import com.itdl_and.facebook.login.MainFragment;
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


public class AlarmService extends IntentService {
    private static final String TAG = "HELLO";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    Context mContext;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Alarm Service has started.");
        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        try {
            JSONObject jsonObject = new JSONObject(ld.GetUserID());
            String userID = jsonObject.getString("UserID");
            String userTwitterAccount = jsonObject.getString("Twitter_Account");
            Log.i(TAG, userID);
            Log.i(TAG, userTwitterAccount + " Twitter");
            ArrayList<NoteEntity> notes = new ArrayList<NoteEntity>();
            notes.addAll(UserController.getAllNotes(userID));
            if(notes.size() > 0)
            {
                Context context = MyApplication.getAppContext();
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent mIntent = new Intent(MyApplication.getAppContext(), HabitActivity.class);
                String result = "";
                for(int i = 0 ; i < notes.size() ; i++)
                {
                    result += ((OrdinaryNoteEntity)notes.get(i)).getNoteContent() + ", ";
                }
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("Notes", notes);
                mIntent.putExtras(bundleObject);
                pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Resources res = this.getResources();
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setTicker("Suggested Actions")
                        .setSound(alarmSound)
                        .setAutoCancel(true)
                        .setContentTitle("These Are Some Notes That Might Interest You")
                        .setContentText(result);

                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Timestamp ts = new Timestamp(new Date().getTime());
                String curDay = (new SimpleDateFormat("EEEE", Locale.getDefault())).format(ts.getTime());
                notificationManager.notify(weekDayToInt(curDay), builder.build());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int weekDayToInt(String weekDay) {
        if (weekDay.equals("Saturday")) {
            return 1;
        } else if (weekDay.equals("Sunday")) {
            return 2;
        } else if (weekDay.equals("Monday")) {
            return 3;
        } else if (weekDay.equals("Tuesday")) {
            return 4;
        } else if (weekDay.equals("Wednesday")) {
            return 5;
        } else if (weekDay.equals("Thursday")) {
            return 6;
        } else if (weekDay.equals("Friday")) {
            return 7;
        }
        return 0;
    }
}