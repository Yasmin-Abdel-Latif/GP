package com.itdl_and.facebook.login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import controllers.MyApplication;
import controllers.NoteController;
import controllers.Recomm_Controller;
import controllers.UserController;
import model.LocalDataBase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView ShowTextView;
    Button UpdateProfile, SignOut, addNoteBtn, btnShowNotes, btnShowCloseStore, btnHistoryNotes, btnShowOfferRecomm;

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
        btnShowNotes = (Button) findViewById(R.id.buttonShowCurrentNotes);
        btnHistoryNotes = (Button) findViewById(R.id.btnHistory);
        btnShowCloseStore = (Button) findViewById(R.id.buttonShowCloseOffer);
        btnShowOfferRecomm = (Button) findViewById(R.id.buttonShowOfferRecomendation);

        UpdateProfile.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);
        btnShowNotes.setOnClickListener(this);
        btnHistoryNotes.setOnClickListener(this);
        btnShowCloseStore.setOnClickListener(this);
        btnShowOfferRecomm.setOnClickListener(this);
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
        } else if (view ==btnShowNotes){
            Intent intent = new Intent(getApplicationContext(), ShowAllNotesActivity.class);
            intent.putExtra("HistoryORCurrent","Current");
            startActivity(intent);
        } else if (view ==btnHistoryNotes){
            Intent intent = new Intent(getApplicationContext(), ShowAllNotesActivity.class);
            intent.putExtra("HistoryORCurrent","History");
            startActivity(intent);
        } else if (view == btnShowCloseStore) {
            Recomm_Controller callGetNearestStoresABI = new Recomm_Controller();
            LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
            try {
                String resultLD = ld.GetUserID();
                if (resultLD.trim().length() > 0) {
                    JSONObject jsonObject = new JSONObject(resultLD);
                    String userID = jsonObject.getString("UserID");
                    String result = callGetNearestStoresABI.getNearestStores(userID);


                    JSONObject jsonRootObject = new JSONObject(result);
                    int resultSize = jsonRootObject.getInt("resultSize");
                    Log.i("HELLO NEAREST STORE", String.valueOf(resultSize));
                    if (resultSize > 0) {
                        Log.i("Stores Res API : ", result);
                        Intent mIntent = new Intent(MyApplication.getAppContext(), GetNearestStoresActivity.class);
                        mIntent.putExtra("storesOutput", result);
                        startActivity(mIntent);
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (view == btnShowOfferRecomm) {
            Recomm_Controller callGetOfferABI = new Recomm_Controller();
            LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
            try {
                String resultLD = ld.GetUserID();
                if (resultLD.trim().length() > 0) {
                    JSONObject jsonObject = new JSONObject(resultLD);
                    String userID = jsonObject.getString("UserID");
                    String result = callGetOfferABI.getOffers(userID);

                    JSONObject jsonRootObject = new JSONObject(result);
                    int resultSize = jsonRootObject.getInt("resultSize");
                    Log.i("HELLO GET OFFER", String.valueOf(resultSize));
                    if (resultSize > 0) {
                        Log.i("Offers Res API : ",result);
                        Intent mIntent = new Intent(MyApplication.getAppContext(), GetOffersActivity.class);
                        mIntent.putExtra("offersOutput", result);
                        startActivity(mIntent);
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
