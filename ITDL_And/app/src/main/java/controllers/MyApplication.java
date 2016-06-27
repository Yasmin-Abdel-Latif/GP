package controllers;

import android.content.Context;

import com.itdl_and.facebook.login.MainFragment;

public class MyApplication extends android.app.Application {


    private static Context context;
    private static UserController userController;
    private static String serviceLink;
    private static String serviceLink2;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        MyApplication.userController = UserController.getInstance();
        MyApplication.serviceLink = "http://5-dot-secondhelloworld-1221.appspot.com/";
        MyApplication.serviceLink2 = "http://8-dot-itdloffers.appspot.com/";
        MainFragment mf = new MainFragment();
        mf.setAlarm();
        mf.setAlarmUpdatePref();
        mf.setAlarmGetOfferBTN();
        mf.setAlarmGetNearestStoreBTN();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static UserController getUserController() {
        return MyApplication.userController;
    }

    public static String getServiceLink() {
        return MyApplication.serviceLink;
    }

    public static String getServiceLink2() {
        return MyApplication.serviceLink2;
    }
}
