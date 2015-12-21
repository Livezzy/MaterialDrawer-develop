package com.mikepenz.materialdrawer.app.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

	public final static String USER_PREFERENCES = "user_pref";
	PrefManager manager;

	public static void saveString(Context context, String key, String value) {
		SharedPreferences sharedpreferences = context.getSharedPreferences(
				USER_PREFERENCES, Context.MODE_PRIVATE);
		sharedpreferences.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sharedpreferences = context.getSharedPreferences(
				USER_PREFERENCES, Context.MODE_PRIVATE);
		return sharedpreferences.getString(key, null);
	}

	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sharedpreferences = context.getSharedPreferences(
				USER_PREFERENCES, Context.MODE_PRIVATE);
		return sharedpreferences.getBoolean(key, false);
	}

	public static void saveBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedpreferences = context.getSharedPreferences(
				USER_PREFERENCES, Context.MODE_PRIVATE);
		sharedpreferences.edit().putBoolean(key, value).commit();
	}
}
