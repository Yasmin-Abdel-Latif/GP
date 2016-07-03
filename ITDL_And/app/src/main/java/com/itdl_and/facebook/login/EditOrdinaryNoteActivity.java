package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.OrdinaryNoteEntity;

public class EditOrdinaryNoteActivity extends AppCompatActivity implements View.OnClickListener{
    Button UpdateNote;
    EditText noteContent;
    RadioGroup priorityRadioGroup;
    RadioButton RbtnHigh,RbtnMedium,RbtnLow;
    String oldContent,oldpriority;
    OrdinaryNoteEntity noteEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ordinary_note);

        Intent intent =getIntent();
        noteEntity = (OrdinaryNoteEntity) intent.getSerializableExtra("note");

        noteContent = (EditText) findViewById(R.id.EditTextNoteContent);
        UpdateNote= (Button) findViewById(R.id.buttonUpdateNote);
        priorityRadioGroup = (RadioGroup)findViewById(R.id.radioGroupPriority);
        RbtnHigh=(RadioButton) findViewById(R.id.radioHigh);
        RbtnMedium=(RadioButton) findViewById(R.id.radioMedium);
        RbtnLow=(RadioButton) findViewById(R.id.radioLow);
        oldContent=noteEntity.getNoteContent();
        noteContent.setText(oldContent);
        oldpriority=noteEntity.getNotePriority();
        if (noteEntity.getNotePriority().equals("High")){
            RbtnHigh.setChecked(true);
        }
        else if (noteEntity.getNotePriority().equals("Medium")){
            RbtnMedium.setChecked(true);

        }
        else if (noteEntity.getNotePriority().equals("Low")){
            RbtnLow.setChecked(true);
        }
UpdateNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
       RadioButton btnpriority = (RadioButton) findViewById(selectedId);
        String priority = btnpriority.getText().toString();
        String notecontent =noteContent.getText().toString();
        if (notecontent.equals(oldContent)&&priority.equals(oldpriority)){
            Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

        }
        else {
            UserController userController = UserController.getInstance();
            boolean isConnected=userController.isNetworkConnected(getApplicationContext());
            NoteController noteController = new NoteController();
            int noteid=noteEntity.getNoteId();

            if (!isConnected) {
              noteController.UpdateOrdinaryNoteInLocalDB(notecontent, priority, noteid);

            }
            else{
                noteController.UpdateOrdinaryNoteInLocalDB(notecontent, priority, noteid);

                //noteController.addShoppingNoteToServer(productname,priority,category);
                //noteController.AddShoppingNoteInLocalDB(productname, priority,category, true);
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
