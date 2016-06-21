package model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Vector;

/**
 * Created by Esraa on 17-Jun-16.
 */
public class Recomm_Parser {
    public Vector<NearestStore> getParsesNearestStores(String nearestStoresSTR) throws ParseException, JSONException {

        Vector<NearestStore> result = new Vector<NearestStore>();
        //  JSONParser parser = new JSONParser();
        JSONObject jsonRootObject = new JSONObject(nearestStoresSTR);
        // JSONObject jsonObjAllNearstStore = new JSONObject();
        //jsonObjAllNearstStore = (JSONObject) parser.parse(nearestStoresSTR.toString());

        int resultSize = Integer.parseInt(jsonRootObject.get("resultSize").toString());
        if (resultSize > 0) {
            JSONArray nearStoreArr = new JSONArray();
            nearStoreArr = (JSONArray) jsonRootObject.get("result");
            for (int i = 0; i < nearStoreArr.length(); i++) {
                JSONObject nStoreObj = new JSONObject();
                nStoreObj = (JSONObject) nearStoreArr.get(i);
                NearestStore n = new NearestStore();
                n.setLat(Double.parseDouble(nStoreObj.get("lat").toString()));
                n.setLat(Double.parseDouble(nStoreObj.get("longt").toString()));
                n.setStoreName(nStoreObj.get("storeName").toString());
                n.setStoreAddress(nStoreObj.get("storeAddress").toString());
                n.setStoreEmail(nStoreObj.get("storeEmail").toString());
                JSONArray listOfcat = new JSONArray();
                listOfcat = (JSONArray) nStoreObj.get("listOfStoreCategories");
                Vector<String> v1 = new Vector<String>();
                for (int j = 0; j < listOfcat.length(); j++) {
                    v1.add(listOfcat.get(j).toString());
                }
                JSONArray noteList = new JSONArray();
                noteList = (JSONArray) nStoreObj.get("listOfShoppingNote");

                Vector<String> v2 = new Vector<String>();
                for (int j = 0; j < noteList.length(); j++) {
                    v2.add(noteList.get(j).toString());
                }
                n.setListOfUserShoppingNotes(v2);
                n.setStoreCategories(v1);
                result.add(n);
            }

        } else {

        }


        return result;

    }

    public Vector<Offer> getParsedOffers(String offersSTR) throws ParseException, JSONException {
        JSONObject jsonRootObject = new JSONObject(offersSTR);
        JSONArray jsonArray = jsonRootObject.optJSONArray("result");
        Vector<Offer> result = new Vector<Offer>();

        for (int i = 0; i < jsonArray.length(); i++) {
            //JSONObject obj = new JSONObject();
            //obj = (JSONObject) jsonArray.getJSONObject(i);
            JSONObject obj = jsonArray.getJSONObject(i);
            Offer offer = new Offer();

            offer.setOfferID(String.valueOf(obj.get("offerID")));
            offer.setCategory(String.valueOf(obj.get("category")));
            offer.setContent(String.valueOf(obj.get("content")));
            offer.setStartDate(String.valueOf(obj.get("startDate")));
            offer.setEndDate(String.valueOf(obj.get("endDate")));
            offer.setStoreID(String.valueOf(obj.get("storeID")));
            offer.setStoreLat(Double.valueOf(obj.get("storeLat").toString()));
            offer.setStoreLong(Double.valueOf(obj.get("storeLong").toString()));
            offer.setJsonStoreEmail(String.valueOf(obj.get("jsonStoreEmail")));

            offer.setStoreName(String.valueOf(obj.get("storeName")));

            offer.setStoreAddress(String.valueOf(obj.get("storeAddress")));
            Log.i("storeName = ", offer.getStoreName() + "   " + offer.getStoreAddress());
            result.add(offer);

        }
        return result;
    }

    public Vector<UserInialWeights> getParsesUserInialWeights(String userInitialWeightsSTR) throws JSONException {
        JSONObject jsonRootObject = new JSONObject(userInitialWeightsSTR);
        JSONArray jsonArray = jsonRootObject.optJSONArray("result");
        Vector<UserInialWeights> result = new Vector<UserInialWeights>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) jsonArray.get(i);
            UserInialWeights uiw = new UserInialWeights();
            uiw.setCategoryID(obj.get("categoryID").toString());
            uiw.setCategoryName(obj.get("categoryName").toString());
            uiw.setInialWeight(Double.parseDouble(obj.get("inialWeight").toString()));
            uiw.setUserID(obj.get("userID").toString());
            uiw.setCategoryRecordID(obj.get("categoryRecordID").toString());

            result.add(uiw);
        }
        return result;
    }
}