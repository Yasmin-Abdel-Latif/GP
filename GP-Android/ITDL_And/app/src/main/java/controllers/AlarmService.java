package controllers;


import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.itdl_and.facebook.login.HomeActivity;
import com.itdl_and.facebook.login.MainActivity;
import com.itdl_and.facebook.login.MainFragment;

import org.json.JSONException;
import org.json.JSONObject;

import model.LocalDataBase;


public class AlarmService extends IntentService
{
   	private static final String TAG = "HELLO";
	Context mContext;

   	public AlarmService() {
	      super("AlarmService");
	  }

   	@Override
   	public int onStartCommand(Intent intent, int flags, int startId) {
       	return super.onStartCommand(intent,flags,startId);
   	}

   	@Override
   	protected void onHandleIntent(Intent intent) {
	   	Log.i(TAG, "Alarm Service has started.");
		LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
		try{
			JSONObject jsonObject = new JSONObject(ld.GetUserID());
			String userID = jsonObject.getString("UserID");
			String userTwitterAccount = jsonObject.getString("Twitter_Account");
			Log.i(TAG, userID);
			Log.i(TAG, userTwitterAccount+ " Twitter");
			Log.i(TAG, String.valueOf(UserController.getAllNotes(userID)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

}