package com.udhamini;

/**
 * Created by brian on 04/Apr/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    private static final String USER_ID = "0";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL_ADDRESS = "email@domail.com";
    private static final String SCHOLARSHIP_POSITIONS = "sp";
    private static final String SCHOLARSHIP_ID = "0";
    private static final String KEY_USER_TOKEN = "xyz";

    private static final String SCHOLARSHIP_TITLE = "st";



    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String id){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_TOKEN, id);
        editor.apply();

        return true;
    }

    public static String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TOKEN, null);
    }


    public boolean writeUserId(String userId){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_ID, userId);
        editor.apply();

        return true;
    }

    public boolean writeScholarshipId(int id){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SCHOLARSHIP_ID, id);
        editor.apply();

        return true;
    }

    public boolean writeFirstName(String firstName){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(FIRST_NAME, firstName);
        editor.apply();

        return true;
    }

    public boolean writeLastName(String lastName){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LAST_NAME, lastName);
        editor.apply();

        return true;
    }

    public boolean writeEmailAddress(String emailAddress){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL_ADDRESS, emailAddress);
        editor.apply();

        return true;
    }

//    public boolean writeScholarshipPositions(String ScholarshipPositions){
//
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString(SCHOLARSHIP_POSITIONS, ScholarshipPositions);
//        editor.apply();
//
//        return true;
//    }

    public boolean writeScholarshipPositions(String[] array) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCHOLARSHIP_POSITIONS +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(SCHOLARSHIP_POSITIONS + "_" + i, array[i]);
        return editor.commit();
    }


    public static String getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null);
    }

    public static String getFirstName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIRST_NAME, null);
    }

    public static int getScholarshipId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SCHOLARSHIP_ID, 0);
    }

    public static String getLastName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_NAME, null);
    }

    public static String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_ADDRESS, null);
    }

    public static String getScholarshipPositions(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOLARSHIP_POSITIONS, null);
    }


    public static boolean writeScholarshipTitle(String scholarshipTitle){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SCHOLARSHIP_TITLE, scholarshipTitle);
        editor.apply();

        return true;
    }

    public static String getScholarshipTitle(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOLARSHIP_TITLE, null);
    }
}
