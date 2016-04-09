package model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import bg.ittalents.bookingtopia.controller.activities.LogInActivity;

public class UserSessionManager {

    SharedPreferences pref;
    Context context;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AndroidExamplePref";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_ID = "id";
    public static final String IS_USER = "user";


    public UserSessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(long id, String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing email in pref
        editor.putLong(KEY_ID, id);

        // Storing name in pref
        editor.putString(IS_USER, name);

        // commit changes
        editor.commit();
    }

    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to LogIn Activity
            Intent i = new Intent(context, LogInActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring LogIn Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user id
        user.put(KEY_ID, String.valueOf(pref.getLong(KEY_ID, 0)));

        // user name
        user.put(IS_USER, String.valueOf(pref.getString(IS_USER, null)));


        return user;
    }


    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to LogIn Activity
        Intent i = new Intent(context, LogInActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LogIn Activity
        context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, true);
    }

}