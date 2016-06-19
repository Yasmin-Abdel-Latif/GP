package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.Vector;

import model.NearestStore;
import model.Recomm_Parser;

public class GetNearestStoresActivity extends ActionBarActivity {
    TextView t;
    ListView storesListView;
    Vector<NearestStore> storesVec = new Vector<NearestStore>();
    JSONArray likedStores = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_nearest_stores);

        t = (TextView) findViewById(R.id.storesOutput);
        String storesOutput = getIntent().getStringExtra("storesOutput");
        Recomm_Parser recomm_parser = new Recomm_Parser();
        try {
            storesVec = recomm_parser.getParsesNearestStores(storesOutput);
            populateListView();


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("ESRAA = ", storesOutput);
        t.setText("you may finish these shopping notes from some nearest stores to you");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_nearest_stores, menu);
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
        ArrayAdapter<NearestStore> storesArrayAdapter = new StoreListAdapter();
        storesListView = (ListView) findViewById(R.id.stores_list);
        storesListView.setAdapter(storesArrayAdapter);
    }

    private class StoreListAdapter extends ArrayAdapter<NearestStore> {
        public StoreListAdapter() {
            super(GetNearestStoresActivity.this, R.layout.store_list_view_item, storesVec);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.store_list_view_item, parent, false);

            }
            //find the store to work with
            final NearestStore currentStore = storesVec.get(position);
            //fill the view

            TextView storeEmail = (TextView) itemView.findViewById(R.id.storeEmail);
            storeEmail.setText(currentStore.getStoreName());
            ImageView storeImg = (ImageView) itemView.findViewById(R.id.imgStore);
            if (currentStore.getCategory().equals("art and entertainment")) {

                storeImg.setImageResource(R.drawable.art);

            } else if (currentStore.getCategory().equals("food and drink")) {

                storeImg.setImageResource(R.drawable.food);

            } else if (currentStore.getCategory().equals("style and fashion")) {

                storeImg.setImageResource(R.drawable.style);

            } else if (currentStore.getCategory().equals("technology and computing")) {

                storeImg.setImageResource(R.drawable.technology);
            } else if (currentStore.getCategory().equals("religion and spirituality")) {

                storeImg.setImageResource(R.drawable.religion);
            } else if (currentStore.getCategory().equals("hobbies and interests")) {

                storeImg.setImageResource(R.drawable.hobbies);

            } else if (currentStore.getCategory().equals("health and fitness")) {

                storeImg.setImageResource(R.drawable.health);

            } else if (currentStore.getCategory().equals("education")) {

                storeImg.setImageResource(R.drawable.education);

            } else if (currentStore.getCategory().equals("sports")) {

                storeImg.setImageResource(R.drawable.sports);

            } else if (currentStore.getCategory().equals("pets")) {
                storeImg.setImageResource(R.drawable.pets);

            }
            TextView productToBuy = (TextView) itemView.findViewById(R.id.userProductToBuy);
            productToBuy.setText("your note: \"" + currentStore.getUserProductToBuy() + "\"");

            TextView productCategory = (TextView) itemView.findViewById(R.id.productCategory);
            productCategory.setText("product category: " + currentStore.getCategory());

//            Button likeStoreBTN = (Button)itemView.findViewById(R.id.BTN_like_Store);
//            likeStoreBTN.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(),currentStore.getUserProductToBuy(),Toast.LENGTH_LONG ).show();
//                }
//            });
            return itemView;

            //return super.getView(position, convertView, parent);


        }

        private void registerClickCallback() {
            ListView list = (ListView) findViewById(R.id.stores_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    NearestStore n = storesVec.get(position);


                }
            });
        }


    }
}
