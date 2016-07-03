package com.itdl_and.facebook.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

import controllers.AlarmController;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.MeetingNoteEntity;

public class EditMeetingNoteActivity extends ActionBarActivity implements View.OnClickListener {
    Calendar calendar = Calendar.getInstance();
    Button btnDate, btnEstimatedTime, btnUpdateNote, btnMeetingTime;
    EditText Title, Agenda, meeingPlace;
    TextView displayDate, DisplayTime, DisplayTimeEstimation;
    String date, time, oldtitle, oldAgnda, oldPlace, olddate, oldtime, oldpriority, oldEstimatedTime;
    String estimatedTime, temp;

    RadioGroup priorityRadioGroup;
    RadioButton RbtnHigh, RbtnMedium, RbtnLow;
    MeetingNoteEntity meetingNoteEntity;
    String date1, time1, estimatedTime1, temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting_note);
        Intent intent = getIntent();
        meetingNoteEntity = (MeetingNoteEntity) intent.getSerializableExtra("note");
        priorityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPriority);

        Title = (EditText) findViewById(R.id.etMeetingTitle);
        Agenda = (EditText) findViewById(R.id.etMeetingAgenda);
        meeingPlace = (EditText) findViewById(R.id.etMeetingPlace);
        btnDate = (Button) findViewById(R.id.btnMeetingDate);
        displayDate = (TextView) findViewById(R.id.tvdisplayDate);
        DisplayTime = (TextView) findViewById(R.id.tvDisplayTime);
        btnMeetingTime = (Button) findViewById(R.id.btnMeetingTime);
        DisplayTimeEstimation = (TextView) findViewById(R.id.tvDisplayEstimatedTime);
        btnEstimatedTime = (Button) findViewById(R.id.btnEstimatedTime);
        btnUpdateNote = (Button) findViewById(R.id.btnAddMeetingNote);
        RbtnHigh = (RadioButton) findViewById(R.id.radioButtonHigh);
        RbtnMedium = (RadioButton) findViewById(R.id.radioButtonMedium);
        RbtnLow = (RadioButton) findViewById(R.id.radioButtonLow);

        String[] datetime = meetingNoteEntity.getMeetingNoteDate().toString().split(" ");

        oldtitle = meetingNoteEntity.getMeetingTitle();
        oldAgnda = meetingNoteEntity.getMeetingAgenda();
        oldPlace = meetingNoteEntity.getMeetingPlace();
        oldEstimatedTime = meetingNoteEntity.getEstimatedTransportTime().toString();
        olddate = datetime[0];
        oldtime = datetime[1];
        oldpriority = meetingNoteEntity.getNotePriority();

        if (meetingNoteEntity.getNotePriority().equals("High")) {
            RbtnHigh.setChecked(true);
        } else if (meetingNoteEntity.getNotePriority().equals("Medium")) {
            RbtnMedium.setChecked(true);

        } else if (meetingNoteEntity.getNotePriority().equals("Low")) {
            RbtnLow.setChecked(true);
        }

        Title.setText(oldtitle);
        Agenda.setText(oldAgnda);
        meeingPlace.setText(oldPlace);
        displayDate.setText(olddate);
        DisplayTime.setText(oldtime);
        DisplayTimeEstimation.setText(oldEstimatedTime);
        btnUpdateNote.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnMeetingTime.setOnClickListener(this);
        btnEstimatedTime.setOnClickListener(this);

    }

    TimePickerDialog.OnTimeSetListener ontimelistener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DecimalFormat formatter = new DecimalFormat("00");
            String hoursFormatted = formatter.format(hours);
            String minuteFormatted = formatter.format(minute);
            DisplayTime.setText("Time :" + hoursFormatted + ":" + minuteFormatted);
            time = hoursFormatted + ":" + minuteFormatted + ":00";
            time1 = hours + ":" + minute + ":00";
        }
    };

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
            int tempp = month + 1;

            DecimalFormat formatter = new DecimalFormat("00");
            String monthFormatted = formatter.format(tempp);
            String dayofmonthFormatted = formatter.format(dayofmonth);
            displayDate.setText("selected Date is " + year + "-" + tempp + "-" + dayofmonthFormatted);
            date = year + "-" + monthFormatted + "-" + dayofmonthFormatted;
            date1 = year + "-" + tempp + "-" + dayofmonth;
        }
    };

    TimePickerDialog.OnTimeSetListener ontimelistener3 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DecimalFormat formatter = new DecimalFormat("00");
            String hoursFormatted = formatter.format(hours);
            String minuteFormatted = formatter.format(minute);
            DisplayTimeEstimation.setText("Estimated Time :" + hoursFormatted + "hours and " + minuteFormatted + "minute");
            estimatedTime = hoursFormatted + ":" + minuteFormatted + ":00";
            estimatedTime1 = hours + ":" + minute + ":00";

        }
    };

    @Override
    public void onClick(View view) {
        if (view == btnMeetingTime) {
            new TimePickerDialog(EditMeetingNoteActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        } else if (view == btnDate) {
            new DatePickerDialog(EditMeetingNoteActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnEstimatedTime) {
            new TimePickerDialog(EditMeetingNoteActivity.this, ontimelistener3,
                    0, 0, true).show();
        } else if (view == btnUpdateNote) {
            String title = Title.getText().toString();
            String place = meeingPlace.getText().toString();
            String agenda = Agenda.getText().toString();
            int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton btnpriority = (RadioButton) findViewById(selectedId);
            String priority = btnpriority.getText().toString();

            if (date == null) {
                date = olddate;
                String[] s = date.split("-");
                date1 = Integer.valueOf(s[0]) + "-" + Integer.valueOf(s[1]) + "-" + Integer.valueOf(s[2]);
            }
            if (time == null) {
                time = oldtime;
                String[] s = time.split(":");
                time1 = Integer.valueOf(s[0]) + ":" + Integer.valueOf(s[1]) + ":00";
            }
            if (estimatedTime == null) {
                estimatedTime = oldEstimatedTime;
                //estimatedTime1=hours+":"+minute+":00";
                String[] s = estimatedTime.split(":");
                estimatedTime1 = Integer.valueOf(s[0]) + ":" + Integer.valueOf(s[1]) + ":00";
            }
            temp = date + " " + time;
            temp1 = date1 + " " + time1;

            if (title.equals(oldtitle) && place.equals(oldPlace) && agenda.equals(oldAgnda) && priority.equals(oldpriority) && date.equals(olddate)
                    && time.equals(oldtime) && estimatedTime.equals(oldEstimatedTime)) {
                Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

            } else {
                AlarmController alarmController = new AlarmController();
                NoteController noteController = new NoteController();
                noteController.UpdateMeetingNoteInLocalDB(title, place, agenda, temp, priority, estimatedTime, meetingNoteEntity.getNoteId());

                if (!date.equals(olddate) || !time.equals(oldtime) || !estimatedTime.equals(oldEstimatedTime)) {
                    alarmController.UpdateAlarm(temp1, estimatedTime1, meetingNoteEntity.getNoteId());

                }

            }

            Intent homeIntent = new Intent(MyApplication.getAppContext(),
                    HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.putExtra("status", "Welcome Back");
            homeIntent.putExtra("serviceType", "Back");
            startActivity(homeIntent);
        }

    }

}
