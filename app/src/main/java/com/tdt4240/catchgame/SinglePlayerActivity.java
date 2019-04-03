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

public class SinglePlayerActivity extends AppCompatActivity {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainThread.setRunning(true);
    }

    //Called if game exit = yes or game over
    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(this, MenuActivity.class));
    }

}
