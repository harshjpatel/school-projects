package com.example.harsh.ideatree;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Locale;

//MainActivity- shows Listing of Songs in Application.
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "MainActivity";
    public TextView songname, collectionname, pricename;
    private static final int B_REQ = 1;
    private static final int NEW = 1;
    private static final int EDIT = 2;
    private SongAdapter songAdapter;
    private RecyclerView recyclerView;
    private static ArrayList<Song> songArrayList = new ArrayList<>();
    private Song song;
    NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int n = 20;
        if (isNetworkConnected())
        {
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            {
                new SongsDownloader(MainActivity.this).execute();
            }
        }
        songAdapter = new SongAdapter(this, songArrayList, this);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(songAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Network Connection
    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        return true;
    }

    //MenuList
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    //done menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.menuB)
        {
            Intent intent1 = new Intent(this, AboutActivity.class);
            intent1.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
            this.startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //show Listing
    ArrayList<Song> newList = new ArrayList<>();
    public void addPosition(ArrayList<Song> position)
    {
        songArrayList.removeAll(songArrayList);
        //Log.d(TAG, "StockList" + position.size());
        songArrayList.addAll(position);
        //Log.d(TAG, "StockList" + locationList.toString());
        newList = new ArrayList<>(songArrayList);
        Log.d(TAG, "newList Size" + newList.size());

        songAdapter.notifyDataSetChanged();
    }


    public void showToast2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Internet Poor Conection");
        builder.setMessage("No Data Access! Please enable Internet ");
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //click position
    @Override
    public void onClick(View v)
    {
        Log.d(TAG, "Working Viewwwwwwwwwwfffffffffffffff");
        int pos = recyclerView.getChildLayoutPosition(v);
        song = songArrayList.get(pos);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("myinfo", song);
        intent.putExtra("position", pos);
        startActivity(intent);
        return;
    }
}
