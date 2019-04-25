package com.tdt4240.catchgame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
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
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MultiPlayerActivity extends AppCompatActivity implements
        View.OnClickListener {


    final static String TAG = "Catch";


    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;

    // Request codes for the UIs that we show with startActivityForResult:
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_INVITATION_INBOX = 10001;
    final static int RC_WAITING_ROOM = 10002;

    //Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient = null;

    // Client used to interact with the real time multi player system.
    private RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;


    //Room Id where the currently active game is taking place; null if we're not playing
    String mRoomId = null;

    //Holds the configuration of the current room
    RoomConfig mRoomConfig;

    // Multiplayer?
    boolean mMultiplayer = false;

    // Participants in the game
    ArrayList<Participant> mParticipants = null;

    //My participant Id in the currently active game
    String mMyId = null;

    // Message buffer for sending messages
    byte[] mMsgBuf = new byte[5];

    //temp
    int myScore = 0;

    // Music
    MediaPlayer backgroundMusic;
    MediaPlayer buttonSound;

    // Broadcast vars
    private int opponentScore;
    private int opponentLife;
    private int isGameOver;
    private int powerup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);
        findViewById(R.id.view_signIn).setVisibility(View.VISIBLE);

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        // Create the client used to sign in
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        //Set up a click listener for everything
        for (int id : CLICKABLEs) {
            findViewById(id).setOnClickListener(this);
            System.out.println("-------Button Id--" + id);
        }

        this.buttonSound = MediaPlayer.create(this, R.raw.buttonclick);
        this.buttonSound.setVolume(1, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mRealTimeMultiplayerClient==null){
            findViewById(R.id.button_quick_game).setVisibility(View.INVISIBLE);
            findViewById(R.id.button_sign_out).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.backgroundMusic.release();
        this.buttonSound.release();
        if(mRealTimeMultiplayerClient!=null){
            findViewById(R.id.button_sign_in).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);

        this.buttonSound = MediaPlayer.create(this, R.raw.buttonclick);
        this.buttonSound.setVolume(1, 1);

        // sign in silently when app resumes
        signInSilently();
        if(mRealTimeMultiplayerClient!=null){
            findViewById(R.id.button_sign_in).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(this, MenuActivity.class));
        this.backgroundMusic.release();
        this.buttonSound.release();
    }

    public void backgroundMusicOn() {
        this.backgroundMusic.start();
    }

    public void backgroundMusicOff() {
        this.backgroundMusic.pause();
    }


    public String getDifficulty() {
        return "easy";
    }

    public String getGametype() {
        return "multi";
    }

    //Clickable buttons
    final static int[] CLICKABLEs = {
            R.id.button_sign_in, R.id.button_sign_out, R.id.button_quick_game, R.id.btn_mpgGoBack
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                this.buttonSound.start();
                // start the sign-in flow
                Log.d(TAG, "-----------Sign-in button clicked");
                startSignInIntent();
                break;
            case R.id.button_sign_out:
                this.buttonSound.start();
                startActivity(new Intent(this, MenuActivity.class));
                signOut();
                break;
            case R.id.button_quick_game:
                this.buttonSound.start();
                startQuickGame();
                break;
            case R.id.btn_mpgGoBack:
                this.buttonSound.start();
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }

    }

    // Quick game with auto match
    void startQuickGame() {
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS, MAX_OPPONENTS, 0);
        switchToScreen(R.id.screen_wait);
        findViewById(R.id.btn_mpgGoBack).setVisibility(View.INVISIBLE);
        keepScreenOn();
        //resetGameVars();

        mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
                .setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
                .setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
                .setAutoMatchCriteria(autoMatchCriteria)
                .build();
        mRealTimeMultiplayerClient.create(mRoomConfig);
    }

    // Screens for multiplayer
    final static int[] SCREENS = {
            R.id.view_signIn, R.id.screen_wait
    };

    void switchToScreen(int screenId) {
        // make the requested screen visible; hide all others
        for (int id : SCREENS) {
            findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
        }

    }

    /**
     * Start a sign in activity.  To properly handle the result, call tryHandleSignInResult from
     * your Activity's onActivityResult function
     */
    public void startSignInIntent() {
        Log.d(TAG, "--------------Start sign in Intent");
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    public void signInSilently(){
        Log.d(TAG, "---------signInsilently()");
        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "------------signInsilently() success");
                            isSignedIn(true);
                            onConnected(task.getResult());
                        } else {
                            Log.d(TAG, "------------signInsilently() Faieled");
                            //onDisconnected();
                        }
                    }
                });
    }

    public void isSignedIn(boolean isSignedIn){
        if(isSignedIn == true){
            findViewById(R.id.button_sign_in).setVisibility(View.INVISIBLE);
            findViewById(R.id.button_quick_game).setVisibility(View.VISIBLE);
            findViewById(R.id.button_sign_out).setVisibility(View.VISIBLE);
        }
    }

    private void signOut() {
        Log.d(TAG, "----------signout()");

        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "--------signout(): success");
                        } else {
                            Log.d(TAG, "-------signout() failed");
                        }
                        mRealTimeMultiplayerClient = null;
                        //switchToScreen(R.id.view_main_menu);
                        //startActivity(new Intent(this, MenuActivity.class));
                        //onDisconnected();
                    }
                });
    }

    /* public void onDisconnected(){
        Log.d(TAG, "onDisconnected()");

        mRealTimeMultiplayerClient = null;
        //witchToScreen(R.id.view_main_menu);
    } */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "---------------- requestcode: " + requestCode);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);
            Log.d(TAG, "--------------- task " + task);
            // handleSignInResult(task);
            try {
                Log.d(TAG, "--------------- try statement");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "--------------- account " + account);
                findViewById(R.id.button_quick_game).setVisibility(View.VISIBLE);
                findViewById(R.id.button_sign_out).setVisibility(View.VISIBLE);
                onConnected(account);
            } catch (ApiException apiException) {
                String message = apiException.getMessage();
                if (message == null || message.isEmpty()) {
                    //message = getString(R.string.signin_other_error);
                    Log.d(TAG, "-------- Message from onActivityResult catch");
                }
            }
        } else if (requestCode == RC_SELECT_PLAYERS) {
            // we got the result from the "select players" UI - ready to create the room
            //handleSelectPlayersResult(resultCode, intent);
            Log.d(TAG, "-------------Select players request code");
        } else if (requestCode == RC_WAITING_ROOM) {
            // we got the result from the "waiting room" UI.
            if (resultCode == Activity.RESULT_OK) {
                // ready to start playing
                Log.d(TAG, "--------Starting game (waiting room returned OK).");
                //start game here startGame(true);
                startGame();
                //getResources().getIdentifier(fname, "raw", getPackageName());

                this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
                this.backgroundMusic.setLooping(true);
                this.backgroundMusic.setVolume(1, 1);
                this.backgroundMusic.start();
            }
        } else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM){
            leaveRoom();
        } else if (resultCode == Activity.RESULT_CANCELED){
            // Dialog was cancelled (user pressed back key, for instance). In our game,
            // this means leaving the room too. In more elaborate games, this could mean
            // something else (like minimizing the waiting room UI).
            leaveRoom();
        }
        Log.d(TAG, "------------if statement failed in onActivityResult");
        super.onActivityResult(requestCode, resultCode, intent);
    }


    // The currently signed in account, used to check the account has changed outside of this activity when resuming.
    GoogleSignInAccount mSignedInAccount = null;

    private String mPlayerId;

    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "---------onConnected(): Connected to Google Api's - Account " + googleSignInAccount);
        if (mSignedInAccount != googleSignInAccount) {
            mSignedInAccount = googleSignInAccount;
            Log.d(TAG, "------Crashing here.");
            //update the clients
            mRealTimeMultiplayerClient = Games.getRealTimeMultiplayerClient(this, googleSignInAccount);
            Log.d(TAG, "------------Real time multiple client " + mRealTimeMultiplayerClient);

            // get the players from the PlayerClient
            PlayersClient playersClient = Games.getPlayersClient(this, googleSignInAccount);
            playersClient.getCurrentPlayer()
                    .addOnSuccessListener(new OnSuccessListener<Player>() {
                        @Override
                        public void onSuccess(Player player) {
                            mPlayerId = player.getPlayerId();

                            //switchToMainScreen();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "-----------Player client failed");
                        }
                    });
        }
    }

    void showWaitingRoom(Room room) {
        final int MIN_PLAYERS = Integer.MAX_VALUE;
        mRealTimeMultiplayerClient.getWaitingRoomIntent(room, MIN_PLAYERS)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        //show waiting room UI
                        startActivityForResult(intent, RC_WAITING_ROOM);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "-----------show waiting room failed");
                    }
                });
    }

    void leaveRoom(){
        Log.d(TAG, "----------Leaving room");
        mRoomConfig = null;
        mRoomId = null;
        switchToScreen(R.id.view_play);
    }

    private RoomStatusUpdateCallback mRoomStatusUpdateCallback = new RoomStatusUpdateCallback() {
        // Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
        // is connected yet).
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
            Log.d(TAG, "-------onConnectedToRoom from RoomStatusUpdateCallback");

            //get participants and my ID:
            mParticipants = room.getParticipants();
            mMyId = room.getParticipantId(mPlayerId);

            // save room ID if its not initialized in onRoomCreated() so we can leave cleanly before the game starts.
            if (mRoomId == null) {
                mRoomId = room.getRoomId();
            }

            // print out the list of participants (for debug purposes)
            Log.d(TAG, "-----------------Room ID: " + mRoomId);
            Log.d(TAG, "-----------------My ID " + mMyId);
            Log.d(TAG, "-----------------<< CONNECTED TO ROOM>>");
            for (Participant p : mParticipants) {
                Log.d(TAG, "--------------mParticipants " + p.getDisplayName());

            }

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
        public void onRoomCreated(int statusCode, Room room) {
            Log.d(TAG, "------------RoomUpdateCallback onRoomCreate()");
            // save room ID so we can leave cleanly before the game starts.
            mRoomId = room.getRoomId();
            showWaitingRoom(room);

        }

        @Override
        public void onJoinedRoom(int statusCode, Room room) {
            Log.d(TAG, "--------------onJoined room");

        }

        @Override
        public void onLeftRoom(int i, @NonNull String s) {

        }

        @Override
        public void onRoomConnected(int i, @Nullable Room room) {

        }
    };

    void startGame() {
        setContentView(new GameView(this, this));
    }


    //Score SinglePlayer of other participants. We update this as we receive their scores from the network
    Map<String, Integer> mParticipantScore = new HashMap<>();

    private OnRealTimeMessageReceivedListener mOnRealTimeMessageReceivedListener = new OnRealTimeMessageReceivedListener() {
        @Override
        public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {
            byte[] buf = realTimeMessage.getMessageData();
            String sender = realTimeMessage.getSenderParticipantId();
            Log.d(TAG, "-----------Message received: " + (char) buf[0] + " Score : " + (int) buf[1] + "Lives : " + (int) buf[2] + " isGameover: " + (int) buf[3] + " Powerup: "+ (int) buf[4]);
            setOpponentScore(buf[1]);
            setOpponentLife(buf[2]);
            setIsGameOver(buf[3]);
            setPowerup(buf[4]);
        }
    };

    // Broadcast my score to everybody else
    void broadcast(int myScore, int myLives, int isGameOver, int powerup) {

        if (myLives == 0) {
            isGameOver = 1;
        }

        //First byte in message indicates whether it's final score or not
        mMsgBuf[0] = 'U';

        //Second byte is the score
        //mMsgBuf[1] = (byte) CoreGame.pScore;
        mMsgBuf[1] = (byte) myScore;
        mMsgBuf[2] = (byte) myLives;
        mMsgBuf[3] = (byte) isGameOver; //1: True, 0: False
        mMsgBuf[4] = (byte) powerup;

        //send to every participant
        for (Participant p : mParticipants) {
            if (p.getParticipantId().equals(mMyId)) {
                continue;
            }
            if (true) {
                // interim score
                mRealTimeMultiplayerClient.sendUnreliableMessage(mMsgBuf, mRoomId, p.getParticipantId());
            }
        }
    }

    public int getOpponentScore() { return this.opponentScore; }

    public void setOpponentScore(int score) { this.opponentScore = score; }

    public int getOpponentLife() { return this.opponentLife; }

    public void setOpponentLife(int lives) { this.opponentLife = lives; }

    public int getIsGameOver() { return this.isGameOver; }

    public void setIsGameOver(int isGameOver) { this.isGameOver = isGameOver; }

    public int getPowerup() { return this.powerup; }

    public void setPowerup(int powerup) { this.powerup= powerup; }


    //Keeps the screen turned on
    void keepScreenOn() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
