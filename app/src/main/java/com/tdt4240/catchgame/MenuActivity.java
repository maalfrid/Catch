package com.tdt4240.catchgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SwitchScreen(R.id.view_main_menu);

        // Click listener for all clickable elements
        for (int id : CLICKABLES) {
            findViewById(id).setOnClickListener(this);
        }
    }

    /*
     * Menu views and logic for switching screens
     */

    final static int[] CLICKABLES = { R.id.btn_play, R.id.btn_rules, R.id.btn_score,
            R.id.btn_settings, R.id.btn_background, R.id.btn_avatar, R.id.switch_sound,
            R.id.switch_background_music, R.id.btn_easy, R.id.btn_medium, R.id.btn_hard,
            R.id.btn_play_single, R.id.btn_play_multi, R.id.btn_goBack
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_play:
                SwitchScreen(R.id.view_play);
                break;
            case R.id.btn_rules:
                SwitchScreen(R.id.view_rules);
                break;
            case R.id.btn_score:
                SwitchScreen(R.id.view_highscore);
                break;
            case R.id.btn_settings:
                SwitchScreen(R.id.view_settings_menu);
                break;
            case R.id.btn_easy:
                startActivity(new Intent(v.getContext(), SinglePlayerActivity.class));
                break;
            case R.id.btn_medium:
                startActivity(new Intent(v.getContext(), SinglePlayerActivity.class));
                break;
            case R.id.btn_hard:
                startActivity(new Intent(v.getContext(), SinglePlayerActivity.class));
                break;
            case R.id.btn_play_single:
                SwitchScreen(R.id.view_play_single);
                break;
            case R.id.btn_play_multi:
                SwitchScreen(R.id.view_play_multi);
                startActivity(new Intent(v.getContext(), MultiPlayerActivity.class));
                break;
            /*case R.id.button_sign_in:
                startActivity(new Intent(v.getContext(), MultiPlayerActivity.class));
                break;*/
            case R.id.btn_goBack:
                if(mCurScreen==R.id.view_play) {
                    SwitchScreen(R.id.view_main_menu);
                    break;
                }
                SwitchScreen(mLastScreen);
                break;
        }
    }

    /*
    * Menu views and logic for switching screens
    */

    final static int[] SCREENS = {
            R.id.view_main_menu, R.id.view_highscore, R.id.view_play, R.id.view_play_multi,
            R.id.view_play_single, R.id.view_rules, R.id.view_settings_menu, R.id.btn_goBack
    };
    int mCurScreen = -1;
    int mLastScreen = -1;

    public void SwitchScreen(int screenId) {
        for (int id : SCREENS) {
            findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
        }
        if(screenId != R.id.view_main_menu) findViewById(R.id.btn_goBack).setVisibility(View.VISIBLE);

        mLastScreen = mCurScreen;
        mCurScreen = screenId;
    }


}
