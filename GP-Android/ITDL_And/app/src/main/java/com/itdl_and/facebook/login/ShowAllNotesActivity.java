package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.NoteAdapter;

public class ShowAllNotesActivity extends AppCompatActivity {

    ListView listViewNotes;
    TextView tvNoNotes;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);
        intent = getIntent();
        String CurrentORHistory =intent.getStringExtra("HistoryORCurrent");
        Log.i("HistoryORCurrent", CurrentORHistory);

        listViewNotes = (ListView) findViewById(R.id.LvShowNotes);
        tvNoNotes = (TextView) findViewById(R.id.tvNoNotes);
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), CurrentORHistory);
        if (noteAdapter.getCount() == 0) {
            tvNoNotes.setText("No Notes To Show ");

        } else {
            tvNoNotes.setText("My Notes ");
            listViewNotes.setAdapter(noteAdapter);
        }

    }

}
