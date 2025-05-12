package com.example.harsh.newsassignment;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by harsh on 5/2/17.
 */

class NewsArticleDownloader extends AsyncTask<String, Void, String>
{
    String x, y;
    String id1;
    Uri datagenerator = null;
    String urlToUse = null;
    ArrayList<ArticleData> articlelist = new ArrayList<>();
    private NewService newsservice;
    private static final String TAG = "NewsArticleDownloader";
    private final String dataURL = "https://newsapi.org/v1/articles?source=";
    private final String apikey ="&apiKey=4cd3be69311e47e789bc1f4c4c334518";


    public NewsArticleDownloader(NewService ma, String id)
    {
        newsservice = ma;
        id1 = id;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(newsservice, "Loading NewsArticle Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        x = null;
        y = null;
        urlToUse = null;
        datagenerator = null;
        datagenerator = Uri.parse(dataURL + id1 + apikey);
        urlToUse = datagenerator.toString();
        Log.d(TAG, urlToUse);
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
            Log.d(TAG, "SB string: " + sb.toString());
        }
        catch (FileNotFoundException e)
        {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);
        }
        catch (Exception e)
        {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);
        }
        ArrayList<ArticleData> newsarticle = parseJSON(sb.toString());
        return String.valueOf(newsarticle);
    }

    private ArrayList<ArticleData> parseJSON(String s)
    {
        Log.d("00000", s);
        String author = null, title = null, description = null, urlToImage = null, publishedAt = null, url = null;
        try
        {
            JSONObject jr1 = new JSONObject(s);
            JSONArray response1 = jr1.getJSONArray("articles");
            Log.d(TAG, "Article Length: " +response1.length());

            for(int i =0; i<response1.length(); i++)
            {
//                author = null;
//                title = null;
//                description = null;
//                urlToImage = null;
//                publishedAt = null;
//                url = null;
                {
                    JSONObject job = response1.getJSONObject(i);
                    author = job.getString("author");
                    Log.d("[" + i + "]" + "Author:", author);
                    title = job.getString("title");
                    Log.d("[" + i + "]" + "Title:", title);
                    description = job.getString("description");
                    Log.d("[" + i + "]" + "Description:", description);
                    urlToImage = job.getString("urlToImage");
                    Log.d("[" + i + "]" + "urlToImage:", urlToImage);
                    publishedAt = job.getString("publishedAt");
                    Log.d("[" + i + "]" + "PublishedAt:", publishedAt);
                    url = job.getString("url");
                    Log.d("[" + i + "]" + "url:", url);
                }
                articlelist.add(new ArticleData(author, title, description, urlToImage, publishedAt, url));
            }
            for(int k = 0; k< articlelist.size(); k++)
            {
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getAuthor());
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getTitle());
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getDescription());
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getUrlToImage());
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getPublishedAt());
                Log.d(TAG, "ResourceList: [" + k + "]" + articlelist.get(k).getUrl());
            }
            return articlelist;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        if(articlelist.size() > 0)
        {
            newsservice.setArticles(articlelist);
        }
    }
}
