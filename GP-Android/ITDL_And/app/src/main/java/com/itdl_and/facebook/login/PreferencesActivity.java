package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.categoryAdapter;
import controllers.UserController;
import model.Category;

public class PreferencesActivity extends ActionBarActivity {
    ListView listViewCategories ;
    Button countinue;
    long userid = UserController.getInstance().getCurrentUserID();
    ArrayList<String> checkedcategories=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        listViewCategories = (ListView) findViewById(R.id.listViewCategories);
        countinue = (Button) findViewById(R.id.btnContinue);

        final categoryAdapter category_adapter = new categoryAdapter(getApplicationContext());
        listViewCategories.setAdapter(category_adapter);

            countinue.setOnClickListener(new View.OnClickListener()
            {  String result;
                @Override
                public void onClick (View v){
                    result = "Selected Product are :";
                    ArrayList<Category> chosenCategories= category_adapter.getCheckedCategories();
                    for (Category c : category_adapter.getCheckedCategories()) {
                          c.setUserId(userid);
                          result += "\n" + c.getCategoryName();

                    }
                    Intent intent = new Intent(getApplicationContext(),PerferencePercentegeActivity.class);
                   // intent.putExtra("CheckedCategories", chosenCategories);
                    Bundle bundleObject = new Bundle();
                    bundleObject.putSerializable("CheckedCategories", chosenCategories);
                    intent.putExtras(bundleObject);
                    startActivity(intent);
                   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                   // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                   // Log.v("Categoriesssss :" ,checkedcategories.toString());
            }
            }

            );
        }


    }
