package com.itdl_and.facebook.login;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.Vector;

import model.Offer;
import model.Recomm_Parser;

public class GetOffersActivity extends ActionBarActivity {
    Vector<Offer> preferedOffers = new Vector<Offer>();
    JSONArray likesOffers = new JSONArray();

    TextView t;
    ListView offerListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_offers);
        t = (TextView)findViewById(R.id.offersOutput);

        String offersOutput = getIntent().getStringExtra("offersOutput");

        t.setText(offersOutput);
                Recomm_Parser parser = new Recomm_Parser();
        try {
           preferedOffers= parser.getParsedOffers(offersOutput);
            Toast.makeText(getApplicationContext(),preferedOffers.toString(),Toast.LENGTH_LONG).show();

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

    public void populateListView()
    {
        ArrayAdapter<Offer> offerArrayAdapter = new OfferListAdapter();
        offerListView = (ListView) findViewById(R.id.offers_list);
        offerListView.setAdapter(offerArrayAdapter);
    }


    private class OfferListAdapter extends ArrayAdapter<Offer>
    {
        public OfferListAdapter()
        {
            super(GetOffersActivity.this, R.layout.offer_list_view_item,preferedOffers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.offer_list_view_item,parent,false);

            }
            //find the store to work with
            Offer currentOffer = preferedOffers.get(position);
            //fill the view
            ImageView offerImg = (ImageView)itemView.findViewById(R.id.imgOffer);
           // offerImg.setImageResource(R.drawable.b);


           // ImageView offerImg = (ImageView)itemView.findViewById(R.id.imgStore);
            if(currentOffer.getCategory().equals("art and entertainment"))
            {

                offerImg.setImageResource(R.drawable.art);

            }
            else if(currentOffer.getCategory().equals("food and drink"))
            {

                offerImg.setImageResource(R.drawable.food);

            }
            else if(currentOffer.getCategory().equals("style and fashion"))
            {

                offerImg.setImageResource(R.drawable.style);

            }
            else if(currentOffer.getCategory().equals("technology and computing"))
            {

                offerImg.setImageResource(R.drawable.technology);
            }
            else if(currentOffer.getCategory().equals("religion and spirituality"))
            {

                offerImg.setImageResource(R.drawable.religion);
            }
            else if(currentOffer.getCategory().equals("hobbies and interests"))
            {

                offerImg.setImageResource(R.drawable.hobbies);

            }
            else if(currentOffer.getCategory().equals("health and fitness"))
            {

                offerImg.setImageResource(R.drawable.health);

            }
            else if(currentOffer.getCategory().equals("education"))
            {

                offerImg.setImageResource(R.drawable.education);

            }
            else if(currentOffer.getCategory().equals("sports"))
            {

                offerImg.setImageResource(R.drawable.sports);

            }
            else if(currentOffer.getCategory().equals("pets"))
            {
                offerImg.setImageResource(R.drawable.pets);

            }
            
            

            TextView offerContent = (TextView)itemView.findViewById(R.id.offerContent);
            offerContent.setText("Offer Content : " + currentOffer.getContent());

            TextView storeName = (TextView)itemView.findViewById(R.id.storeName);
            storeName.setText("Store Name : "+currentOffer.getStoreName());
           
           
            TextView storeAddress = (TextView)itemView.findViewById(R.id.storeAddress);
            storeAddress.setText("Store Address : "+currentOffer.getStoreAddress());

            TextView offerCategory = (TextView) itemView.findViewById(R.id.offerCategory);
            offerCategory.setText("Offer category: "+currentOffer.getCategory());
            return itemView;

            //return super.getView(position, convertView, parent);


        }
    }
}
