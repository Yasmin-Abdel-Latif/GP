package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import model.NearestStore;
import model.Recomm_Parser;

public class GetNearestStoresActivity extends AppCompatActivity {
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
            storesVec = recomm_parser.getParsesNearestStores(storesOutput, myLat, myLng);

            ListView list = (ListView) findViewById(R.id.stores_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    NearestStore n = storesVec.get(position);
                    if (n != null) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("Lat", n.getLat());
                        intent.putExtra("Lng", n.getLongt());
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

            String storeInfo = currentStore.toString();

            TextView storeInformation = (TextView) itemView.findViewById(R.id.storeInformation);
            storeInformation.setText(storeInfo);
            ImageView storeImg = (ImageView) itemView.findViewById(R.id.imgStore);

            storeImg.setImageResource(R.drawable.store);
            return itemView;


        }
    }
}