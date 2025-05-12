package com.example.harsh.a4;

/**
 * Created by harsh on 3/16/17.
 */
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


class StockDataDownloader extends AsyncTask<String, Void, String> {
    private MainActivity mainActivity;
    private int count;
    private final String dataURL = "http://finance.google.com/finance/info?client=ig&q=";
    private static final String TAG = "AsyncDataLoader";
    String part2 = null;
    String part1 = null;
    StockData stock1 = null;

    public StockDataDownloader(MainActivity ma)
    {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity, "Loading Stock Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s)
    {
        Toast.makeText(mainActivity, "Loading Stock Data..", Toast.LENGTH_SHORT).show();
        ArrayList <StockData> stockdataList = parseJSON(s);
        Log.d(TAG, "postExecute: " + stockdataList);
        Log.d(TAG, "part1: " + part1);
        Log.d(TAG, "part2: " + part2);
        if(stockdataList.size() > 0)
        {
            mainActivity.addStock(stockdataList);
        }
        else
            mainActivity.showToast2();

    }

    @Override
    protected String doInBackground(String... params)
    {
        String s1 = params[0];
        String[] parts = s1.split("-");
        part1 = parts[0];
        Log.d(TAG, "Location" +part1);
        part2 = parts[1];
        Log.d(TAG, "doInBackground1: " + part1);
        Uri dataUri = Uri.parse(dataURL+part1);
        String urlToUse = dataUri.toString();
        Log.d(TAG, "doInBackground1: " + urlToUse);

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
            String s = sb.toString();
            s11 = s.replace("//","");
            Log.d(TAG, "doInBackground2: " + s11);
        }
        catch (Exception e)
        {
            Log.e(TAG, "doInBackground3: ", e);
            return null;
        }

        Log.e(TAG, "doInBackground4: "+ s11);
        return s11;

    }

    private ArrayList<StockData> parseJSON(String s)
    {
        ArrayList<StockData> stockdatalist = new ArrayList<>();
        try {
            JSONArray jObjMain = new JSONArray(s);
            count = jObjMain.length();


            for (int i = 0; i < jObjMain.length(); i++)
            {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String id = jCountry.getString("id");
                String t = jCountry.getString("t");
                String e = jCountry.getString("e");
                String l = jCountry.getString("l");
                String l_fix = jCountry.getString("l_fix");
                String l_cur = jCountry.getString("l_cur");
                String ss = jCountry.getString("s");
                String ltt = jCountry.getString("ltt");
                String lt = jCountry.getString("lt");
                String lt_dts = jCountry.getString("lt_dts");
                String c = jCountry.getString("c");
                String c_fix = jCountry.getString("c_fix");
                String cp = jCountry.getString("cp");
                String cp_fix = jCountry.getString("cp_fix");
                String ccol = jCountry.getString("ccol");
                String pcls_fix = jCountry.getString("pcls_fix");


                Log.d(TAG, "1: " + id);
                Log.d(TAG, "2: " + t);
                Log.d(TAG, "3: " + e);
                Log.d(TAG, "4: " + l);
                Log.d(TAG, "5: " + l_fix);
                Log.d(TAG, "6: " + l_cur);
                Log.d(TAG, "7: " + ss);
                Log.d(TAG, "8: " + ltt);
                Log.d(TAG, "9: " + lt);
                Log.d(TAG, "10: " + lt_dts);
                Log.d(TAG, "11: " + c);
                Log.d(TAG, "12: " + c_fix);
                Log.d(TAG, "13: " + cp);
                Log.d(TAG, "14: " + cp_fix);
                Log.d(TAG, "15: " + ccol);
                Log.d(TAG, "16: " +pcls_fix);


                if(t!=null || l!=null || c!=null || part2!=null || cp !=null)
                {
                    stockdatalist.add(new StockData(t, Double.parseDouble(l), Double.parseDouble(c), part2, cp));
                }


            }
            Log.d(TAG, "stocklist: " + stockdatalist.toString());
            return stockdatalist;

        }
        catch (Exception e)
        {
            Log.d(TAG, "parseJSON11: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }




}
