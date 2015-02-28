package com.omkarmoghe.androidgamedev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class GameActivity extends ActionBarActivity {
    private GameView gameView;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Find out how to get this to stop the thread too
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            gameView.kill();
            Intent goHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goHome);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameView = new GameView(GameActivity.this);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();// Hides the action bar
        setContentView(gameView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}