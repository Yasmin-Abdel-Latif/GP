package com.itdl_and.facebook.login;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import receivers.AlarmReceiverDeadlineNote;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.DeadlineNoteEntity;
import model.LocalDataBase;

public class EditDeadlineNoteActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    DeadlineNoteEntity deadlineNoteEntity;
    EditText deadlineTitle;
    Button btnUpdateDeadlineNote, btnDate, btnTime;
    Calendar calendar = Calendar.getInstance();
    TextView displayDate, DisplayTime, viewProgress;
    String date, time, deadlinedate_time, olddeadlinetitle, oldDatetime, oldPriority;
    SeekBar seekBarProgress;
    int progressValue, oldprogress;
    RadioGroup priorityRadioGroup;
    RadioButton RbtnHigh, RbtnMedium, RbtnLow;
    String[] oldDatetimeparts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deadline_note);

        Intent intent = getIntent();
        deadlineNoteEntity = (DeadlineNoteEntity) intent.getSerializableExtra("note");

        deadlineTitle = (EditText) findViewById(R.id.etDeadlineTitle);
        btnUpdateDeadlineNote = (Button) findViewById(R.id.btnUpdateDeadline);
        btnDate = (Button) findViewById(R.id.btnDeadlineDate);
        btnTime = (Button) findViewById(R.id.btnDeadlineTime);
        displayDate = (TextView) findViewById(R.id.tvdisplayDeadlineDate);
        DisplayTime = (TextView) findViewById(R.id.tvdisplayDeadlineTime);
        seekBarProgress = (SeekBar) findViewById(R.id.seekBarProgress);
        priorityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPriority);
        viewProgress = (TextView) findViewById(R.id.tvProgress);
        RbtnHigh = (RadioButton) findViewById(R.id.radioHigh);
        RbtnMedium = (RadioButton) findViewById(R.id.radioMedium);
        RbtnLow = (RadioButton) findViewById(R.id.radioLow);

        olddeadlinetitle = deadlineNoteEntity.getDeadLineTitle();
        oldPriority = deadlineNoteEntity.getNotePriority();
        oldprogress = deadlineNoteEntity.getProgressPercentage();
        oldDatetime = deadlineNoteEntity.getDeadLineDate().toString();
        oldDatetimeparts = oldDatetime.split(" ");

        deadlineTitle.setText(olddeadlinetitle);
        displayDate.setText(oldDatetimeparts[0]);
        DisplayTime.setText(oldDatetimeparts[1]);
        viewProgress.setText("progress is " + oldprogress + " % ");
        seekBarProgress.setProgress(oldprogress);
        if (oldPriority.equals("High")) {
            RbtnHigh.setChecked(true);
        } else if (oldPriority.equals("Medium")) {
            RbtnMedium.setChecked(true);
        } else if (oldPriority.equals("Low")) {
            RbtnLow.setChecked(true);
        }
        seekBarProgress.setOnSeekBarChangeListener(this);
        btnUpdateDeadlineNote.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);

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
        if (view == btnTime) {
            new TimePickerDialog(EditDeadlineNoteActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        } else if (view == btnDate) {
            new DatePickerDialog(EditDeadlineNoteActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnUpdateDeadlineNote) {

            int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton btnpriority = (RadioButton) findViewById(selectedId);
            String priority = btnpriority.getText().toString();
            String title = deadlineTitle.getText().toString();

            if (date == null) {
                date = oldDatetimeparts[0];
            }
            if (time == null) {
                time = oldDatetimeparts[1];
            }
            if (progressValue == 0) {
                progressValue = oldprogress;
            }
            deadlinedate_time = date + " " + time;
            if (title.equals(olddeadlinetitle) && priority.equals(oldPriority) && date.equals(oldDatetimeparts[0]) && time.equals(oldDatetimeparts[1]) && oldprogress == progressValue) {
                Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

            } else {
                UserController userController = UserController.getInstance();
                boolean isConnected = userController.isNetworkConnected(getApplicationContext());
                NoteController noteController = new NoteController();
                if (!isConnected) {
                    // Toast.makeText(getApplicationContext(), " not Connected ", Toast.LENGTH_LONG).show();
                    noteController.UpdateDeadlineNoteInLocalDB(title, priority, deadlinedate_time, progressValue, deadlineNoteEntity.getNoteId());
                } else {
                    //Toast.makeText(getApplicationContext(), "  Connected ", Toast.LENGTH_LONG).show();
                    noteController.UpdateDeadlineNoteInLocalDB(title, priority, deadlinedate_time, progressValue, deadlineNoteEntity.getNoteId());
                    // noteController.addDeadlineNoteToServer(title, priority, deadlinedate_time, progressValue);
                    //noteController.addDeadlineNoteInLoacalDB(title, priority,deadlinedate_time, progressValue, true);
                }
                if(!date.equals(oldDatetimeparts[0]) || !time.equals(oldDatetimeparts[1]))
                {
                    int deadlineNoteID = deadlineNoteEntity.getNoteId();
                    LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
                    Cursor cur = localDataBase.GetAlarmByNoteId(deadlineNoteID);
                    if (!cur.moveToFirst())
                        return;
                    cur.moveToFirst();
                    int alarmID = cur.getInt(cur.getColumnIndex("Requestcode"));

                    cur.moveToNext();
                    int alarmID2 = cur.getInt(cur.getColumnIndex("Requestcode"));


                    cur.close();
                    localDataBase.DeleteNoteAlarmPermanentlyByNoteID(deadlineNoteID);
                    setNewAlarmDeadline(date, time, deadlineNoteID, alarmID, alarmID2);
                }
            }
        }
    }

    private void cancelAlarm(int alarmID){
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiverDeadlineNote.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), alarmID, intent, 0);
        AlarmManager alarmManager = (AlarmManager)MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void setNewAlarmDeadline(String newDate, String newTime, int noteID, int alarmID, int alarmID2) {
        long milliseconds = ((Timestamp.valueOf(newDate + " " + newTime).getTime() - new Date().getTime()) / (2));
        long middle = new Date().getTime() + milliseconds;
        Timestamp firstAlarm = new Timestamp(middle);
        Log.i("HELLO FirstAlarm", firstAlarm.toString());
        setAlarmDeadline(firstAlarm, alarmID, noteID, 1);

        Timestamp secondAlarm = new Timestamp(Timestamp.valueOf(newDate + " " + newTime).getTime() - (1000 * 60 * 60));
        Log.i("HELLO SecondAlarm", secondAlarm.toString());
        setAlarmDeadline(secondAlarm, alarmID2, noteID, 2);
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
        int day = Integer.parseInt(dateSplit[2]);
        int month = Integer.parseInt(dateSplit[1])-1;
        int year = Integer.parseInt(dateSplit[0]);
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);
        int second = 0;

        Log.i("HELLO SET ALARM", day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" + second);

        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.setTimeInMillis(System.currentTimeMillis());
        alarmStartTime.set(Calendar.DAY_OF_MONTH, day);
        alarmStartTime.set(Calendar.MONTH, month);
        alarmStartTime.set(Calendar.YEAR, year);
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmStartTime.set(Calendar.MINUTE, minute);
        alarmStartTime.set(Calendar.SECOND, second);

        Log.i("HELLO SET ALARM", alarmStartTime.getTime().toString());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent);
    }
}