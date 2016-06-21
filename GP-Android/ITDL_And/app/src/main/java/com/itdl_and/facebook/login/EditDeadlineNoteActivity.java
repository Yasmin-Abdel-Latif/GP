package com.itdl_and.facebook.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.text.DecimalFormat;
import java.util.Calendar;

import controllers.NoteController;
import controllers.UserController;
import model.DeadlineNoteEntity;

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
            if (progressValue==0){
                progressValue=oldprogress;
            }
            deadlinedate_time = date + " " + time;
            if (title.equals(olddeadlinetitle) && priority.equals(oldPriority) && date.equals(oldDatetimeparts[0])&&time.equals(oldDatetimeparts[1]) && oldprogress == progressValue) {
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

            }
        }
    }
}