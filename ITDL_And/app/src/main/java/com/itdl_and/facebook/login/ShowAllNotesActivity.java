package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.NoteAdapter;
import controllers.MyApplication;
import controllers.NoteController;
import model.NoteEntity;

public class ShowAllNotesActivity extends AppCompatActivity {

    ListView listViewNotes;
    TextView tvNoNotes;
    Intent intent;
    Button btH;

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

        btH = (Button) findViewById(R.id.btnBack);
        btH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MyApplication.getAppContext(),
                        HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                homeIntent.putExtra("status", "Welcome Back");
                homeIntent.putExtra("serviceType", "Back");
                startActivity(homeIntent);
            }
        });

    }

}
