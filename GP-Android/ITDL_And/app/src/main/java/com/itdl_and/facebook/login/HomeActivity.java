package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import controllers.AlarmService;
import controllers.MyApplication;
import controllers.NoteController;
import controllers.UserController;
import model.LocalDataBase;

public class HomeActivity extends ActionBarActivity implements View.OnClickListener {
    TextView ShowTextView;
    Button UpdateProfile, SignOut, addNoteBtn, btnShowNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NoteController noteController = new NoteController();
        noteController.StartService();


        Bundle extras = getIntent().getExtras();
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());

        String status = extras.getString("status");
        String serviceType = extras.getString("serviceType");

        String name = "", welcome = "Hello", text = "";

        if (serviceType.equals("LoginService")) {
            name = extras.getString("name");

            welcome = "Welcome " + name;
            String id = extras.getString("userId");
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
            String id = extras.getString("userId");
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
            String id = extras.getString("userId");
            text = status + " ... " + welcome + "  , your id is " + id;
        }

        ShowTextView = (TextView) findViewById(R.id.ShowText);
        ShowTextView.setText(text);
        UpdateProfile = (Button) findViewById(R.id.UpdateProfile);
        SignOut = (Button) findViewById(R.id.SignOut);
        addNoteBtn = (Button) findViewById(R.id.buttonAddNote);
        btnShowNotes = (Button) findViewById(R.id.buttonShowAllNotes);
        UpdateProfile.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);
        btnShowNotes.setOnClickListener(this);


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
        }
    }
}
