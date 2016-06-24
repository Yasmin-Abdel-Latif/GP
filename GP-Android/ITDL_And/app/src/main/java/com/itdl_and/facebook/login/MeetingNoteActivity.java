package com.itdl_and.facebook.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;

import controllers.AlarmController;
import controllers.NoteController;
import controllers.UserController;

public class MeetingNoteActivity extends AppCompatActivity implements View.OnClickListener {


    Calendar calendar =Calendar.getInstance();
    Button btnDate,btnEstimatedTime,btnAddNote,btnMeetingTime;
    EditText Title,Agenda,meeingPlace;
    TextView displayDate,DisplayTime,DisplayTimeEstimation;
    String date,time,estimatedTime,temp;
    String date1,time1,estimatedTime1,temp1;

    RadioGroup priorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_note);
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
        btnAddNote = (Button) findViewById(R.id.btnAddMeetingNote);

        btnAddNote.setOnClickListener(this);
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
            DisplayTime.setText("Time :"+hoursFormatted + ":" + minuteFormatted);
            time=hoursFormatted+":"+minuteFormatted+":00";
            time1=hours+":"+minute+":00";
        }
    };

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
            int tempp=month+1;
            DecimalFormat formatter = new DecimalFormat("00");
            String monthFormatted = formatter.format(tempp);
            String dayofmonthFormatted = formatter.format(dayofmonth);
            displayDate.setText("selected Date is "+year+"-"+tempp+"-"+dayofmonthFormatted);
            date =year+"-"+monthFormatted+"-"+dayofmonthFormatted;
            date1 =year+"-"+tempp+"-"+dayofmonth;
        }
    };

    TimePickerDialog.OnTimeSetListener ontimelistener3 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DecimalFormat formatter = new DecimalFormat("00");
            String hoursFormatted = formatter.format(hours);
            String minuteFormatted = formatter.format(minute);
            DisplayTimeEstimation.setText("Estimated Time :"+hoursFormatted + "hours and " + minuteFormatted+"minute");
            estimatedTime=hoursFormatted+":"+minuteFormatted+":00";
            estimatedTime1=hours+":"+minute+":00";
        }
    };

    @Override
    public void onClick(View view) {
        if (view == btnMeetingTime) {
            new TimePickerDialog(MeetingNoteActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        } else if (view == btnDate) {
            new DatePickerDialog(MeetingNoteActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnEstimatedTime) {
            new TimePickerDialog(MeetingNoteActivity.this, ontimelistener3,
                    0, 0, true).show();
        }  else if (view  == btnAddNote){
            String title=Title.getText().toString();
            String place=meeingPlace.getText().toString();
            String agenda =Agenda.getText().toString();
            int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton btnpriority = (RadioButton) findViewById(selectedId);
            String priority = btnpriority.getText().toString();
            UserController userController = UserController.getInstance();
            boolean isConnected=userController.isNetworkConnected(getApplicationContext());
            NoteController noteController = new NoteController();
            temp = date+" "+time;
            temp1 = date1+" "+time1;

            int id;
            if (!isConnected) {
                id= noteController.addMeetingNoteInLoacalDB(title,place,agenda,temp,priority,estimatedTime,false,0);

            }
            else{
                id=noteController.addMeetingNote(title, place, agenda, temp, priority, estimatedTime);
            }
            AlarmController alarmController =new AlarmController();
            alarmController.SetMeetingAlarm(temp1,estimatedTime1,id);
        }
    }
}
