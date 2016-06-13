package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.PereferenceAdapter;
import controllers.UserController;
import model.Category;

public class PerferencePercentegeActivity extends ActionBarActivity {
    Intent intent =getIntent();
    ListView listViewPereferences ;
    Bundle extras;
    UserController userController= UserController.getInstance();
   ArrayList<Category> pereferences ;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perference_percentege);
       // extras = getIntent().getExtras();
        listViewPereferences= (ListView) findViewById(R.id.listViewchosencategory);
        Submit= (Button) findViewById(R.id.btnSubmit);

        Bundle bundleObject = getIntent().getExtras();
         pereferences = (ArrayList<Category>) bundleObject.getSerializable("CheckedCategories");
        final PereferenceAdapter category_adapter = new PereferenceAdapter(getApplicationContext(),pereferences);
        listViewPereferences.setAdapter(category_adapter);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               userController.UserPreferences(pereferences);

            }
        });


    }

}
