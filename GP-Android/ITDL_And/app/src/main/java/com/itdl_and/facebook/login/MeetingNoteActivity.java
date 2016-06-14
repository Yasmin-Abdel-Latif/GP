package com.itdl_and.facebook.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.util.Calendar;

import controllers.NoteController;
import controllers.UserController;

public class MeetingNoteActivity extends ActionBarActivity implements View.OnClickListener {

    Calendar calendar =Calendar.getInstance();
    Button btnDate,btnEstimatedTime,btnAddNote,btnMeetingTime;
    EditText Title,Agenda,meeingPlace;
    TextView displayDate,DisplayTime;
    String date,time,estimatedTime,temp;
    RadioGroup priorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_not);
        priorityRadioGroup = (RadioGroup)findViewById(R.id.radioGroupPriority);

        Title = (EditText) findViewById(R.id.etMeetingTitle);
        Agenda = (EditText) findViewById(R.id.etMeetingAgenda);
        meeingPlace = (EditText) findViewById(R.id.etMeetingPlace);
        btnDate = (Button) findViewById(R.id.btnMeetingDate );
        displayDate= (TextView) findViewById(R.id.tvdisplayDate);
        DisplayTime= (TextView) findViewById(R.id.tvDisplayTime);
        btnMeetingTime = (Button) findViewById(R.id.btnMeetingTime);
        btnEstimatedTime = (Button) findViewById(R.id.btnEstimatedTime);
        btnAddNote = (Button) findViewById(R.id.btnAddMeetingNote);
        btnAddNote.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnMeetingTime.setOnClickListener(this);
        btnEstimatedTime.setOnClickListener(this);

    }
    TimePickerDialog.OnTimeSetListener ontimelistener2 =new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DisplayTime.setText("Estimated Time :"+hours + "hours and " + minute+"minute");
            time=twoDigits(hours)+":"+twoDigits(minute)+":00";
        }
    };

    TimePickerDialog.OnTimeSetListener ontimelistener3 =new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            estimatedTime=twoDigits(hours)+":"+twoDigits(minute)+":00";
        }
    };

    DatePickerDialog.OnDateSetListener listener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {

            displayDate.setText("selected Date is "+year+"-"+(month+1) +"-"+dayofmonth);
            date =twoDigits(year)+"-"+twoDigits(month+1)+"-"+twoDigits(dayofmonth);
        }
    };

    @Override
    public void onClick(View view) {
        if (view == btnMeetingTime){
            new TimePickerDialog(MeetingNoteActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }
        else if (view==btnDate){
            new DatePickerDialog(MeetingNoteActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (view==btnEstimatedTime){
            new TimePickerDialog(MeetingNoteActivity.this, ontimelistener3,
                    0, 0, true).show();
        }
        else if (view  == btnAddNote){
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

            if (!isConnected) {
                noteController.addMeetingNoteInLoacalDB(title,place,agenda,temp,priority,estimatedTime,false,0);
            }
            else{
                noteController.addMeetingNote(title, place, agenda, temp, priority, estimatedTime);
            }

        }
    }

    public String twoDigits(int num)
    {
        String numStr = "" + num;
        if(num < 10)
            numStr = "0" + numStr;
        return numStr;
    }
}

