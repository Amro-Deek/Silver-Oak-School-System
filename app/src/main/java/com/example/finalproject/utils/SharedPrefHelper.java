package com.example.finalproject.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private static final String PREF_NAME = "SchoolAppPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_USER_NAME = "user_name";

    private static SharedPrefHelper instance;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private SharedPrefHelper(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();

        // TEMP: Hardcoded teacher session for development (user_id = 3, Dr. Kamal)
        // Remove this when login is ready
        saveStaticTeacherSession();
    }

    public static synchronized SharedPrefHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefHelper(context);
        }
        return instance;
    }

    // ORIGINAL METHOD (Commented for now)
    /*
    public void saveUserSession(int userId, String role, String name) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_ROLE, role);
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }
    */

    // TEMP: Hardcoded static session for teacher
    private void saveStaticTeacherSession() {
        editor.putInt(KEY_USER_ID, 3); // Dr. Kamal
        editor.putString(KEY_USER_ROLE, "teacher");
        editor.putString(KEY_USER_NAME, "Dr. Kamal");
        editor.apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public String getUserRole() {
        return prefs.getString(KEY_USER_ROLE, null);
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, null);
    }

    public boolean isLoggedIn() {
        return getUserId() != -1;
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
