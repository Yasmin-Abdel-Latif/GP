package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.DirectionsJSONParser;

public class MapsActivity extends FragmentActivity {

    GoogleMap map;
    TextView tvDistanceDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.i("Map Activity", "1");

        tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);
        Log.i("Map Activity", "2");

        // Initializing
        Log.i("Map Activity", "3");

        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Log.i("Map Activity", "4");

        // Getting Map for the SupportMapFragment
        map = fm.getMap();
        Log.i("Map Activity", "5");

        // Enable MyLocation Button in the Map
        map.setMyLocationEnabled(true);
        Log.i("Map Activity", "6");

        Intent intent = getIntent();
        double destLat = intent.getDoubleExtra("Lat", 0.0);
        double destLng = intent.getDoubleExtra("Lng", 0.0);
        String address = intent.getStringExtra("Address");
        LatLng destLatLng = new LatLng(destLat, destLng);
        Log.i("Map Activity", "Dest Lat: " + destLat + " Dest Lng: " + destLng);

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


        LatLng myLatLng = new LatLng(myLat, myLng);

        float zoomLevel = (float) 16.0; //This goes up to 21
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoomLevel));
        Log.i("Map Activity", "Lat: " + myLat + " Lng: " + myLng);

        // Creating MarkerOptions
        MarkerOptions options2 = new MarkerOptions();
        options2.position(myLatLng)
                .title("Current Location");
        options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(options2);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();
        options.position(destLatLng)
                .title(address);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        map.addMarker(options);

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(myLatLng, destLatLng);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if (result.size() < 1) {
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        distance = String.valueOf(point.get("distance"));
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = String.valueOf(point.get("duration"));
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            String textOut = "Distance:" + distance + ", Duration:" + duration;

            tvDistanceDuration.setText(textOut);

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }
}