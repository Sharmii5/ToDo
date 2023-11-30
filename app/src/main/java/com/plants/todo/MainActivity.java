package com.plants.todo;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an intent to start the main activity
        final Intent i = new Intent(MainActivity.this, Tasks.class);

        // Use a handler to delay the start of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                startActivity(i);
                // Finish the splash activity so that it's not shown when back button is pressed
                finish();
            }
        }, 2000); // Delay for 2000 milliseconds (2 seconds)
    }
}