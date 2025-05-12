package com.example.harsh.ideatree;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.VideoView;
import android.widget.SeekBar;
import java.util.ArrayList;
import java.util.List;

//OfficialActivity Class

public class OfficialActivity extends AppCompatActivity
{
    final String TAG = "OfficialActivity";
    public TextView songName, collectionName, releaseDate, genre, singer, price, url;
    public TextView pricetag;
    public Song song, song1;
    private int position;
    VideoView preview;
    private SeekBar mProgress;
    private Button startMedia;
    private Button stopMedia;
    ImageView imagephoto;
    private Button button1;
    public List<Song> songList = new ArrayList<>();
    public List<Song> songList1 = new ArrayList<>();
    private ConstraintLayout CL1, CL2;
    NetworkInfo activeNetworkInfo;
    private MediaController mediacontroller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officialactivity);
        songName = (TextView) findViewById(R.id.positiontype);
        collectionName = (TextView) findViewById(R.id.positionName);

//        //seekbar
//        mProgress = (SeekBar) findViewById(R.id.seekBar);
//        startMedia = (Button) findViewById(R.id.button1);
//        stopMedia = (Button) findViewById(R.id.button2);
//
//        startMedia.setOnClickListener(click);
//        stopMedia.setOnClickListener(click);
//        mProgress.setOnSeekBarChangeListener(change);
//        mProgress.setEnabled(false);

        singer = (TextView) findViewById(R.id.partyType2);
        genre = (TextView) findViewById(R.id.genre);
        pricetag = (TextView) findViewById(R.id.priceID);
        //imageView = (ImageView) findViewById(R.id.);
        Intent intent = getIntent();
        imagephoto = (ImageView) findViewById(R.id.positionphoto);
        song = (Song) intent.getSerializableExtra("myinfo");
        position = intent.getIntExtra("position", 0);
        preview = (VideoView) findViewById(R.id.preview);
        button1 = (Button) findViewById(R.id.button);
    }



    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        return true;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "********************************************");
        Intent intent1 = getIntent();
        if (intent1.hasExtra("myinfo"))
        {
            song = (Song) intent1.getSerializableExtra("myinfo");
            Log.d("ImageUrl:", "1");
            //CL1 = (ConstraintLayout) findViewById(R.id.CL);
            Log.d("Songname", song.getSongname().toString());
            songName.setText(song.getSongname().toString());
            collectionName.setText(song.getCollectionname().toString());
            //releaseDate.setText(song.getReleaseDate().toString());
            //Picasso.with(this)
            //      .load(imageurl)
            //    .into(imageView);
            genre.setText(song.getPrimaryGenreName());
            singer.setText(song.getArtistName());
            pricetag.setText(song.getPrice() + " " + song.getCurrency());


//            Picasso Image
            if (isNetworkConnected())
            {
                if (song.getImage() != null)
                {
                    imagephoto.setTag(song.getImage());
                    new DownloadImageTask1(OfficialActivity.this).execute(imagephoto);
                }
            }


            if (isNetworkConnected())
            {
                //Media Player
                try
                {
                    mediacontroller = new MediaController(OfficialActivity.this);
                    mediacontroller.setAnchorView(preview);
                    String path = song.getPreviewUrl();
                    System.out.println("PreviewUrl from server--" + path);
                    Uri video = Uri.parse(path);
                    preview.setMediaController(mediacontroller);
                    preview.setKeepScreenOn(true);
                    preview.setVideoURI(video);
                    preview.start();
                }
                catch (Exception e)
                {
                    System.out.println("Video Exception--" + e.getMessage());
                    e.printStackTrace();
                }

            }

            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(OfficialActivity.this).create(); //Read Update
                    alertDialog.setTitle(song.getSongname() + " by " + song.getArtistName());
                    alertDialog.setMessage("You have to buy it from iTunes");

                    alertDialog.show();
                }

            });


        }
    }
}



