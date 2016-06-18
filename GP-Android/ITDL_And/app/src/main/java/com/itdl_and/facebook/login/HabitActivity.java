package com.itdl_and.facebook.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.HabitNotesAdapter;
import controllers.MyApplication;
import controllers.NoteController;
import model.NoteEntity;
import model.OrdinaryNoteEntity;

public class HabitActivity extends ActionBarActivity {

    ListView listViewNotes;
    Button countinue;
    ArrayList<String> checkednotes = new ArrayList<String>();
    Bundle extras;
    ArrayList<NoteEntity> NotesGiven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        listViewNotes = (ListView) findViewById(R.id.listViewNotes);
        countinue = (Button) findViewById(R.id.btnContinue);

        Bundle bundleObject = getIntent().getExtras();
        NotesGiven = (ArrayList<NoteEntity>) bundleObject.getSerializable("Notes");

        final HabitNotesAdapter note_adapter = new HabitNotesAdapter(getApplicationContext(), NotesGiven);
        listViewNotes.setAdapter(note_adapter);
        countinue.setOnClickListener(new View.OnClickListener() {
                                         String result;

                                         @Override
                                         public void onClick(View v) {
                                             result = "Selected Notes are :";
                                             ArrayList<NoteEntity> chosenNotes = note_adapter.getCheckedNotes();
                                             for (NoteEntity c : chosenNotes) {
                                                 result += c.getNoteType() + " " + c.getServernoteId() + " " + c.getUserId();
                                                 NoteController nc = new NoteController();
                                                 nc.UpdateCreationDateAndIsDone(c.getServernoteId());
                                             }
                                             Intent homeIntent = new Intent(MyApplication.getAppContext(),
                                                     HomeActivity.class);
                                             homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                             MyApplication.getAppContext().startActivity(homeIntent);
                                             Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                         }
                                     }

        );
    }
}
