package com.itdl_and.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddNoteActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String noteTypes[] = {"Select note Type ", "Ordinary", "Meeting", "Deadline", "Shopping"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adpter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, noteTypes);

        spinner.setAdapter(adpter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int position = spinner.getSelectedItemPosition();
        String noteType = adapterView.getItemAtPosition(position).toString();

        if (noteType.equals("Ordinary")) {
            Intent intent = new Intent(getApplicationContext(), OrdinaryNoteActivity.class);
            startActivity(intent);

        } else if (noteType.equals("Meeting")) {
            Intent intent = new Intent(getApplicationContext(), MeetingNoteActivity.class);
            startActivity(intent);

        } else if (noteType.equals("Deadline")) {
            Intent intent = new Intent(getApplicationContext(), DeadlineActivity.class);
            startActivity(intent);
        } else if (noteType.equals("Shopping")) {
            Intent intent = new Intent(getApplicationContext(), ShoppingNoteActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

