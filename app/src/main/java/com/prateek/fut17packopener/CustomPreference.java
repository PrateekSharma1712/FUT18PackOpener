package com.prateek.fut17packopener;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prateek on 13/8/17.
 */

public class CustomPreference {

    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String COINS = "COINS";
    public static final String BEST_PACK = "BEST_PACK";
    public static final String TOTAL_PACK = "TOTAL_PACK";
    private static SharedPreferences _preferences;

    private static SharedPreferences getInstance(){
        if (_preferences == null) {
            _preferences = AppController.getInstance().getApplicationContext().getSharedPreferences("FUT17packopener", Context.MODE_PRIVATE);
        }
        return _preferences;
    }

    public static void updateTime(long value) {
        insert(TIMESTAMP, value);
    }

    public static void updateCoins(long value) {
        insert(COINS, value);
    }

    private static void insert(String key, long value) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private static long getLong(String key) {
        if (getInstance().contains(key)) {
            return getInstance().getLong(key, 0L);
        }
        return 0;
    }

    private static void insert(String key, int value) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static int getInt(String key) {
        if (getInstance().contains(key)) {
            return getInstance().getInt(key, 0);
        }
        return 0;
    }

    public static long getTime() {
        return getLong(TIMESTAMP);
    }

    public static long getCoins() {
        return getLong(COINS);
    }

    public static int getBestPackValue() {
        return getInt(BEST_PACK);
    }

    public static void updateBestPackValue(int totalPackValue) {
        insert(BEST_PACK, totalPackValue);
    }

    public static long getTotalPackValue() {
        return getLong(TOTAL_PACK);
    }

    public static void updateTotalPackValue(long totalPackValue) {
        insert(TOTAL_PACK, totalPackValue);
    }
}
