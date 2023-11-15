package com.everton.pilltime.Authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

        private static final String PREF_NAME = "TokenPrefs";
        private static final String KEY_TOKEN = "token";

        private static final String KEY_ID = "id";

        private SharedPreferences preferences;

        public TokenManager(Context context) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        public void saveToken(String token) {
            preferences.edit().putString(KEY_TOKEN, token).apply();
        }


        public void saveId(Long id) {
            preferences.edit().putLong(KEY_ID, id).apply();
        }

        public Long getId(){
            return preferences.getLong(KEY_ID, 0);
        }

        public String getToken() {
            return preferences.getString(KEY_TOKEN, null);
        }

        public void clearToken() {
            preferences.edit().remove(KEY_TOKEN).apply();
        }

    }



