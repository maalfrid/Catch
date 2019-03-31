package com.tdt4240.catchgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

//Google Sign In feature
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;



public class MultiPlayerActivity extends AppCompatActivity implements
        View.OnClickListener {


    final static String TAG = "Catch";


    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;

    //Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        // Create the client used to sign in
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        //Set up a click listener for everything
        for(int id: CLICKABLE){
            findViewById(id).setOnClickListener(this);
        }
    }

    final static int[] CLICKABLE = {
            R.id.button_sign_in
    };


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_sign_in:
                // start the sign-in flow
                Log.d(TAG, "-----------Sign-in button clicked");
                startSignInIntent();
                break;
        }
    }

    /**
     * Start a sign in activity.  To properly handle the result, call tryHandleSignInResult from
     * your Activity's onActivityResult function
     */
    public void startSignInIntent(){
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);

            /*try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);
            } catch (ApiException apiException){
                String message = apiException.getMessage();
                if(message == null || message.isEmpty()){
                    message = getString(R.string.signin_other_error);
                }
            } */
        }
    }


}
