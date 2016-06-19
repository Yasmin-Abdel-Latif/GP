package controllers;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.itdl_and.facebook.login.GetOffersActivity;
import com.itdl_and.facebook.login.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import model.LocalDataBase;

/**
 * Created by Yasmin Abdel Latif on 6/18/2016.
 */
public class AlarmServiceGetOffer extends IntentService {
    private static final String TAG = "HELLO";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    Context mContext;

    public AlarmServiceGetOffer() {
        super("AlarmServiceGetOffer");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Recomm_Controller callGetOfferABI = new Recomm_Controller();
        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        try {
            String resultLD = ld.GetUserID();
            if (resultLD.trim().length() > 0) {
                JSONObject jsonObject = new JSONObject(resultLD);
                String userID = jsonObject.getString("UserID");
                String result = callGetOfferABI.getNearestStores(userID);

                JSONObject jsonRootObject = new JSONObject(result);
                int resultSize = jsonRootObject.getInt("resultSize");
                Log.i("HELLO GET OFFER", String.valueOf(resultSize));
                if (resultSize > 0) {
                    Log.i("Offers Res API : ",result);
                    Context context = MyApplication.getAppContext();
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent mIntent = new Intent(MyApplication.getAppContext(), GetOffersActivity.class);

                    mIntent.putExtra("offersOutput", result);
                    pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Resources res = this.getResources();
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                    builder.setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                            .setTicker("Offers")
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentTitle("You Might be Interested in these Offers")
                            .setContentText("You Might be Interested in these Offers");

                    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    Timestamp ts = new Timestamp(new Date().getTime());
                    String curDay = (new SimpleDateFormat("EEEE", Locale.getDefault())).format(ts.getTime());
                    notificationManager.notify(weekDayToInt(curDay), builder.build());
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
