package com.itdl_and.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import controllers.UserController;


public class MainFragment extends Fragment implements View.OnClickListener {

    private CallbackManager callbackManager;
    Button LogInButton,SignUpButton,GoToHomeButton;
    EditText Email ,Password;
    private TextView textView;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    public String fbUserProfile = "";
    public String fbUserPosts = "";

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            final Profile profile = Profile.getCurrentProfile();

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
                                String out = fbUserID
                                        + "\n" + fbUserEmail
                                        + "\n" + fbUserName
                                        + "\n" + fbUserBirthday
                                        + "\n" + fbUserCity
                                        + "\n" + fbUserGender;
                                UserController usercontrol = UserController.getInstance();
                                String userGAEID = usercontrol.Login(fbUserEmail, "");
                                textView.setText(fbUserName);

                                if(userGAEID == null)
                                {
                                    usercontrol.signUp(fbUserName, fbUserEmail, "", fbUserGender, fbUserCity, fbUserBirthday, "");
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            fbUserProfile = response.toString();
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile != null){
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
        LogInButton=(Button) view.findViewById(R.id.buttonLogIn);
        SignUpButton=(Button) view.findViewById(R.id.buttonSignUp);
        GoToHomeButton= (Button) view.findViewById(R.id.buttonGoToHome);
        Email= (EditText)view.findViewById(R.id.editTextEmail);
        Password=(EditText)view.findViewById(R.id.editTextPassword);

        Log.i("Cursor", "Empty");

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
        }
    }

    public void onClick(View v) {
        if (v == GoToHomeButton) {
            Intent i = new Intent(getActivity().getApplicationContext(), PreferencesActivity.class);
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
