package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import controllers.NoteController;
import controllers.UserController;

public class OrdinaryNoteActivity extends ActionBarActivity {
    Button addNote;
    EditText noteContent;
    RadioGroup priorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_note);
        noteContent = (EditText) findViewById(R.id.EditTextNoteContent);
        priorityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPriority);
        addNote = (Button) findViewById(R.id.buttonAddNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
                RadioButton btnpriority = (RadioButton) findViewById(selectedId);
                String priority = btnpriority.getText().toString();
                String notecontent = noteContent.getText().toString();
                UserController userController = UserController.getInstance();
                boolean isConnected = userController.isNetworkConnected(getApplicationContext());
                NoteController noteController = new NoteController();
                if (!isConnected) {
                    noteController.addOrdinaryNoteInLoacalDB(notecontent, priority, false, 0);
                } else {

                    //Toast.makeText(getApplicationContext(), " Connected ", Toast.LENGTH_LONG).show();
                    noteController.addOrdinaryNote(notecontent, priority);
                }
            }
        });
    }

}
