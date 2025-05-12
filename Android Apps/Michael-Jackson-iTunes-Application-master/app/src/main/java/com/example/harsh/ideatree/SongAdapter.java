package com.example.harsh.ideatree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


//Adapter to ViewHolder.

public class SongAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private static final String TAG = "SongAdapter";
    private List<Song> songList;
    MainActivity mainActivity;
    NetworkInfo activeNetworkInfo;
    Context c;
    Song song;

    public SongAdapter(Context c, List<Song> songList, MainActivity ma)
    {
        this.c = c;
        this.songList = songList;
        mainActivity = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        view.setOnClickListener(mainActivity);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        song = songList.get(position);
        holder.songname.setText(song.getSongname());
        holder.collectionname.setText(song.getCollectionname());
        holder.pricename.setText(song.getPrice() + " " + song.getCurrency());
        new DownloadImageTask(SongAdapter.this).execute(holder.imageView);
        holder.imageView.setTag(song.getImage());



        if (position == getItemCount()) {//check if this is the last child, if yes then hide the divider
            holder.divider.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount()
    {
        return songList.size();
    }
}
