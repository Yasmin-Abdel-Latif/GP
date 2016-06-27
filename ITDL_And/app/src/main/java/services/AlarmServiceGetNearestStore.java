package services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.itdl_and.facebook.login.GetNearestStoresActivity;
import com.itdl_and.facebook.login.HabitActivity;
import com.itdl_and.facebook.login.MainActivity;
import com.itdl_and.facebook.login.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import controllers.MyApplication;
import controllers.Recomm_Controller;
import model.LocalDataBase;
import model.NoteEntity;
import model.OrdinaryNoteEntity;

/**
 * Created by Yasmin Abdel Latif on 6/18/2016.
 */
public class AlarmServiceGetNearestStore extends IntentService {
    private static final String TAG = "HELLO";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    public AlarmServiceGetNearestStore() {
        super("AlarmServiceGetNearestStore");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int alarmID = intent.getIntExtra("alarmID",0);
        Recomm_Controller callGetNearestStoresABI = new Recomm_Controller();
        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        try {
            String resultLD = ld.GetUserID();
            if (resultLD.trim().length() > 0) {
                JSONObject jsonObject = new JSONObject(resultLD);
                String userID = jsonObject.getString("UserID");
                String result = callGetNearestStoresABI.getNearestStores(userID);


                JSONObject jsonRootObject = new JSONObject(result);
                int resultSize = jsonRootObject.getInt("resultSize");
                Log.i("HELLO NEAREST STORE", String.valueOf(resultSize));
                if (resultSize > 0) {
                    Log.i("Stores Res API : ", result);
                    Context context = MyApplication.getAppContext();
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent mIntent = new Intent(MyApplication.getAppContext(), GetNearestStoresActivity.class);

                    mIntent.putExtra("storesOutput", result);
                    pendingIntent = PendingIntent.getActivity(context, alarmID, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Resources res = this.getResources();
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                    builder.setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                            .setTicker("Nearest Stores")
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentTitle("You Might Want to See these stores")
                            .setContentText("You Might Want to See these stores");

                    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(alarmID, builder.build());
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
