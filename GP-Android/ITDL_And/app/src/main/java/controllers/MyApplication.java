package controllers;

import android.content.Context;

import com.itdl_and.facebook.login.MainFragment;

public class MyApplication extends android.app.Application {


    private static Context context;
    private static UserController userController;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        MyApplication.userController = UserController.getInstance();
        MainFragment mf = new MainFragment();
        mf.setAlarm();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static UserController getUserController() {
        return MyApplication.userController;
    }
}
