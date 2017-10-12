package com.prateek.fut17packopener.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.prateek.fut17packopener.AppController;

/**
 * Created by prateek on 12/8/17.
 */

public class NetworkHandler {

    private static NetworkHandler _handler;

    public static NetworkHandler getInstance(){
        if (_handler == null) {
            _handler = new NetworkHandler();
        }
        return _handler;
    }

    public boolean isNetworkOn() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AppController.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
