package controllers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by samah on 22/03/2016.
 */
public class ApplicationService extends Service {
    Timer ConTime;
    int intervalInSec;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intervalInSec = intent.getIntExtra("Interval", 120);
        // MyDBH =intent.getParcelableExtra("MyDBHandler");
        ConTime = new Timer();
        ConTime.schedule(new SyncAdd(), 0, intervalInSec * 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    class SyncAdd extends TimerTask {

        @Override
        public void run() {

            // Toast.makeText(MyApplication.getAppContext(),"in run  ",Toast.LENGTH_LONG).show();

            boolean IsConnected = UserController.getInstance().isNetworkConnected(MyApplication.getAppContext());

            if (IsConnected == true) {
                //select from table with sync 0
                //Toast.makeText(MyApplication.getAppContext(),"ConnectionToInternet is true ",Toast.LENGTH_LONG).show();
                System.out.println("--- IN Run  connected ----");

                Log.i("ConnectionInternet", "connected");
                NoteController noteController = new NoteController();
                String NotSyncNotes = noteController.GetNotSyncNotes();

                if (NotSyncNotes != "") {
                    //  Toast.makeText(MyApplication.getAppContext(),"there is notes  ",Toast.LENGTH_LONG).show();
                    Log.i("CursornnnnteeeeeN000o= ", NotSyncNotes);
                    noteController.Syncroinzation(NotSyncNotes);
                } else {
                    Log.i("Cursor", "Empty");

                }
            }
        }
    }

    @Override
    public void onDestroy() {
        ConTime.cancel();
        super.onDestroy();
    }
}
