package controllers;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import model.LocalDataBase;

/**
 * Created by samah on 23/06/2016.
 */
public class AlarmController {

    public void SetMeetingAlarm(String datetime,String estimatedTime ,int id){
        Log.i("SetMeetingAlarmNoteID=", String.valueOf(id));
        Alarms alarms = new Alarms();
        //Alarms alarms2 = new Alarms();

        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        Calendar d=alarms.getDateBefore(datetime);
        int dayrequestCode=localDataBase.UpdateAlarmCell();
        alarms.setMeetingAlarm(d,id,dayrequestCode);
        Toast.makeText(MyApplication.getAppContext(), " dayrequestCode " + dayrequestCode, Toast.LENGTH_LONG).show();
        Log.i("dayrequestCode=", String.valueOf(dayrequestCode));
        long re= localDataBase.InsertNoteAlarm(id, dayrequestCode);

        Calendar d2=alarms.getBeforeEstimatedTime(datetime,estimatedTime);
        int timerequestCode=localDataBase.UpdateAlarmCell();
        alarms.setMeetingAlarm(d2, id, timerequestCode);
        Log.i("timerequestCode=", String.valueOf(timerequestCode));
        Toast.makeText(MyApplication.getAppContext(), " timerequestCode " + timerequestCode, Toast.LENGTH_LONG).show();
        long re1= localDataBase.InsertNoteAlarm(id, timerequestCode);
        // Toast.makeText(MyApplication.getAppContext(), " res "+re, Toast.LENGTH_LONG).show();
    }

    public void DeleteAlram(int noteid){
        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        Alarms alarms =new Alarms();
        Cursor res= localDataBase.GetAlarmByNoteId(noteid);
        Log.i("countttttt=", String.valueOf(res.getCount()));

        res.moveToFirst();
        int daybeforerequest = res.getInt(res.getColumnIndex("Requestcode"));

        res.moveToNext();
        int beforetimerequest = res.getInt(res.getColumnIndex("Requestcode"));
        Log.i("daybeforerequest=", String.valueOf(daybeforerequest));
        Log.i("beforetimerequest=", String.valueOf(beforetimerequest));
        alarms.CancelAlarm(daybeforerequest, noteid);
        alarms.CancelAlarm(beforetimerequest, noteid);
        localDataBase.DeleteNoteAlarmPermanentlyByNoteID(noteid);
    }



    public void UpdateAlarm(String datetime,String timeEstimates,int noteid){
        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        Alarms alarms =new Alarms();
        Cursor res= localDataBase.GetAlarmByNoteId(noteid);

        res.moveToFirst();
        int daybeforerequest = res.getInt(res.getColumnIndex("Requestcode"));

        res.moveToNext();
        int beforetimerequest = res.getInt(res.getColumnIndex("Requestcode"));

        Log.i("daybeforerequest=", String.valueOf(daybeforerequest));
        Log.i("beforetimerequest=", String.valueOf(beforetimerequest));
        Calendar c= alarms.getDateBefore(datetime);
        Calendar c2=alarms.getBeforeEstimatedTime(datetime,timeEstimates);
        alarms.setMeetingAlarm(c, noteid, daybeforerequest);
        alarms.setMeetingAlarm(c2, noteid, beforetimerequest);

    }

}
