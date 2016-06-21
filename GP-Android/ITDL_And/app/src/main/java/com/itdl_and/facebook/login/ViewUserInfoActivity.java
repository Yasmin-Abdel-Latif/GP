package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import controllers.UserController;

public class ViewUserInfoActivity extends AppCompatActivity {
    EditText Email, Password, TwitterAccount, userName, DateOfBirth, City;
    RadioGroup GenderRadioGroup;
    RadioButton MaleRadio, FemaleRadio;
    Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_info);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        String useremail = extras.getString("useremail");
        String userpassword = extras.getString("userpassword");
        String usergender = extras.getString("usergender");
        String usercity = extras.getString("usercity");
        String usertwiterAcc = extras.getString("usertwiterAcc");
        String userbirthdate = extras.getString("userbirthdate");

        Email = (EditText) findViewById(R.id.editTextmail);
        Password = (EditText) findViewById(R.id.editTextpassword);
        TwitterAccount = (EditText) findViewById(R.id.editTextTwitter);
        userName = (EditText) findViewById(R.id.TextUserName);
        DateOfBirth = (EditText) findViewById(R.id.editTextBirthDate);
        City = (EditText) findViewById(R.id.editTextCity);
        MaleRadio = (RadioButton) findViewById(R.id.radioMale);
        FemaleRadio = (RadioButton) findViewById(R.id.radioFemale);
        Update = (Button) findViewById(R.id.buttonupdate);
        GenderRadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);

        if (usergender.equals("Male")) {
            MaleRadio.setChecked(true);
        } else {
            FemaleRadio.setChecked(true);
        }


        Email.setText(useremail);
        Password.setText(userpassword);
        userName.setText(username);
        TwitterAccount.setText(usertwiterAcc);
        City.setText(usercity);
        DateOfBirth.setText(userbirthdate);

        Update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int selectedId = GenderRadioGroup.getCheckedRadioButtonId();
                RadioButton genderbtn = (RadioButton) findViewById(selectedId);
                final String gender = genderbtn.getText().toString();
                Log.d("Gender", gender);
                String email = Email.getText().toString();
                String pas = Password.getText().toString();
                String twitter = TwitterAccount.getText().toString();
                String name = userName.getText().toString();
                String date = DateOfBirth.getText().toString();
                String city = City.getText().toString();

                UserController usercontrol = UserController.getInstance();
                if (usercontrol.isNetworkConnected(getApplicationContext()))
                    usercontrol.UpdateProfile(name, email, pas, gender, city, date, twitter);
                else {
                    Toast.makeText(getApplicationContext(), " No Internet access  ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
