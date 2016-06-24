package receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.itdl_and.facebook.login.R;
import com.itdl_and.facebook.login.ShowNoteDetailsActivity;

import controllers.MyApplication;
import controllers.NoteController;
import model.MeetingNoteEntity;
import model.NoteEntity;

/**
 * Created by samah on 18/06/2016.
 */
public class AlarmReceiverMeeting extends BroadcastReceiver
{
    PendingIntent pendingIntent;
    NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {

         Toast.makeText(context, "Alarm received ", Toast.LENGTH_LONG).show();
        Log.i("ONReceiverrrrrrr,", "HEREEEE");

        int  noteId = intent.getIntExtra("notid", 0);
        int  alarmId = intent.getIntExtra("requestCode",0);

        notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(MyApplication.getAppContext(), ShowNoteDetailsActivity.class);
        NoteController noteController =new NoteController();
        NoteEntity noteEntity= noteController.GetNoteDetails(noteId);
        MeetingNoteEntity meetingNoteEntity =(MeetingNoteEntity)noteEntity;
        mIntent.putExtra("note", meetingNoteEntity);
        mIntent.putExtra("fromActivity", "Current");

        pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(), alarmId, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources res = MyApplication.getAppContext().getResources();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getAppContext());
        Log.i("meetingNoteEneType=",meetingNoteEntity.getNoteType());
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker("You have a meeting ")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentTitle(meetingNoteEntity.getNoteType())
                .setContentText(meetingNoteEntity.getMeetingTitle());

        notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(MyApplication.getAppContext().NOTIFICATION_SERVICE);
        notificationManager.notify(alarmId, builder.build());

    }

}
