package com.example.harsh.newsassignment;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import static com.example.harsh.newsassignment.MainActivity.ACTION_MSG_TO_SERVICE;
import static com.example.harsh.newsassignment.MainActivity.ACTION_NEWS_STORY;

/**
 * Created by harsh on 5/4/17.
 */

public class NewService extends Service {

    private static final String TAG = "NewService";
    private boolean running = true;
    public SourceGetSet sourcename;
    private ArrayList<ArticleData> storylist = new ArrayList<>();
    private ServiceReciever servicereceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("harsh", "new Intent service started:");

        servicereceiver = new ServiceReciever();

        IntentFilter filter1 = new IntentFilter(ACTION_MSG_TO_SERVICE);
        registerReceiver(servicereceiver, filter1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        while (storylist.size() == 0) {

                            Thread.sleep(250);
                        }

                        Intent intent1 = new Intent();
                        intent1.setAction(ACTION_NEWS_STORY);
                        intent1.putExtra("harsh", storylist);
                        sendBroadcast(intent1);
                        storylist.removeAll(storylist);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        return Service.START_STICKY;
    }

    private class ServiceReciever extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (intent.getAction()) {
                case ACTION_MSG_TO_SERVICE:
                    if (intent.hasExtra("myinfo"))
                    {
                        sourcename = (SourceGetSet) intent.getSerializableExtra("myinfo");
                        new NewsArticleDownloader(NewService.this, sourcename.getId()).execute();
                    }
            }

        }
    }

    public void setArticles(ArrayList<ArticleData> newsarticlelist)
    {
        storylist.clear();
        storylist.addAll(newsarticlelist);
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(servicereceiver);
        Intent intent = new Intent(NewService.this, MainActivity.class);
        stopService(intent);
        super.onDestroy();
    }
}



