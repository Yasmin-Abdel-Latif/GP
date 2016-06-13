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

import java.util.Calendar;

import controllers.NoteController;
import controllers.UserController;
import model.MeetingNoteEntity;

public class EditMeetingNoteActivity extends ActionBarActivity implements View.OnClickListener {
    Calendar calendar =Calendar.getInstance();
    Button btnDate,btnEstimatedTime,btnUpdateNote;
    EditText Title,Agenda,meeingPlace;
    TextView displayDate,DisplayTime;
    String date,time,oldtitle,oldAgnda,oldPlace,olddate,oldtime,oldpriority;
    RadioGroup priorityRadioGroup;
    RadioButton RbtnHigh,RbtnMedium,RbtnLow;
    MeetingNoteEntity meetingNoteEntity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting_note);
        Intent intent =getIntent();
        meetingNoteEntity = (MeetingNoteEntity) intent.getSerializableExtra("note");
        priorityRadioGroup = (RadioGroup)findViewById(R.id.radioGroupPriority);

        Title = (EditText) findViewById(R.id.etMeetingTitle);
        Agenda = (EditText) findViewById(R.id.etMeetingAgenda);
        meeingPlace = (EditText) findViewById(R.id.etMeetingPlace);
        btnDate = (Button) findViewById(R.id.btnMeetingDate);
        displayDate= (TextView) findViewById(R.id.tvdisplayDate);
        DisplayTime= (TextView) findViewById(R.id.tvDisplayTime);
        btnEstimatedTime = (Button) findViewById(R.id.btnEstimatedTime);
        btnUpdateNote = (Button) findViewById(R.id.btnAddMeetingNote);
        RbtnHigh=(RadioButton) findViewById(R.id.radioHigh);
        RbtnMedium=(RadioButton) findViewById(R.id.radioMedium);
        RbtnLow=(RadioButton) findViewById(R.id.radioLow);

        oldtitle=meetingNoteEntity.getMeetingTitle();
        oldAgnda=meetingNoteEntity.getMeetingAgenda();
        oldPlace=meetingNoteEntity.getMeetingPlace();
        oldtime=meetingNoteEntity.getEstimatedTransportTime().toString();
        olddate=meetingNoteEntity.getMeetingNoteDate().toString();
        oldpriority=meetingNoteEntity.getNotePriority();

        if (meetingNoteEntity.getNotePriority().equals("High")){
            RbtnHigh.setChecked(true);
        }
        else if (meetingNoteEntity.getNotePriority().equals("Medium")){
            RbtnMedium.setChecked(true);

        }
        else if (meetingNoteEntity.getNotePriority().equals("Low")){
            RbtnLow.setChecked(true);
        }

        Title.setText(oldtitle);
        Agenda.setText(oldAgnda);
        meeingPlace.setText(oldPlace);
        displayDate.setText(olddate);
        DisplayTime.setText(oldtime);

        btnUpdateNote.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnEstimatedTime.setOnClickListener(this);

    }
    TimePickerDialog.OnTimeSetListener ontimelistener2 =new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minute) {
            DisplayTime.setText("Estimated Time :"+hours + "hours and " + minute+"minute");
            time=hours+":"+minute+":00";
        }
    };

    DatePickerDialog.OnDateSetListener listener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {

            displayDate.setText("selected Date is "+year+"-"+(month+1) +"-"+dayofmonth);
            date =year+"-"+(month+1)+"-"+dayofmonth;
        }
    };
    @Override
    public void onClick(View view) {
        if (view == btnEstimatedTime){
            new TimePickerDialog(EditMeetingNoteActivity.this, ontimelistener2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }
        else if (view==btnDate){
            new DatePickerDialog(EditMeetingNoteActivity.this, listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (view  == btnUpdateNote) {
            String title = Title.getText().toString();
            String place = meeingPlace.getText().toString();
            String agenda = Agenda.getText().toString();
            int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton btnpriority = (RadioButton) findViewById(selectedId);
            String priority = btnpriority.getText().toString();
            if (date==null){
                date=olddate;
            }
            if (time==null){
                time=oldtime;
            }
           if (title.equals(oldtitle) && place.equals(oldPlace) && agenda.equals(oldAgnda) && priority.equals(oldpriority) && date.equals(olddate)
                    && time.equals(oldtime)) {
                Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

            } else {
                UserController userController = UserController.getInstance();
                boolean isConnected = userController.isNetworkConnected(getApplicationContext());
                NoteController noteController = new NoteController();
                if (!isConnected) {
                    noteController.UpdateMeetingNoteInLocalDB(title, place, agenda, date, priority, time, meetingNoteEntity.getNoteId());
                } else {

                    noteController.UpdateMeetingNoteInLocalDB(title, place, agenda, date, priority, time, meetingNoteEntity.getNoteId());
                }

            }
        }

    }
}
