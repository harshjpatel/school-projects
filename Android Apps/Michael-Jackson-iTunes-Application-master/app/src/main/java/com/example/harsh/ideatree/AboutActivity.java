package com.example.harsh.ideatree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.view.Window;
import android.view.*;
import android.widget.*;
import android.view.WindowManager;
import android.widget.EditText;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";
    public EditText text, text2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.about);

        /* adapt the image to the size of the display */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(),R.drawable.splash),size.x,size.y,true);

        /* fill the background ImageView with the resized image */
        ImageView iv_background = (ImageView) findViewById(R.id.background);
        iv_background.setImageBitmap(bmp);
        setContentView(R.layout.about);
        /* create a full screen window */
    }

    @Override
    public boolean onNavigateUp()
    {
        onBackPressed();
        return true;
    }

}
