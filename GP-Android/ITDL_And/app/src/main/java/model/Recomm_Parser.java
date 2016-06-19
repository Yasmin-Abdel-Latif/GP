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
        //JSONParser parser = new JSONParser();
        Vector<NearestStore> result = new Vector<NearestStore>();
        Log.i("EsraaSTR : ",nearestStoresSTR);
        JSONObject jsonRootObject = new JSONObject(nearestStoresSTR);
        JSONArray jsonArray = jsonRootObject.optJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) jsonArray.get(i);
            NearestStore nearStore = new NearestStore();

            nearStore.setStoreName(String.valueOf(obj.get("storeName")));
            nearStore.setUserProductToBuy(String.valueOf(obj.get("userProductToBuy")));
            nearStore.setLat(Double.parseDouble(obj.get("lat").toString()));
            nearStore.setLongt(Double.parseDouble(obj.get("longt").toString()));
            nearStore.setCategory(String.valueOf(obj.get("category")));



            result.add(nearStore);

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
            Log.i("storeName = ", offer.getStoreName()+"   "+offer.getStoreAddress() );
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
