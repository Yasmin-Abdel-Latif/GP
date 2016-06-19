package controllers;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

/**
 * Created by Esraa on 17-Jun-16.
 */
public class Recomm_Controller {


    public String getOffers(String userID) throws ExecutionException, InterruptedException {

        AsynchTaskGetOffers fetchOffers = new AsynchTaskGetOffers();
        String output = fetchOffers.execute(userID).get();

        return output;
    }
    public String getNearestStores(String userID) throws ExecutionException, InterruptedException {

        AsynchTaskGetNearestStores fetchStores = new AsynchTaskGetNearestStores();
        String output = fetchStores.execute(userID).get();

        return output;
    }


    public String updateUserPreferencesWeekly(String userID, String twitterID) throws ExecutionException, InterruptedException {

        AsynchTaskUpdatePreferences updatePreferences = new AsynchTaskUpdatePreferences();
        String output = updatePreferences.execute(userID, twitterID).get();

        return output;
    }
    public String enterConfirmedWeights(String userID, JSONArray userNewWeights) throws ExecutionException, InterruptedException {

        String userNewWeight = userNewWeights.toString();
        AsynchTaskEnterConfirmedWeights confirmedPreferences = new AsynchTaskEnterConfirmedWeights();
        String output = confirmedPreferences.execute(userID, userNewWeight).get();

        return output;
    }



}
