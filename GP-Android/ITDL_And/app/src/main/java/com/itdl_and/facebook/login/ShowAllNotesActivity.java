package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.NoteAdapter;

public class ShowAllNotesActivity extends ActionBarActivity {

    ListView listViewNotes ;
    TextView tvNoNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);

        listViewNotes = (ListView) findViewById(R.id.LvShowNotes);
        tvNoNotes= (TextView) findViewById(R.id.tvNoNotes);
        NoteAdapter noteAdapter =new NoteAdapter(getApplicationContext());
        if (noteAdapter.getCount()==0)
        {tvNoNotes.setText("No Notes To Show ");

        }
        else {
            //listViewNotes.setItemsCanFocus(false);
            listViewNotes.setAdapter(noteAdapter);
        }

    }

}
