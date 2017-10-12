package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.os.Bundle;

import com.prateek.fut17packopener.R;

public class LauncherActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        login();
    }

    @Override
    protected void onConnected() {
        startActivity(new Intent(LauncherActivity.this, LandingActivity.class));
        finish();
    }
}
