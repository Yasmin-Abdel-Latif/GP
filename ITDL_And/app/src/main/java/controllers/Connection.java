package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by samah on 29/04/2016.
 */
public class Connection {

     Connection(){
    }

    String connect(String Stringurl,String urlparamter){
        URL url;

        HttpURLConnection connection;
        try {
            url = new URL(Stringurl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(60000); // 60 Seconds
            connection.setReadTimeout(60000); // 60 Seconds

            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream());
            writer.write(urlparamter);
            writer.flush();
            String line, retJson = "";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                retJson += line;
            }
            return retJson;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
