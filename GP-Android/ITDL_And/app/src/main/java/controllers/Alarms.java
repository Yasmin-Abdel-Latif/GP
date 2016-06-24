package controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.LocalDataBase;
import receivers.AlarmReceiverDeadlineNote;
import receivers.AlarmReceiverMeeting;

/**
 * Created by samah on 18/06/2016.
 */
public class Alarms {

    public void CancelAlarm(int requestcode, int noteid) {
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiverMeeting.class);
        intent.putExtra("notid", noteid);
        intent.putExtra("requestCode", requestcode);
        PendingIntent sender = PendingIntent.getBroadcast(MyApplication.getAppContext(), requestcode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(MyApplication.getAppContext().ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setMeetingAlarm(Calendar targetCal, int noteid, int alarmid) {
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiverMeeting.class);
        Log.i("TimmmmmeithLAi=", String.valueOf(targetCal.getTime()) + "  /- " + alarmid);
        intent.putExtra("notid", noteid);
        intent.putExtra("requestCode", alarmid);
        pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), alarmid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public Calendar getDateBefore(String datetime) {
        Calendar calendar = Calendar.getInstance();
        //Calendar.JUNE
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //cal.setTime(sdf.parse("2016-5-19 01:22:00"));
        try {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.clear();
            calendar.setTime(sdf.parse(datetime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        //calendar.add(Calendar.MINUTE, -3);

        return calendar;
    }

    public String[] SplitTime(String t) {
        String[] a = t.split(":");
        return a;
    }

    public String[] SplitDashedDate(String t) {
        String[] a = t.split("-");
        return a;
    }

    public Calendar getBeforeEstimatedTime(String datetime, String EstimatedTime) {

        String[] EstimatedTime1 = SplitTime(EstimatedTime);
        int estmuinit, esthours;
        esthours = Integer.valueOf(EstimatedTime1[0]);
        estmuinit = Integer.valueOf(EstimatedTime1[1]);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        //cal.setTime(sdf.parse("2016-5-19 01:22:00"));
        try {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.clear();
            calendar.setTime(sdf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(calendar.HOUR, -esthours);
        calendar.add(calendar.MINUTE, -estmuinit);
        return calendar;
        //return Back;

    }

    public void setAlarmDeadline(Timestamp alarmTime, int alarmID, int noteID, int alarmType) {
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(MyApplication.getAppContext(), AlarmReceiverDeadlineNote.class);
        alarmIntent.putExtra("alarmID", alarmID);
        alarmIntent.putExtra("noteID", noteID);
        alarmIntent.putExtra("alarmType", alarmType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), alarmID, alarmIntent, 0);

        String dateStr = alarmTime.toString().split(" ")[0];
        String timeStr = alarmTime.toString().split(" ")[1];

        String[] dateSplit = dateStr.split("-");
        String[] timeSplit = timeStr.split(":");
        Calendar alarmStartTime = Calendar.getInstance();
        int day = Integer.parseInt(dateSplit[2]);
        int month = Integer.parseInt(dateSplit[1]) - 1;
        int year = Integer.parseInt(dateSplit[0]);
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);
        int second = 0;

        Log.i("HELLO SET ALARM", day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" + second);

        alarmStartTime.set(Calendar.DAY_OF_MONTH, day);
        alarmStartTime.set(Calendar.MONTH, month);
        alarmStartTime.set(Calendar.YEAR, year);

        alarmStartTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmStartTime.set(Calendar.MINUTE, minute);
        alarmStartTime.set(Calendar.SECOND, second);

        Log.i("HELLO SET ALARM", alarmStartTime.getTime().toString());

        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent);

    }
}