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

import java.text.DecimalFormat;
import java.util.Calendar;

import controllers.UserController;

public class SignUpActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    Button Regest, btnDate;
    EditText Email, Password, TwitterAccount, userName, City;
    TextView DateOfBirth;
    String date;
    RadioGroup GenderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        Email = (EditText) findViewById(R.id.editTextmail);
        Password = (EditText) findViewById(R.id.editTextpassword);
        TwitterAccount = (EditText) findViewById(R.id.editTextTwitter);
        userName = (EditText) findViewById(R.id.TextUserName);
        DateOfBirth = (TextView) findViewById(R.id.textViewBirthDate);
        City = (EditText) findViewById(R.id.editTextCity);
        GenderRadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        Regest = (Button) findViewById(R.id.buttonRegister);
        btnDate = (Button) findViewById(R.id.btnBirthDate);
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
                String city = City.getText().toString();

                UserController usercontrol = UserController.getInstance();
                usercontrol.signUp(name, email, pas, gender, city, date, twitter);
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DatePickerDialog(SignUpActivity.this, listener,
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
