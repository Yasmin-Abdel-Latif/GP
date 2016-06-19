package controllers;

import java.io.IOException;

/**
 * Created by Esraa on 17-Jun-16.
 */
public class Recomm_APIs {

    public String callGetOffersAPI(String userID) throws IOException {

        String returenedJson = "";


        String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getOfferService";
        String urlParameters = "userID=" + userID;
        returenedJson = Recomm_Connection.connect(serviceUrl, urlParameters, "POST",
                "application/x-www-form-urlencoded;charset=UTF-8");

        return returenedJson;
    }

    public String callGetNearestStoreAPI(String userID) throws IOException
    {
        String returenedJson = "";
        String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getNearestStoresToUserService";

        String urlParameters = "userID=" + userID;
        returenedJson = Recomm_Connection.connect(serviceUrl, urlParameters, "POST",
                "application/x-www-form-urlencoded;charset=UTF-8");

        return  returenedJson;
    }
    public String callUpdatePreferencesAPI(String userID, String twitterID) throws IOException
    {

        String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restRanking/updateUserPreferenceService";

        String urlParameters = "userID=" + userID + "&twitterID=" + twitterID;
        String returenedJson = Recomm_Connection.connect(serviceUrl, urlParameters, "POST",
                "application/x-www-form-urlencoded;charset=UTF-8");
        return  returenedJson;
    }
    public String updateUserPrefAfterConfirmation(String userID, String nrePrefJsonArrStr) throws IOException
    {

        String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/updatePrefAfterConfirmationService";

        String urlParameters = "userID=" + userID + "&userInitialWeightsSTR=" + nrePrefJsonArrStr;
        String returenedJson = Recomm_Connection.connect(serviceUrl, urlParameters, "POST",
                "application/x-www-form-urlencoded;charset=UTF-8");
        return  returenedJson;
    }



}
