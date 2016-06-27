package com.itdl_and.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import controllers.MyApplication;
import controllers.UserController;
import model.LocalDataBase;

public class LoadingActivity extends Activity {
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
                try {
                    String resultLD = ld.GetUserID();
                    if (resultLD.trim().length() > 0) {
                        JSONObject jsonObject = new JSONObject(resultLD);
                        String userEmail = jsonObject.getString("UserEmail");
                        String userPassword = jsonObject.getString("UserPassword");
                        int isLoggedin = jsonObject.getInt("IsLoggedIn");
                        if (isLoggedin == 1) {
                            UserController usercontrol = UserController.getInstance();
                            usercontrol.Login(userEmail, userPassword);
                        } else {
                            Intent intent1 = new Intent(LoadingActivity.this,
                                    MainActivity.class);
                            startActivity(intent1);
                        }
                    } else {
                        Intent intent1 = new Intent(LoadingActivity.this,
                                MainActivity.class);
                        startActivity(intent1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                finish();
            }
        }, SPLASH_TIME_OUT);

        /*new CountDownTimer(5000, 1000) {
            public void onFinish() {
                LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
                Intent intent;
                try {
                    String resultLD = ld.GetUserID();
                    if (resultLD.trim().length() > 0) {
                        JSONObject jsonObject = new JSONObject(resultLD);
                        String userID = jsonObject.getString("UserID");
                        int isLoggedin = jsonObject.getInt("IsLoggedIn");
                        if(isLoggedin == 1)
                        {
                            intent = new Intent(MyApplication.getAppContext(),
                                    HomeActivity.class);
                            intent.putExtra("serviceType","StayLoggedIn");
                            intent.putExtra("userId",userID);
                            intent.putExtra("status", "Welcome back");
                            MyApplication.getAppContext().startService(intent);
                        }
                    }

                    intent = new Intent(MyApplication.getAppContext(),
                            MainActivity.class);
                    MyApplication.getAppContext().startService(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();*/
    }
}
