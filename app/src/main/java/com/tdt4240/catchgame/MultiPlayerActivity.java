package com.tdt4240.catchgame;

import android.app.Activity;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
//Quick game
import android.view.WindowManager;

//Google Sign In feature
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//Quick Game
import  com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.Games;



import  java.util.ArrayList;
import java.util.List;


public class MultiPlayerActivity extends AppCompatActivity implements
        View.OnClickListener {


    final static String TAG = "Catch";


    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;

    // Request codes for the UIs that we show with startActivityForResult:
    final static int RC_WAITING_ROOM = 10002;

    //Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient = null;

    // Client used to interact with the real time multi player system.
    private  RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;


    //Room Id where the currently active game is taking place; null if we're not playing
    String mRoomId = null;

    //Holds the configuration of the current room
    RoomConfig mRoomConfig;

    // Multiplayer?
    boolean mMultiplayer = false;

    // Participants in the game
    ArrayList<Participant> mParticipants = null;

    //My participant Id in the currently active game
    String myId = null;

    // Message buffer for sending messages
    byte[] mMsgBuf = new byte[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);
        findViewById(R.id.view_signIn).setVisibility(View.VISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Create the client used to sign in
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Set up a click listener for everything
        for(int id: CLICKABLEs){
            findViewById(id).setOnClickListener(this);
            System.out.println("-------Button Id--" + id);
        }
    }

    //Clickable buttons
    final static int[] CLICKABLEs = {
            R.id.button_sign_in, R.id.button_sign_out, R.id.button_quick_game
    };


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_sign_in:
                // start the sign-in flow
                Log.d(TAG, "-----------Sign-in button clicked");
                startSignInIntent();
                break;
            case R.id.button_sign_out:
                signOut();
                break;
            case R.id.button_quick_game:
                startQuickGame();
                break;
        }

    }

    // Quick game with auto match
    void startQuickGame(){
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS, MAX_OPPONENTS, 0);
        switchToScreen(R.id.screen_wait);
        keepScreenOn();

        mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
                .setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
                .setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
                .setAutoMatchCriteria(autoMatchCriteria)
                .build();
        //mRealTimeMultiplayerClient.create(mRoomConfig);
        Games.getRealTimeMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .create(mRoomConfig);
    }

    // Screens for multiplayer
    final static int[] SCREENS = {
            R.id.view_signIn, R.id.screen_wait
    };

    void switchToScreen(int screenId){
        // make the requested screen visible; hide all others
        for(int id : SCREENS) {
            findViewById(id).setVisibility(screenId == id ? View.VISIBLE: View.GONE);
        }

    }

    /**
     * Start a sign in activity.  To properly handle the result, call tryHandleSignInResult from
     * your Activity's onActivityResult function
     */
    public void startSignInIntent(){
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    private void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, " ----------Signed out successfully");
                                } else {
                                    Log.d(TAG, " ----------Signed out failed");
                                }
                            }
                        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);
            handleSignInResult(task);
        } else if (requestCode == RC_WAITING_ROOM) {
            // we got the result from the "waiting room" UI.
            if(resultCode == Activity.RESULT_OK) {
                // ready to start playing
                Log.d(TAG, "Starting game (waiting room returned OK).");
                //start game here
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //Signed in successfully, show authenticated UI.
            Log.d(TAG, "--------Account:"+ account);
        } catch(ApiException e){
            Log.w(TAG, "-----------signInResult:failed code=" + e.getStatusCode());
        }
    }

    private RoomStatusUpdateCallback mRoomStatusUpdateCallback = new RoomStatusUpdateCallback() {
        @Override
        public void onRoomConnecting(@Nullable Room room) {

        }

        @Override
        public void onRoomAutoMatching(@Nullable Room room) {

        }

        @Override
        public void onPeerInvitedToRoom(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerDeclined(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerJoined(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerLeft(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onConnectedToRoom(@Nullable Room room) {

        }

        @Override
        public void onDisconnectedFromRoom(@Nullable Room room) {

        }

        @Override
        public void onPeersConnected(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeersDisconnected(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onP2PConnected(@NonNull String s) {

        }

        @Override
        public void onP2PDisconnected(@NonNull String s) {

        }
    };
    private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {
        @Override
        public void onRoomCreated(int i, @Nullable Room room) {

        }

        @Override
        public void onJoinedRoom(int i, @Nullable Room room) {

        }

        @Override
        public void onLeftRoom(int i, @NonNull String s) {

        }

        @Override
        public void onRoomConnected(int i, @Nullable Room room) {

        }
    };

    private OnRealTimeMessageReceivedListener mOnRealTimeMessageReceivedListener = new OnRealTimeMessageReceivedListener() {
        @Override
        public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {

        }
    };



    //Keeps the screen turned on
    void keepScreenOn() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
