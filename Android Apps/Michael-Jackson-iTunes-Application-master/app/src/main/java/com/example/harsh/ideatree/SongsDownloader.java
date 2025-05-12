package com.example.harsh.ideatree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//get data from SongAPI
public class SongsDownloader extends AsyncTask<String, Void, String>
{
    private MainActivity mainActivity;
    private int count;
    private static final String TAG = "SongsDownloader";
    ArrayList<Song> songlist = new ArrayList<>();
    ArrayList<Song> songlist1 = new ArrayList<>();
    private final String dataURL = "https://itunes.apple.com/search?term=Michael+jackson";
    ArrayList<Song> backlist = new ArrayList<>();
    public SongsDownloader(MainActivity ma)
    {
        mainActivity = ma;
    }


    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity, "Loading Songs Data...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected String doInBackground(String... params)
    {
        Log.d(TAG, "doInBackground: ");

        Uri datauri = Uri.parse(dataURL);
        String urlToUse = datauri.toString();

        Log.d(TAG, "doInBackground: " + urlToUse);

        //stringBuilder sb
        StringBuilder sb = new StringBuilder();
        String line, s11;
        try
        {
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
            Log.d(TAG, "doInBackground: " + sb.toString());
        }
        catch (FileNotFoundException e)
        {
            ArrayList<String> song = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(song);
        }
        catch (Exception e)
        {
            ArrayList<String> song = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(song);
        }
        ArrayList<Song> song = parseJSON(sb.toString());
        return String.valueOf(song);
    }

    @Override
    protected void onPostExecute(String s)
    {
        Toast.makeText(mainActivity, "Loading Songs Data..", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "postExecute2: " +songlist1 );
        Log.d(TAG, "postExecute2: " +songlist1.size() );
        if(songlist1.size() > 0)
        {
            mainActivity.addPosition(songlist);
        }
        else
            mainActivity.showToast2();

    }

    private ArrayList<Song> parseJSON(String s) {
        try {
            JSONObject jr = new JSONObject(s);
            JSONArray response1 = (jr.getJSONArray("results"));
            Log.d("Length", String.valueOf(response1.length()));
            String songname = null;
            String collectionName = null;
            String priceName = null;
            String image = null;
            String collectionId = null;
            String trackId = null;
            String releaseDate = null;
            String trackCount = null;
            String trackNumber = null;
            String trackTimeMillis = null;
            String currency = null;
            String primaryGenreName = null;
            String artistName = null;
            String previewUrl = null;
            String artistViewUrl = null;


            for (int i = 0; i < response1.length(); i++) {
                JSONObject jb1 = response1.getJSONObject(i);

                //songName
                {
                    if (jb1.has("trackName")) {
                        songname = (String) jb1.getString("trackName");
                    } else
                        songname = " ";

                }
                Log.d("songname", String.valueOf(songname));

                //collectionName
                {
                    if (jb1.has("collectionName")) {
                        collectionName = (String) jb1.getString("collectionName");
                    } else
                        collectionName = " ";
                }
                Log.d("CollectionName", String.valueOf(collectionName));

                //trackprice
                {
                    if (jb1.has("trackPrice")) {
                        priceName = (String) jb1.getString("trackPrice");
                    } else
                        priceName = "Loading...";
                }
                Log.d("Price", String.valueOf(priceName));

                //ImageURL
                {
                    if (jb1.has("artworkUrl100")) {
                        image = (String) jb1.getString("artworkUrl100");
                    } else
                        image = "Loading...";
                }
                Log.d("image100:", String.valueOf(image));

                //CollectionID
                {
                    if (jb1.has("collectionId")) {
                        collectionId = (String) jb1.getString("collectionId");
                    } else
                        collectionId = " ";

                }
                Log.d("image100:", String.valueOf(collectionId));

                //trackId
                {
                    if (jb1.has("trackId")) {
                        trackId = (String) jb1.getString("trackId");
                    } else
                        trackId = " ";
                }
                Log.d("image100:", String.valueOf(trackId));

                //realeaseDate
                {
                    if (jb1.has("releaseDate")) {
                        releaseDate = (String) jb1.getString("releaseDate");
                    } else
                        releaseDate = " ";
                }
                Log.d("image100:", String.valueOf(releaseDate));


                //TrackCount
                {
                    if (jb1.has("trackCount")) {
                        trackCount = (String) jb1.getString("trackCount");
                    } else
                        trackCount = " ";
                }
                Log.d("image100:", String.valueOf(trackCount));


                //trackNumber
                {
                    if (jb1.has("trackNumber")) {
                        trackNumber = (String) jb1.getString("trackNumber");
                    } else
                        trackNumber = " ";
                }
                Log.d("image100:", String.valueOf(trackNumber));

                //trackMillis
                {
                    if (jb1.has("trackTimeMillis")) {
                        trackTimeMillis = (String) jb1.getString("trackTimeMillis");
                    } else
                        trackTimeMillis = " ";
                }
                Log.d("image100:", String.valueOf(trackTimeMillis));


                //currency
                {
                    if (jb1.has("currency")) {
                        currency = (String) jb1.getString("currency");
                    } else
                        currency = " ";
                }
                Log.d("image100:", String.valueOf(currency));


                //primaryGenere
                {
                    if (jb1.has("primaryGenreName")) {
                        primaryGenreName = (String) jb1.getString("primaryGenreName");
                    } else
                        primaryGenreName = " ";
                }
                Log.d("image100:", String.valueOf(primaryGenreName));


                //ArtistName
                {
                    if (jb1.has("artistName")) {
                        artistName = (String) jb1.getString("artistName");
                    } else
                        artistName = " ";
                }
                Log.d("image100:", String.valueOf(artistName));

                //preview
                {
                    if (jb1.has("previewUrl")) {
                        previewUrl = (String) jb1.getString("previewUrl");
                    } else
                        previewUrl = " ";
                }
                Log.d("image100:", String.valueOf(previewUrl));


                //collectionViewURL
                {
                    if (jb1.has("artistViewUrl")) {
                        artistViewUrl = (String) jb1.getString("artistViewUrl");
                    } else
                        artistViewUrl = " ";
                }
                Log.d("image100:", String.valueOf(artistViewUrl));



                songlist.add(new Song(songname, collectionName, priceName, image, collectionId, trackId, releaseDate, trackCount, trackNumber, trackTimeMillis, currency, primaryGenreName, artistName, previewUrl, artistViewUrl));
                Log.d(TAG, "songListSize: " + songlist.size());
            }

            Log.d(TAG, "songListSize1: " + songlist.size());
            for (int k = 0; k < songlist.size(); k++) {
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getSongname());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getCollectionname());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getPrice());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getImage());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getCollectionId());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getTrackId());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getReleaseDate());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getTrackCount());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getTrackNumber());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getTrackTimeMillis());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getCurrency());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getPrimaryGenreName());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getArtistName());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getPreviewUrl());
                Log.d(TAG, "SongList: [" + k + "]" + songlist.get(k).getArtistViewUrl());
                songlist1.add(new Song(songname, collectionName, priceName, image, collectionId, trackId, releaseDate, trackCount, trackNumber, trackTimeMillis, currency, primaryGenreName, artistName, previewUrl, artistViewUrl));
            }
            Log.d(TAG, "songListSize2: " + songlist1.size());

            return songlist1;
        }
        catch (JSONException e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
