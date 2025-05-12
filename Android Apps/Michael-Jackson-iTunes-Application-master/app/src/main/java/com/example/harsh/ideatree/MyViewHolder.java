package com.example.harsh.ideatree;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//Viewholder
class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView songname;
    public TextView collectionname;
    public TextView pricename;
    public ImageView imageView;
    public ImageView divider;
    NetworkInfo activeNetworkInfo;

    public MyViewHolder(View view)
    {
        super(view);

        songname = (TextView) view.findViewById(R.id.songname);
        collectionname = (TextView) view.findViewById(R.id.collectionname);
        pricename = (TextView) view.findViewById(R.id.pricename);
        imageView = (ImageView) view.findViewById(R.id.image);
        divider = (ImageView) view.findViewById(R.id.divider);
    }
}
