package controllers;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.itdl_and.facebook.login.HabitActivity;
import com.itdl_and.facebook.login.HomeActivity;
import com.itdl_and.facebook.login.MainActivity;
import com.itdl_and.facebook.login.PreferenceActivity;
import com.itdl_and.facebook.login.R;
import com.itdl_and.facebook.login.ViewUserInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import model.Category;
import model.FacebookPost;
import model.LocalDataBase;
import model.NoteEntity;
import model.NoteParser;
import model.OrdinaryNoteEntity;
import model.UserEntity;

public class UserController {

    private static UserController userController;
    private static String twitterAccountActive;

    public static UserController getInstance() {

        FacebookSdk.sdkInitialize(MyApplication.getAppContext());
        if (userController == null) {
            userController = new UserController();
        }

        return userController;
    }

    public void signUp(String userName, String email, String password, String gender,
                       String city, String birth_date, String twitterAccount) {
        try {
            String result = new CallWebService().execute(MyApplication.getServiceLink() + "restNotes/RegestrationService", userName,
                    email, password, gender, city, birth_date, twitterAccount, "RegistrationService").get();

            Log.i("Cursor", "Signup");

            JSONObject object = new JSONObject(result);
            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }

            String id = object.get("userId").toString();

            Intent perefernce = new Intent(MyApplication.getAppContext(),
                    PreferenceActivity.class);
            perefernce.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            perefernce.putExtra("status", "Registered successfully");
            perefernce.putExtra("userId", id);
            perefernce.putExtra("serviceType", "RegistrationService");
            LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
            String twitterId = twitterAccount;
            if (localDataBase.GetUserID().length() > 0) {
                localDataBase.UpdateUserInfo(id, twitterId, email, password);
                Toast.makeText(MyApplication.getAppContext(), " ID Updated 1", Toast.LENGTH_LONG).show();
            } else {
                long localId = localDataBase.InsertUserInfo(id, twitterId, email, password);
                Toast.makeText(MyApplication.getAppContext(), " ID Inserted " + localId, Toast.LENGTH_LONG).show();
            }

            Log.d("user_id ", object.get("userId").toString());
            MyApplication.getAppContext().startActivity(perefernce);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void fbSignUp(String userName, String email, String password, String gender,
                         String city, String birth_date, String twitterAccount, ArrayList<FacebookPost> fbPosts) {
        try {
            String result = new CallWebService().execute(MyApplication.getServiceLink() + "restNotes/RegestrationService", userName,
                    email, password, gender, city, birth_date, twitterAccount, "RegistrationService").get();

            Log.i("Cursor", result);

            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }
            Log.i("Cursor", result);

            for (int i = 0; i < fbPosts.size(); i++) {
                fbPosts.get(i).setUserID(String.valueOf(object.getLong("userId")));
                addFBUserPost(fbPosts.get(i));
            }

            String id = object.get("userId").toString();

            Intent perefernce = new Intent(MyApplication.getAppContext(),
                    PreferenceActivity.class);
            perefernce.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            perefernce.putExtra("status", "Registered successfully");
            perefernce.putExtra("userId", id);
            perefernce.putExtra("serviceType", "RegistrationService");
            LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
            String twitterId = twitterAccount;
            if (localDataBase.GetUserID().length() > 0) {
                localDataBase.UpdateUserInfo(id, twitterId, email, password);
                Toast.makeText(MyApplication.getAppContext(), " ID Updated 1", Toast.LENGTH_LONG).show();
            } else {
                long localId = localDataBase.InsertUserInfo(id, twitterId, email, password);
                Toast.makeText(MyApplication.getAppContext(), " ID Inserted " + localId, Toast.LENGTH_LONG).show();
            }

            Log.d("user_id ", object.get("userId").toString());
            MyApplication.getAppContext().startActivity(perefernce);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String Login(String email, String password) {

        String userID = null;

        try {
            String result = new CallWebService().execute(
                    MyApplication.getServiceLink() + "restNotes/LoginService",
                    email, password, "LoginService").get();

            Log.i("Cursor", "Login");

            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.get("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();

                Intent homeIntent = new Intent(MyApplication.getAppContext(),
                        MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                MyApplication.getAppContext().startActivity(homeIntent);
                //return;
            } else {
                Intent homeIntent = new Intent(MyApplication.getAppContext(),
                        HomeActivity.class);
                //System.out.println("--- " + serviceType + "IN LOGIN " + object.getString("Status"));

                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                homeIntent.putExtra("status", "Logged in successfully");
                homeIntent.putExtra("name", object.getString("username"));
                homeIntent.putExtra("userId", object.get("userId").toString());
                homeIntent.putExtra("userEmail", email);
                homeIntent.putExtra("userPassword", password);
                homeIntent.putExtra("serviceType", "LoginService");

                userID = object.getString("userId");

                MyApplication.getAppContext().startActivity(homeIntent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public long fbLogin(String email, String password, ArrayList<FacebookPost> fbPosts) {

        long userID = 0;

        try {
            String result = new CallWebService().execute(
                    MyApplication.getServiceLink() + "restNotes/LoginService",
                    email, password, "LoginService").get();

            Log.i("Cursor", "FBLogin");

            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.get("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();

                Intent homeIntent = new Intent(MyApplication.getAppContext(),
                        MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                MyApplication.getAppContext().startActivity(homeIntent);
                return 0;
            } else {
                for (int i = 0; i < fbPosts.size(); i++) {
                    fbPosts.get(i).setUserID(String.valueOf(object.getLong("userId")));
                    addFBUserPost(fbPosts.get(i));
                }

                Intent homeIntent = new Intent(MyApplication.getAppContext(),
                        HomeActivity.class);
                //System.out.println("--- " + serviceType + "IN LOGIN " + object.getString("Status"));

                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                homeIntent.putExtra("status", "Logged in successfully");
                homeIntent.putExtra("name", object.getString("username"));
                homeIntent.putExtra("userId", String.valueOf(object.getLong("userId")));
                homeIntent.putExtra("userEmail", email);
                homeIntent.putExtra("userPassword", password);
                homeIntent.putExtra("serviceType", "LoginService");

                userID = object.getLong("userId");

                MyApplication.getAppContext().startActivity(homeIntent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public void addFBUserPost(FacebookPost fbPost) {
        try {
            String result = new CallWebService().execute(MyApplication.getServiceLink2() + "rest/AddUserPostService", fbPost.getUserID(),
                    fbPost.getPostID(), fbPost.getPostContent(), fbPost.getCreationDate(), "AddUserPostService").get();
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured while adding Post", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getFBUserPosts(String userID) throws ParseException {
        try {
            String result = new CallWebService().execute(MyApplication.getServiceLink2() + "rest/GetPostsService", userID, "GetPostsService").get();
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured while getting Posts", Toast.LENGTH_LONG).show();
                return;
            }

            ArrayList<FacebookPost> Posts = new ArrayList<FacebookPost>();
            if (object.get("Status").equals("OK")) {
                JSONArray jposts = object.getJSONArray("AllUserPosts");
                for (int i = 0; i < jposts.length(); i++) {
                    JSONObject jpost;
                    jpost = (JSONObject) jposts.get(i);
                    Posts.add(new FacebookPost(jpost.getString("userID"),
                            jpost.getString("postID"), jpost.getString("postContent"), jpost.getString("creationDate"), 1));
                }
                Map<String, ArrayList<FacebookPost>> allposts = new HashMap<String, ArrayList<FacebookPost>>();
                allposts.put("allposts", Posts);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NoteEntity> getAllNotes(String userID) {

        ArrayList<NoteEntity> notes = new ArrayList<NoteEntity>();
        try {
            Log.i("HELLO HI", userID);
            userID = userID.trim();
            String result = new CallWebService().execute(MyApplication.getServiceLink() + "restNotes/getAllNotesService", userID, "getAllNotesService").get();

            Log.i("HELLO", result);
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured while getting Notes", Toast.LENGTH_LONG).show();
                return notes;
            }

            NoteParser noteParser = new NoteParser();
            if (object.get("Status").equals("OK")) {
                JSONArray jnotes = object.getJSONArray("AllUserNotes");
                Log.i("HELLO", String.valueOf(jnotes.length()));
                Timestamp ts = new Timestamp(new Date().getTime());
                String curDay = (new SimpleDateFormat("EEEE", Locale.getDefault())).format(ts.getTime());
                for (int i = 0; i < jnotes.length(); i++) {
                    JSONObject jnote = (JSONObject) jnotes.get(i);
                    if (jnote.has("Ordinary")) {
                        Log.i("HELLO", jnote.getString("Ordinary"));
                        JSONObject note1 = new JSONObject(jnote.getString("Ordinary"));
                        NoteEntity note = noteParser.convertJsonObjToOrdinaryNoteObj(note1);
                        String day = (new SimpleDateFormat("EEEE", Locale.getDefault())).format(note.getNoteDateCreation().getTime());
                        int days = (int) ((note.getNoteDateCreation().getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
                        notes.add(note);
                        /*if ((day.equals(curDay)) && (days > 0) && (days <= 14)) {
                            notes.add(note);
                        }*/
                    }
                }
                return notes;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public UserEntity GetUserInformation(String userID) {
        try {
            String result = new CallWebService().execute(
                    MyApplication.getServiceLink() + "restNotes/GetUserInfoService",
                    userID, "GetUserInfoService").get();
            Log.i("HELLO", result);
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return null;
            }
            UserEntity ue = new UserEntity(object.get("username").toString(),
                    object.get("useremail").toString(), object.get("usertwiterAcc").toString(),
                    object.get("usergender").toString(), object.get("userpassword").toString(),
                    object.get("usercity").toString(), object.get("userbirthdate").toString());
            return ue;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void UpdateProfile(String userName, String email, String password, String gender,
                              String city, String birth_date, String twitterAccount) {
        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        try {
            String userId = "";
            String resultLD = ld.GetUserID();
            if (resultLD.trim().length() > 0) {
                JSONObject jsonObject = new JSONObject(resultLD);
                userId = jsonObject.getString("UserID");

            }
            String result = new CallWebService().execute(
                    MyApplication.getServiceLink() + "restNotes/UpdateProfileService", userId, userName,
                    email, password, gender, city, birth_date, twitterAccount, "UpdateProfileService").get();
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }
            Intent homeIntent = new Intent(MyApplication.getAppContext(),
                    HomeActivity.class);
            homeIntent.putExtra("status", "Profile Updated successfully");
            homeIntent.putExtra("serviceType", "UpdateProfileService");

            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getAppContext().startActivity(homeIntent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SignOut() {
        userController = null;
        if (LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();
        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        ld.LogoutUserID();
        Intent mainIntent = new Intent(MyApplication.getAppContext(),
                MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getAppContext().startActivity(mainIntent);
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = connectivity.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void UserPreferences(ArrayList<Category> Preferences, String email, String password, String id) {
        JSONArray jsonArrayPrfrnce = new JSONArray();
        for (int i = 0; i < Preferences.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("categoryName", Preferences.get(i).getCategoryName().trim().toLowerCase());
                object.put("initialWeight", Preferences.get(i).getCategoryPercentage());
                jsonArrayPrfrnce.put(object);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("category_Chosenn", String.valueOf(jsonArrayPrfrnce));

        LocalDataBase ld = new LocalDataBase(MyApplication.getAppContext());
        try {
            String userId = "";
            String resultLD = ld.GetUserID();
            if (resultLD.trim().length() > 0) {
                JSONObject jsonObject = new JSONObject(resultLD);
                userId = jsonObject.getString("UserID");

            }

            String result = new CallWebService().execute(MyApplication.getServiceLink() + "restNotes/enterInitialWeightsForOneUserService",
                    userId, jsonArrayPrfrnce.toString(), "enterInitialWeightsForOneUserService").get();

            Log.i("HELLO PRFRNC", result);

            if (!result.equals("added")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occurred", Toast.LENGTH_LONG).show();
                return;
            }

            Intent homeIntent = new Intent(MyApplication.getAppContext(),
                    HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.putExtra("status", "Registered successfully");
            homeIntent.putExtra("userId", id);
            homeIntent.putExtra("userEmail", email);
            homeIntent.putExtra("userPassword", password);
            homeIntent.putExtra("serviceType", "UserPreferenceService");
            Log.i("HELLO SIGNUP ID", id);
            MyApplication.getAppContext().startActivity(homeIntent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
