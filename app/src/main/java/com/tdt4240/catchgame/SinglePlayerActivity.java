package com.tdt4240.catchgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SinglePlayerActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private String difficulty;
    public boolean gameExit;
    public boolean gameOver;


    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");
        this.gameExit = false;
        this.gameOver = false;
    }

    public String getDifficulty(){
        return this.difficulty;
    }

    public void setGameExit(boolean b){
        this.gameExit = b;
    }

    public void setGameOver(boolean b){
        this.gameOver = b;
    }

    public boolean getGameExit(){
        return this.gameExit;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    @Override
    protected void onPause(){
        super.onPause();
        MainThread.setRunning(false);
        /*Toast.makeText(this,
                "Are you sure you want to quit the game?", Toast.LENGTH_LONG).show();*/
        //TODO:
        //Set new content view
        //Pop up: Do you want to quit?
        //Use input to quit game or resume
        /*boolean quitGame = false;
        if(quitGame){
            this.finish();
        }
        else{
            this.onResume();
        }*/
    }

    @Override
    protected void onResume() {
        MainThread.setRunning(true);
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Game exit: "+gameExit);
        System.out.println("Game over: "+gameOver);
        if(gameExit){
            popUpExit();
            //startActivity(new Intent(this, MenuActivity.class));
        }
        if(gameOver){
            popUpOver();
            //startActivity(new Intent(this, MenuActivity.class));
        }

    }

    public void popUpExit() {
        setContentView(R.layout.activity_single_player);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the game?");
        builder.setPositiveButton(R.string.cast_tracks_chooser_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("--------------Clicked OK");
            }
        });
        builder.setNegativeButton(R.string.cast_tracks_chooser_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("--------------Clicked CANCEL");
            }
        });
        AlertDialog dialog = builder.create();
    }

    public void popUpOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the game?");
        builder.setPositiveButton(R.string.cast_tracks_chooser_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("--------------Clicked OK");
            }
        });
        builder.setNegativeButton(R.string.cast_tracks_chooser_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("--------------Clicked CANCEL");
            }
        });
        AlertDialog dialog = builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
