package com.itdl_and.facebook.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import controllers.AlarmReceiver;
import controllers.UserController;
import model.FacebookPost;


public class MainFragment extends Fragment implements View.OnClickListener {

    private CallbackManager callbackManager;
    Button LogInButton, SignUpButton, GoToHomeButton;
    EditText Email, Password;
    private static TextView textView;
    LoginResult log;
    private static final String TAG = "HELLO";

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    public AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;

    public void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, alarmIntent, 0);

        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 0);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), getInterval()/*AlarmManager.INTERVAL_DAY*/, pendingIntent);
    }

    private int getInterval() {
        int days = 1;
        int hours = 1;
        int minutes = 1;
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = days * hours * minutes * seconds * milliseconds;
        return repeatMS;
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            log = loginResult;

            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            JSONObject jsonUserData = response.getJSONObject();
                            try {
                                String fbUserID = jsonUserData.getString("id");
                                String fbUserEmail = jsonUserData.getString("email");
                                String fbUserName = jsonUserData.getString("name");
                                String fbUserBirthday = jsonUserData.getString("birthday");
                                String fbUserGender = jsonUserData.getString("gender");
                                String fbUserCity = jsonUserData.getJSONObject("location").getString("name");
                                JSONObject feeds = jsonUserData.getJSONObject("feed");
                                JSONArray data = feeds.getJSONArray("data");

                                ArrayList<FacebookPost> fbPosts = new ArrayList<FacebookPost>();

                                for (int i = 0; i < data.length(); i++) {
                                    String story = "";
                                    String message = "";
                                    if (data.getJSONObject(i).has("story"))
                                        story = data.getJSONObject(i).getString("story");
                                    if (data.getJSONObject(i).has("message"))
                                        message = data.getJSONObject(i).getString("message");
                                    String fbPostCreationDate = data.getJSONObject(i).getString("created_time");
                                    String fbPostContent = story + " " + message;
                                    String fbPostID = data.getJSONObject(i).getString("id");
                                    int read = 0;
                                    FacebookPost fbPost = new FacebookPost("", fbPostID, fbPostContent, fbPostCreationDate, read);
                                    fbPosts.add(fbPost);
                                }
                                String out = fbUserID
                                        + "\n" + fbUserEmail
                                        + "\n" + fbUserName
                                        + "\n" + fbUserBirthday
                                        + "\n" + fbUserCity
                                        + "\n" + fbUserGender;
                                Log.i("FFFFFFFFFFFFF", out);
                                UserController usercontrol = UserController.getInstance();
                                long userGAEID = usercontrol.fbLogin(fbUserEmail, "", fbPosts);
                                Log.i(TAG, String.valueOf(userGAEID));

                                if (userGAEID == 0) {
                                    usercontrol.fbSignUp(fbUserName, fbUserEmail, "", fbUserGender, fbUserCity, fbUserBirthday, "", fbPosts);
                                }

                                setAlarm();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,name,birthday,gender,location,friends{name,gender},feed");
            request.setParameters(parameters);
            request.executeAsync();

            /*Bundle params = new Bundle();
            params.putString("with", "location");
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/feed",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            textView.setText(response.toString());
                        }
                    }
            ).executeAsync();*/
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if (newProfile != null) {
                    textView.setText(newProfile.getName());
                }
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        textView = (TextView) view.findViewById(R.id.textView);
        LogInButton = (Button) view.findViewById(R.id.buttonLogIn);
        SignUpButton = (Button) view.findViewById(R.id.buttonSignUp);
        GoToHomeButton = (Button) view.findViewById(R.id.buttonGoToHome);
        Email = (EditText) view.findViewById(R.id.editTextEmail);
        Password = (EditText) view.findViewById(R.id.editTextPassword);

        LogInButton.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);
        GoToHomeButton.setOnClickListener(this);
        textView.setMovementMethod(new ScrollingMovementMethod());

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends", "user_posts", "user_location"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();

        if(profile != null){
            textView.setText(profile.getName());
            AccessToken accessToken = AccessToken.getCurrentAccessToken();

            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            JSONObject jsonUserData = response.getJSONObject();
                            try {
                                String fbUserID = jsonUserData.getString("id");
                                String fbUserEmail = jsonUserData.getString("email");
                                String fbUserName = jsonUserData.getString("name");
                                String fbUserBirthday = jsonUserData.getString("birthday");
                                String fbUserGender = jsonUserData.getString("gender");
                                String fbUserCity = jsonUserData.getJSONObject("location").getString("name");
                                JSONObject feeds = jsonUserData.getJSONObject("feed");
                                JSONArray data = feeds.getJSONArray("data");

                                ArrayList<FacebookPost> fbPosts = new ArrayList<FacebookPost>();

                                for(int i =0;i<data.length();i++) {
                                    String story = "";
                                    String message = "";
                                    if(data.getJSONObject(i).has("story"))
                                        story = data.getJSONObject(i).getString("story");
                                    if(data.getJSONObject(i).has("message"))
                                        message = data.getJSONObject(i).getString("message");
                                    String fbPostCreationDate = data.getJSONObject(i).getString("created_time");
                                    String fbPostContent = story + " " + message;
                                    String fbPostID = data.getJSONObject(i).getString("id");
                                    int read = 0;
                                    FacebookPost fbPost = new FacebookPost("", fbPostID, fbPostContent, fbPostCreationDate, read);
                                    fbPosts.add(fbPost);
                                }
                                String out = fbUserID
                                        + "\n" + fbUserEmail
                                        + "\n" + fbUserName
                                        + "\n" + fbUserBirthday
                                        + "\n" + fbUserCity
                                        + "\n" + fbUserGender;
                                UserController usercontrol = UserController.getInstance();
                                long userGAEID = usercontrol.fbLogin(fbUserEmail, "", fbPosts);


                                if(userGAEID != 0)
                                {
                                    Log.i(TAG, String.valueOf(userGAEID));
                                    setAlarm();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,name,birthday,gender,location,friends{name,gender},feed");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    public void onClick(View v) {
        if (v == GoToHomeButton) {
            Intent i = new Intent(getActivity().getApplicationContext(), PreferenceActivity.class);
            i.putExtra("name", "Sam");
            i.putExtra("userId", "5634472569470976");
            i.putExtra("serviceType", "LoginService");
            startActivity(i);
        } else if (v == SignUpButton) {
            Intent i = new Intent(getActivity(), SignUpActivity.class);
            startActivity(i);
        } else if (v == LogInButton) {

            String email = Email.getText().toString();
            String pas = Password.getText().toString();
            if (email.equals(""))
                Toast.makeText(getActivity().getApplicationContext(), "please Enter Your Email ", Toast.LENGTH_LONG).show();
            else if (pas.equals(""))
                Toast.makeText(getActivity().getApplicationContext(), "please Enter Your Password ", Toast.LENGTH_LONG).show();
            else {
                UserController usercontrol = UserController.getInstance();

                if (usercontrol.isNetworkConnected(getActivity().getApplicationContext()))
                    usercontrol.Login(email, pas);

                else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }

        }
    }
}
