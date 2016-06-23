package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import controllers.AlarmService;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.LocalDataBase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView ShowTextView;
    Button UpdateProfile, SignOut, addNoteBtn, btnShowNotes, btnShowCloseOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NoteController noteController = new NoteController();
        noteController.StartService();


        Intent extras = getIntent();
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());

        String status = extras.getStringExtra("status");
        String serviceType = extras.getStringExtra("serviceType");

        String name = "", welcome = "Hello", text = "";

        if (serviceType.equals("LoginService")) {
            name = extras.getStringExtra("name");

            welcome = "Welcome " + name;
            String id = extras.getStringExtra("userId");
            text = status + " ... " + welcome + "  , your id is " + id;
            UserController uc = UserController.getInstance();
            String twitterId = uc.getCurrentActiveUser().getUserTwitterAccount();
            if(localDataBase.GetUserID().length() > 0)
            {
                localDataBase.UpdateUserInfo(id, twitterId);
                Toast.makeText(MyApplication.getAppContext(), " ID Updated 1", Toast.LENGTH_LONG).show();
            }
            else
            {
                long localId = localDataBase.InsertUserInfo(id, twitterId);
                Toast.makeText(MyApplication.getAppContext(), " ID Inserted " + localId, Toast.LENGTH_LONG).show();
            }

        } else if (serviceType.equals("UserPreferenceService")) {
            String id = extras.getStringExtra("userId");
            text = status + " ... " + welcome + "  , your id is " + id;
            UserController uc = UserController.getInstance();
            String twitterId = uc.getCurrentActiveUser().getUserTwitterAccount();
            if(localDataBase.GetUserID().length() > 0)
            {
                localDataBase.UpdateUserInfo(id, twitterId);
                Toast.makeText(MyApplication.getAppContext(), " ID Updated 1", Toast.LENGTH_LONG).show();
            }
            else
            {
                long localId = localDataBase.InsertUserInfo(id, twitterId);
                Toast.makeText(MyApplication.getAppContext(), " ID Inserted " + localId, Toast.LENGTH_LONG).show();
            }
        } else if (serviceType.equals("UpdateProfileService")) {
            text = status;
        } else if (serviceType.equals("UpdatePrefService")) {
            String id = extras.getStringExtra("userId");
            text = status + " ... " + welcome + "  , your id is " + id;
        }

        ShowTextView = (TextView) findViewById(R.id.ShowText);
        ShowTextView.setText(text);
        UpdateProfile = (Button) findViewById(R.id.UpdateProfile);
        SignOut = (Button) findViewById(R.id.SignOut);
        addNoteBtn = (Button) findViewById(R.id.buttonAddNote);
        btnShowNotes = (Button) findViewById(R.id.buttonShowAllNotes);
        btnShowCloseOffer = (Button) findViewById(R.id.buttonShowCloseOffer);
        UpdateProfile.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);
        btnShowNotes.setOnClickListener(this);
        btnShowCloseOffer.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == addNoteBtn) {
            Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
            startActivity(intent);
        } else if (view == SignOut) {
            UserController usercontrol = UserController.getInstance();
            usercontrol.SignOut();
        } else if (view == UpdateProfile) {
            UserController usercontrol = UserController.getInstance();
            usercontrol.GetUserInformation();
        } else if (view == btnShowNotes) {
            Intent intent = new Intent(getApplicationContext(), ShowAllNotesActivity.class);
            startActivity(intent);
        } else if (view == btnShowCloseOffer) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
    }
}
