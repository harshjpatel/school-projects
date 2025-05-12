package com.example.harsh.ideatree;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//InitialActivity
public class InitialActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent i = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}