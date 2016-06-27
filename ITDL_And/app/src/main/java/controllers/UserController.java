package controllers;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.itdl_and.facebook.login.HomeActivity;
import com.itdl_and.facebook.login.MainActivity;
import com.itdl_and.facebook.login.PreferenceActivity;
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
import model.UserEntity;

public class UserController {

    private static UserEntity currentActiveUser;
    private static UserController userController;
    private static long currentActiveUserID;

    public static long getCurrentUserID() {

        return currentActiveUserID;
    }

    public UserEntity getCurrentActiveUser() {

        return currentActiveUser;
    }

    public static UserController getInstance() {

        FacebookSdk.sdkInitialize(MyApplication.getAppContext());
        if (userController == null) {
            userController = new UserController();
        }

        return userController;
    }

    public void setCurrentActiveUser(UserEntity currentActiveUserSet) {
        currentActiveUser = currentActiveUserSet;
    }

    public void signUp(String userName, String email, String password, String gender,
                       String city, String birth_date, String twitterAccount) {
        try {
            String result = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/RegestrationService", userName,
                    email, password, gender, city, birth_date, twitterAccount, "RegistrationService").get();

            Log.i("Cursor", "Signup");

            JSONObject object = new JSONObject(result);
            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }
            currentActiveUser = UserEntity.createLoginUser(result);
            currentActiveUserID = currentActiveUser.getUserId();

            Intent perefernce = new Intent(MyApplication.getAppContext(),
                    PreferenceActivity.class);
            perefernce.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            perefernce.putExtra("status", "Registered successfully");
            perefernce.putExtra("userId", object.get("userId").toString());
            perefernce.putExtra("serviceType", "RegistrationService");

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
            String result = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/RegestrationService", userName,
                    email, password, gender, city, birth_date, twitterAccount, "RegistrationService").get();

            Log.i("Cursor", result);

            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }
            currentActiveUser = UserEntity.createLoginUser(result);
            currentActiveUserID = currentActiveUser.getUserId();
            Log.i("Cursor", result);

            for (int i = 0; i < fbPosts.size(); i++) {
                fbPosts.get(i).setUserID(String.valueOf(object.getLong("userId")));
                addFBUserPost(fbPosts.get(i));
            }

            Intent perefernce = new Intent(MyApplication.getAppContext(),
                    PreferenceActivity.class);
            perefernce.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            perefernce.putExtra("status", "Registered successfully");
            perefernce.putExtra("userId", object.getLong("userId"));
            perefernce.putExtra("serviceType", "RegistrationService");

            Log.i("user_id ", String.valueOf(object.getLong("userId")));
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
                    "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/LoginService",
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
                currentActiveUser = UserEntity.createLoginUser(result);
                currentActiveUserID = getCurrentActiveUser().getUserId();
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
                    "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/LoginService",
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
                currentActiveUser = UserEntity.createLoginUser(result);
                currentActiveUserID = getCurrentActiveUser().getUserId();

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
            String result = new CallWebService().execute("http://8-dot-itdloffers.appspot.com/rest/AddUserPostService", fbPost.getUserID(),
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

    public void getFBUserPosts(String userID) throws ParseException{
        try {
            String result = new CallWebService().execute("http://8-dot-itdloffers.appspot.com/rest/GetPostsService", userID, "GetPostsService").get();
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
            String result = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/getAllNotesService", userID, "getAllNotesService").get();

            Log.i("HELLO", result);
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured while getting Notes", Toast.LENGTH_LONG).show();
                return notes;
            }

            NoteParser noteParser = new NoteParser();
            if (object.get("Status").equals("OK")) {
                JSONArray jnotes = object.getJSONArray("AllUserNotes");
                Log.i("HELLO",String.valueOf(jnotes.length()));
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

    public void GetUserInformation() {
        Log.i("UserID" , String.valueOf(currentActiveUser.getUserId()));
        try {
            String result = new CallWebService().execute(
                    "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/GetUserInfoService",
                    String.valueOf(currentActiveUser.getUserId()), "GetUserInfoService").get();
            Log.i("HELLO" , result);
            JSONObject object = new JSONObject(result);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            }
            Intent viewIntent = new Intent(MyApplication.getAppContext(),
                    ViewUserInfoActivity.class);

            viewIntent.putExtra("username", object.get("username").toString());
            viewIntent.putExtra("useremail", object.get("useremail").toString());
            viewIntent.putExtra("userpassword", object.get("userpassword").toString());
            viewIntent.putExtra("usergender", object.get("usergender").toString());
            viewIntent.putExtra("usercity", object.get("usercity").toString());
            viewIntent.putExtra("usertwiterAcc", object.get("usertwiterAcc").toString());
            viewIntent.putExtra("userbirthdate", object.get("userbirthdate").toString());

            viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            MyApplication.getAppContext().startActivity(viewIntent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void UpdateProfile(String userName, String email, String password, String gender,
                              String city, String birth_date, String twitterAccount) {
        String userId = String.valueOf(currentActiveUser.getUserId());
        try {
            String result = new CallWebService().execute(
                    "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/UpdateProfileService", userId, userName,
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
        currentActiveUser = null;
        userController = null;
        if(LoginManager.getInstance() != null)
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

    public void UserPreferences(ArrayList<Category> Preferences) {
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

        try {

            String result = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/enterInitialWeightsForOneUserService",
                    String.valueOf(UserController.getCurrentUserID()), jsonArrayPrfrnce.toString(), "enterInitialWeightsForOneUserService").get();

            Log.i("HELLO PRFRNC", result);

            if (!result.equals("added")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occurred", Toast.LENGTH_LONG).show();
                return;
            }

            Intent homeIntent = new Intent(MyApplication.getAppContext(),
                    HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.putExtra("status", "Registered successfully");
            homeIntent.putExtra("userId", String.valueOf(currentActiveUserID));
            homeIntent.putExtra("userEmail", currentActiveUser.getUserEmail());
            homeIntent.putExtra("userPassword", currentActiveUser.getUserPassword());
            homeIntent.putExtra("serviceType", "UserPreferenceService");
            Log.i("HELLO SIGNUP ID", String.valueOf(currentActiveUserID));
            MyApplication.getAppContext().startActivity(homeIntent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
