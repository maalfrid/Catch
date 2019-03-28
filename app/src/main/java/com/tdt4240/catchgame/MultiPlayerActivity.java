package com.tdt4240.catchgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//Google Sign In feature
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;





public class MultiPlayerActivity extends Activity implements
        View.OnClickListener {


    final static String TAG = "Catch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        System.out.println("Test");
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_sign_in:
                // start the sign-in flow
                Log.d(TAG, "------------------Sign-in button clicked");
                //startSignInIntent();
        }
    }
}
