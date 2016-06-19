package controllers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Esraa on 17-Jun-16.
 */


public class AsynchTaskGetOffers extends AsyncTask<String, Void, String> {


    protected void onPostExecute(String result) {

    }

    @Override
    protected String doInBackground(String... params)  {

        Recomm_APIs m = new Recomm_APIs();

        Log.i("Esraa params[0] = ", params[0]);
        try {
            return m.callGetOffersAPI(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";

    }
}