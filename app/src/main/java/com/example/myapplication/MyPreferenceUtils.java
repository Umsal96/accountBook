package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.item.listItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyPreferenceUtils {

    private static final String PREF_NAME = "account_book";

    public void saveObject(Context context, String key, ArrayList<listItem> itemList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<listItem> getObject(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return null;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<listItem>>(){}.getType();
            return gson.fromJson(json, type);
        }
    }

    public void remove(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}

