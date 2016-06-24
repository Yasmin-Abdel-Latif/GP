package com.itdl_and.facebook.login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import controllers.UserController;

public class ViewUserInfoActivity extends AppCompatActivity {
    EditText Password, TwitterAccount, userName, City;
    RadioGroup GenderRadioGroup;
    RadioButton MaleRadio, FemaleRadio;
    TextView Email, DateOfBirth;
    String date;
    Button Update, btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_info);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        final String useremail = extras.getString("useremail");
        String userpassword = extras.getString("userpassword");
        String usergender = extras.getString("usergender");
        String usercity = extras.getString("usercity");
        String usertwiterAcc = extras.getString("usertwiterAcc");
        final String userbirthdate = extras.getString("userbirthdate");

        Email = (TextView) findViewById(R.id.editTextmail);
        Password = (EditText) findViewById(R.id.editTextpassword);
        TwitterAccount = (EditText) findViewById(R.id.editTextTwitter);
        userName = (EditText) findViewById(R.id.TextUserName);
        City = (EditText) findViewById(R.id.editTextCity);
        MaleRadio = (RadioButton) findViewById(R.id.radioMale);
        FemaleRadio = (RadioButton) findViewById(R.id.radioFemale);
        Update = (Button) findViewById(R.id.buttonupdate);
        GenderRadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        btnDate = (Button) findViewById(R.id.btnBirthDate);
        DateOfBirth = (TextView) findViewById(R.id.textViewBirthDate);

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
        DateOfBirth.setText("Current Date is "+userbirthdate);

        Update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int selectedId = GenderRadioGroup.getCheckedRadioButtonId();
                RadioButton genderbtn = (RadioButton) findViewById(selectedId);
                final String gender = genderbtn.getText().toString();
                Log.d("Gender", gender);
                String pas = Password.getText().toString();
                String twitter = TwitterAccount.getText().toString();
                String name = userName.getText().toString();
                String city = City.getText().toString();

                UserController usercontrol = UserController.getInstance();
                if (usercontrol.isNetworkConnected(getApplicationContext()))
                    usercontrol.UpdateProfile(name, useremail, pas, gender, city, date, twitter);
                else {
                    Toast.makeText(getApplicationContext(), " No Internet access  ", Toast.LENGTH_LONG).show();

                }
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DatePickerDialog(ViewUserInfoActivity.this, listener,
                        2000, 1, 1).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
            month = month + 1;
            DecimalFormat formatter = new DecimalFormat("00");
            String monthFormatted = formatter.format(month);
            String dayofmonthFormatted = formatter.format(dayofmonth);
            DateOfBirth.setText("selected Date is "+year+"-"+ monthFormatted+"-"+dayofmonthFormatted);
            date = year + "-" + monthFormatted + "-" + dayofmonthFormatted;
            Log.i("DDDDDDDDDDD", date);
        }
    };
}
