package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.PreferenceAdapter;
import controllers.UserController;
import model.Category;

public class PreferenceActivity extends ActionBarActivity {
    ListView listViewCategories;
    Button Submit;
    UserController userController = UserController.getInstance();
    ArrayList<Category> pereferences = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        listViewCategories = (ListView) findViewById(R.id.listViewCategories);
        Submit = (Button) findViewById(R.id.btnSubmit);

        PreferenceAdapter preferenceAdapter = new PreferenceAdapter(getApplicationContext());
        listViewCategories.setAdapter(preferenceAdapter);
        pereferences = preferenceAdapter.getMYCategories();

        Log.i("HELLO PRFACT", pereferences.size() + "");

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userController.UserPreferences(pereferences);

            }
        });

    }

}
