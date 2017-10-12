package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.GoogleApiHelper;
import com.prateek.fut17packopener.R;

import lombok.NonNull;

/**
 * Created by prateek on 14/9/17.
 */

public abstract class BaseLoginActivity extends AppCompatActivity implements GoogleApiHelper.ConnectionListener {

    protected static int RC_SIGN_IN = 9001;

    protected boolean mResolvingConnectionFailure = false;
    protected boolean mAutoStartSignInflow = true;
    protected boolean mSignInClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                AppController.getGoogleApiHelper().getGoogleApiClient().connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.sign_in_failure);
            }
        }

    }

    protected void login() {
        AppController.getGoogleApiHelper().connect();
        AppController.getGoogleApiHelper().setConnectionListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(BaseLoginActivity.this,
                    AppController.getGoogleApiHelper().getGoogleApiClient(), connectionResult,
                    RC_SIGN_IN, "There was an issue with sign-in, please try again later.")) {
                mResolvingConnectionFailure = false;
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        AppController.getGoogleApiHelper().getGoogleApiClient().connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        onConnected();
    }

    protected abstract void onConnected();
}
