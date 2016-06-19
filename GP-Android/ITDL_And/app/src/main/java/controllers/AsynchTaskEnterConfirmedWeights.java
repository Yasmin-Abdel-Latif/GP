package controllers;

        import android.annotation.SuppressLint;
        import android.os.AsyncTask;
        import android.util.Log;

        import java.io.IOException;

/**
 * Created by Esraa on 17-Jun-16.
 */
public class AsynchTaskEnterConfirmedWeights  extends AsyncTask<String, Void, String>
{
    protected void onPostExecute(String result) {

    }


    @SuppressLint("LongLogTag")
    protected String doInBackground(String... params)  {

        Recomm_APIs m = new Recomm_APIs();

        Log.i("Esraa params[0]  params[1]  = ", params[0] + "   " + params[1]);
        try {
            return m.updateUserPrefAfterConfirmation(params[0], params[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";

    }


}

