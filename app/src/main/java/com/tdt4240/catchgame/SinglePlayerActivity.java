package com.tdt4240.catchgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
            Toast.makeText(this,
                    "Quitting game", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuActivity.class));
        }
        if(gameOver){
            Toast.makeText(this,
                    "Game Over", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuActivity.class));
        }

    }
}
