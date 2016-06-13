package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import controllers.UserController;

public class SignUpActivity extends ActionBarActivity {
    Button Regest;
    EditText Email ,Password,TwitterAccount,userName,DateOfBirth,City;
    RadioGroup GenderRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Email =(EditText) findViewById(R.id.editTextmail);
        Password= (EditText) findViewById(R.id.editTextpassword);
        TwitterAccount =(EditText) findViewById(R.id.editTextTwitter);
        userName=(EditText) findViewById(R.id.TextUserName);
        DateOfBirth =(EditText) findViewById(R.id.editTextBirthDate);
        City =(EditText) findViewById(R.id.editTextCity);
        GenderRadioGroup = (RadioGroup)findViewById(R.id.radioGroupGender);
        Regest = (Button)findViewById(R.id.buttonRegister);
        Regest.setOnClickListener(new View.OnClickListener() {

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
                usercontrol.signUp(name, email, pas, gender, city, date, twitter);

            }

        });
    }
}
