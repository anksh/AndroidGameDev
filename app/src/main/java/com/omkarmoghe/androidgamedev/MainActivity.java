package com.omkarmoghe.androidgamedev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    public static final int COUNT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();// Hides the action bar
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Button b = (Button) findViewById(R.id.button_id);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent gameScreen = new Intent(getApplicationContext(), GameActivity.class);
                startActivityForResult(gameScreen, COUNT_REQUEST);

            }
        });
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            String countNumber = data.getStringExtra("count");
            TextView countText = (TextView)findViewById(R.id.textView2);
            countText.setText("Nice job! You clicked " + countNumber + " circles!");
            countText.setVisibility(View.VISIBLE);
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