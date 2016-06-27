package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import model.NearestStore;
import model.Offer;
import model.Recomm_Parser;

public class GetOffersActivity extends AppCompatActivity {
    Vector<Offer> preferedOffers = new Vector<Offer>();
    JSONArray likesOffers = new JSONArray();

    TextView t;
    ListView offerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_offers);
        t = (TextView) findViewById(R.id.offersOutput);

        String offersOutput = getIntent().getStringExtra("offersOutput");

        Recomm_Parser parser = new Recomm_Parser();
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);
            Location l = null;
            double myLat = 0.0;
            double myLng = 0.0;

            for (int i = 0; i < providers.size(); i++) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) {
                    myLat = l.getLatitude();
                    myLng = l.getLongitude();
                    break;
                }
            }
            preferedOffers = parser.getParsedOffers(offersOutput, myLat, myLng);
            ListView list = (ListView) findViewById(R.id.offers_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    Offer n = preferedOffers.get(position);
                    if (n != null) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("Lat", n.getStoreLat());
                        intent.putExtra("Lng", n.getStoreLong());
                        intent.putExtra("Address", n.getStoreAddress());
                        startActivity(intent);
                    }
                }
            });

            populateListView();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        t.setText("Some offers you may interested in");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_offers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateListView() {
        ArrayAdapter<Offer> offerArrayAdapter = new OfferListAdapter();
        offerListView = (ListView) findViewById(R.id.offers_list);
        offerListView.setAdapter(offerArrayAdapter);
    }


    private class OfferListAdapter extends ArrayAdapter<Offer> {
        public OfferListAdapter() {
            super(GetOffersActivity.this, R.layout.offer_list_view_item, preferedOffers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.offer_list_view_item, parent, false);

            }
            //find the store to work with
            Offer currentOffer = preferedOffers.get(position);
            //fill the view
            ImageView offerImg = (ImageView) itemView.findViewById(R.id.imgOffer);
            // offerImg.setImageResource(R.drawable.b);


            // ImageView offerImg = (ImageView)itemView.findViewById(R.id.imgStore);
            if (currentOffer.getCategory().equals("art and entertainment")) {

                offerImg.setImageResource(R.drawable.art);

            } else if (currentOffer.getCategory().equals("food and drink")) {

                offerImg.setImageResource(R.drawable.food);

            } else if (currentOffer.getCategory().equals("style and fashion")) {

                offerImg.setImageResource(R.drawable.style);

            } else if (currentOffer.getCategory().equals("technology and computing")) {

                offerImg.setImageResource(R.drawable.technology);
            } else if (currentOffer.getCategory().equals("religion and spirituality")) {

                offerImg.setImageResource(R.drawable.religion);
            } else if (currentOffer.getCategory().equals("hobbies and interests")) {

                offerImg.setImageResource(R.drawable.hobbies);

            } else if (currentOffer.getCategory().equals("health and fitness")) {

                offerImg.setImageResource(R.drawable.health);

            } else if (currentOffer.getCategory().equals("education")) {

                offerImg.setImageResource(R.drawable.education);

            } else if (currentOffer.getCategory().equals("sports")) {

                offerImg.setImageResource(R.drawable.sports);

            } else if (currentOffer.getCategory().equals("pets")) {
                offerImg.setImageResource(R.drawable.pets);

            }

            String outText = "Offer Content : " + currentOffer.getContent()
                    + "\nOffer category: " + currentOffer.getCategory()
                    + "\nStore Name : " + currentOffer.getStoreName()
                    + "\nStore Address : " + currentOffer.getStoreAddress();

            TextView offerContent = (TextView) itemView.findViewById(R.id.offerContent);
            offerContent.setText(outText);
            return itemView;
            //return super.getView(position, convertView, parent);

        }
    }
}
