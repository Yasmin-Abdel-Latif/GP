package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;

/**
 * Created by samah on 22/03/2016.
 */
public class SynchronizationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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

        return super.onStartCommand(intent, flags, startId);
    }
}
