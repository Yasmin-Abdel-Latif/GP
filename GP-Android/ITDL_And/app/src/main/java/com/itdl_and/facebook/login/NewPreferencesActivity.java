package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;
import java.util.concurrent.ExecutionException;

import controllers.Recomm_Controller;
import model.Recomm_Parser;
import model.UserInialWeights;

public class NewPreferencesActivity extends AppCompatActivity {

    Vector<UserInialWeights> newUserPref = new Vector<UserInialWeights>();
    TextView newPref;
    Button yesBtn;
    Button noBtn;
    TextView tt;
    public String userID;
    public String newPrefOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_preferences);

        newPrefOutput = getIntent().getStringExtra("updatePrefOutput");
        userID = getIntent().getStringExtra("userID");
        tt = (TextView) findViewById(R.id.res);
        // tt.setText(newPrefOutput);

        Recomm_Parser parser = new Recomm_Parser();
        String text = "";
//        try {
//            newUserPref = parser.getParsesUserInialWeights(newPrefOutput);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            Toast.makeText(getApplicationContext(), "updated successfully  " + newUserPref.size(), Toast.LENGTH_LONG).show();

            newUserPref = parser.getParsesUserInialWeights(newPrefOutput);
            for (int i = 0; i < newUserPref.size(); i++) {
                for (int j = 0; j < newUserPref.size(); j++) {
                    if (newUserPref.get(i).getInialWeight() > newUserPref.get(j).getInialWeight()) {
                        UserInialWeights u1 = new UserInialWeights();
                        u1 = newUserPref.get(i);
                        UserInialWeights u2 = new UserInialWeights();
                        u2 = newUserPref.get(j);
                        newUserPref.set(i, u2);
                        newUserPref.set(j, u1);

                    }
                }
            }

            for (int i = 0; i < newUserPref.size(); i++) {
                UserInialWeights u = new UserInialWeights();
                u = newUserPref.get(i);
                String num1Str = String.format("%.2g%n", u.getInialWeight());

                double num1 = Double.parseDouble(num1Str);

                text += u.getCategoryName() + "    " + num1 + "\n";

            }
            newPref = (TextView) findViewById(R.id.newPreferences);
            newPref.setText(text);
            yesBtn = (Button) findViewById(R.id.BTN_Yes);
            noBtn = (Button) findViewById(R.id.BTN_No);
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recomm_Controller callEnterNewPref = new Recomm_Controller();
                    String result = "";
                    try {
                        JSONObject jsonRootObject = new JSONObject(newPrefOutput);
                        JSONArray jsonArray = jsonRootObject.optJSONArray("result");
                        result = callEnterNewPref.enterConfirmedWeights(userID, jsonArray);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
                    Intent k = new Intent(NewPreferencesActivity.this, MainActivity.class);

                    startActivity(k);


                }
            });

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k = new Intent(NewPreferencesActivity.this, MainActivity.class);

                    startActivity(k);

                }
            });
            // } catch (JSONException e) {
            //  e.printStackTrace();
            //}


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_preferences, menu);
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
}
