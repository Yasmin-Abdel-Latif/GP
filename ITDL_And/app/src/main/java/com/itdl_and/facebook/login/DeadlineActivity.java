package com.itdl_and.facebook.login;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import controllers.Alarms;
import receivers.AlarmReceiverDeadlineNote;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.LocalDataBase;

public class DeadlineActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    EditText deadlineTitle;
    Button btnAddDeadlineNote, btnDate, btnTime;
    Calendar calendar = Calendar.getInstance();
    TextView displayDate, DisplayTime, viewProgress;
    String date, time, deadlinedate_time;
    SeekBar seekBarProgress;
    int progressValue;
    RadioGroup priorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline);
        deadlineTitle = (EditText) findViewById(R.id.etDeadlineTitle);
        btnAddDeadlineNote = (Button) findViewById(R.id.btnaddDeadline);
        btnDate = (Button) findViewById(R.id.btnDeadlineDate);
        btnTime = (Button) findViewById(R.id.btnDeadlineTime);
        displayDate = (TextView) findViewById(R.id.tvdisplayDeadlineDate);
        DisplayTime = (TextView) findViewById(R.id.tvdisplayDeadlineTime);
        seekBarProgress = (SeekBar) findViewById(R.id.seekBarProgress);
        priorityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPriority);
        viewProgress = (TextView) findViewById(R.id.tvProgress);
        btnAddDeadlineNote.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        viewProgress.setText("Progress is " + seekBarProgress.getProgress() + "%");
        seekBarProgress.setOnSeekBarChangeListener(this);

    }

    TimePickerDialog.OnTimeSetListener ontimelistener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DecimalFormat formatter = new DecimalFormat("00");
            String hoursFormatted = formatter.format(hours);
            String minuteFormatted = formatter.format(minute);
            DisplayTime.setText(hoursFormatted + ":" + minuteFormatted);
            time = hoursFormatted + ":" + minuteFormatted + ":00";
        }
    };

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
            month = month + 1;
            DecimalFormat formatter = new DecimalFormat("00");
            String monthFormatted = formatter.format(month);
            String dayofmonthFormatted = formatter.format(dayofmonth);
            displayDate.setText("selected Date is " + year + "-" + monthFormatted + "-" + dayofmonthFormatted);
            date = year + "-" + monthFormatted + "-" + dayofmonthFormatted;
        }
    };

    @Override
    public void onClick(View view) {
        if (view == btnTime) {
            new TimePickerDialog(DeadlineActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        } else if (view == btnDate) {
            new DatePickerDialog(DeadlineActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnAddDeadlineNote) {
            int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton btnpriority = (RadioButton) findViewById(selectedId);
            String priority = btnpriority.getText().toString();
            String title = deadlineTitle.getText().toString();
            deadlinedate_time = date + " " + time;
            UserController userController = UserController.getInstance();
            boolean isConnected = userController.isNetworkConnected(getApplicationContext());
            NoteController noteController = new NoteController();

            int noteID = -1;

            if (!isConnected) {
                noteID = noteController.addDeadlineNoteInLoacalDB(title, priority, deadlinedate_time, progressValue, false, 0);
            } else {
                noteID = noteController.addDeadlineNote(title, priority, deadlinedate_time, progressValue);
            }

            if (noteID > 0) {
                long milliseconds = ((Timestamp.valueOf(date + " " + time).getTime() - new Date().getTime()) / (2));
                long middle = new Date().getTime() + milliseconds;
                Timestamp firstAlarm = new Timestamp(middle);
                Log.i("HELLO FirstAlarm", firstAlarm.toString());
                LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
                int alarmID = localDataBase.UpdateAlarmCell();
                Alarms alarms = new Alarms();
                alarms.setAlarmDeadline(firstAlarm, alarmID, noteID, 1);
                localDataBase.InsertNoteAlarm(noteID, alarmID);

                Timestamp secondAlarm = new Timestamp(Timestamp.valueOf(date + " " + time).getTime() - (1000 * 60 * 60));
                Log.i("HELLO SecondAlarm", secondAlarm.toString());
                LocalDataBase localDataBase2 = new LocalDataBase(MyApplication.getAppContext());
                int alarmID2 = localDataBase2.UpdateAlarmCell();
                alarms = new Alarms();
                alarms.setAlarmDeadline(secondAlarm, alarmID2, noteID, 2);
                localDataBase.InsertNoteAlarm(noteID, alarmID2);
            }


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
}
