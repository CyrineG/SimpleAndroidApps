package com.parsingjson.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;

    public Prefs(Activity act){
        sharedPreferences = act.getPreferences(act.MODE_PRIVATE);
    }

    public void setSearch(String search){
        sharedPreferences.edit().putString("search", search).commit();

    }

    public String getSearch(){
        return sharedPreferences.getString("search", "Batman");
    }
}
