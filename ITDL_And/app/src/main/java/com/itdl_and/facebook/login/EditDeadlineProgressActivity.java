package com.itdl_and.facebook.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;

import receivers.AlarmReceiverDeadlineNote;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.DeadlineNoteEntity;
import model.LocalDataBase;

/**
 * Created by Yasmin Abdel Latif on 6/21/2016.
 */
public class EditDeadlineProgressActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    DeadlineNoteEntity note;
    Button btnUpdateDeadlineProgress, btnDone;
    Calendar calendar = Calendar.getInstance();
    TextView viewTitle, viewProgress;
    String olddeadlinetitle, oldDatetime, oldPriority;
    SeekBar seekBarProgress;
    int progressValue, deadlineNoteID, oldprogress, alarmID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deadline_progress);

        Intent intent = getIntent();
        deadlineNoteID = intent.getIntExtra("noteID",0);
        alarmID = intent.getIntExtra("alarmID",0);

        viewTitle = (TextView) findViewById(R.id.tvDeadlineProgressTitle);
        btnUpdateDeadlineProgress = (Button) findViewById(R.id.btnUpdateDeadlineProgress);
        seekBarProgress = (SeekBar) findViewById(R.id.seekBarUpdateProgress);
        viewProgress = (TextView) findViewById(R.id.tvNoteProgress);
        btnDone = (Button) findViewById(R.id.btnDoneDeadlineNote);

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        Cursor res = localDataBase.GetNoteById(deadlineNoteID);

        if (res.getCount() == 0)
            return;

        res.moveToFirst();
        String noteType, Priority;
        boolean isDone, isDeleted, isTextcat, isAdded;
        Timestamp creationDate;
        int id;
        noteType = res.getString(res.getColumnIndex("noteType")); // note type
        Log.i("noteType", noteType);
        if (noteType.equals("Deadline")) {
            String deadLineTitle;
            int progressprecentage;
            Timestamp deadlineDate;
            deadLineTitle = res.getString(res.getColumnIndex("deadLineTitle"));
            Priority = res.getString(res.getColumnIndex("Priority"));
            id = res.getInt(res.getColumnIndex("localnoteId"));
            creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
            isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
            isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
            isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
            isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
            progressprecentage = res.getInt(res.getColumnIndex("progressPercentage"));
            deadlineDate = Timestamp.valueOf(res.getString(res.getColumnIndex("deadLineDate")));
            note = new DeadlineNoteEntity("Deadline", creationDate, isDone, isDeleted, isTextcat, isAdded, progressprecentage,
                    deadLineTitle, deadlineDate, Priority);
            note.setNoteId(id);
            olddeadlinetitle = note.getDeadLineTitle();
            oldPriority = note.getNotePriority();
            oldprogress = note.getProgressPercentage();
            oldDatetime = note.getDeadLineDate().toString();

            localDataBase.DeleteNoteAlarmPermanently(alarmID);

            viewTitle.setText(olddeadlinetitle);
            viewProgress.setText("progress is " + oldprogress + " % ");
            seekBarProgress.setProgress(oldprogress);
            seekBarProgress.setOnSeekBarChangeListener(this);
            btnUpdateDeadlineProgress.setOnClickListener(this);
            btnDone.setOnClickListener(this);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int prgress, boolean b) {
        progressValue = prgress;
        viewProgress.setText("Progress is " + prgress + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        viewProgress.setText("Progress is " + progressValue + "%");
    }

    @Override
    public void onClick(View view) {
        if (view == btnUpdateDeadlineProgress) {
            if (progressValue==0){
                progressValue=oldprogress;
            }
            if (oldprogress == progressValue) {
                Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

            } else {
                UserController userController = UserController.getInstance();
                boolean isConnected = userController.isNetworkConnected(getApplicationContext());
                NoteController noteController = new NoteController();
                if (!isConnected) {
                    noteController.UpdateDeadlineNoteInLocalDB(olddeadlinetitle, oldPriority, oldDatetime, progressValue, note.getNoteId());
                } else {
                    noteController.UpdateDeadlineNoteInLocalDB(olddeadlinetitle, oldPriority, oldDatetime, progressValue, note.getNoteId());
                }
            }
        }

        else if(view == btnDone)
        {
            LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
            Cursor cur = localDataBase.GetAlarmByNoteId(deadlineNoteID);
            if (!cur.moveToFirst())
                return;
            cur.moveToFirst();
            do {
                int alarmID = cur.getInt(cur.getColumnIndex("alarmID"));
                cancelAlarm(alarmID);
            } while (cur.moveToNext());

            cur.close();
            localDataBase.DeleteNoteAlarmPermanentlyByNoteID(deadlineNoteID);
        }
    }

    private void cancelAlarm(int alarmID){
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiverDeadlineNote.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), alarmID, intent, 0);
        AlarmManager alarmManager = (AlarmManager)MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private boolean IntToboolean(int x) {
        return x != 0;
    }
}