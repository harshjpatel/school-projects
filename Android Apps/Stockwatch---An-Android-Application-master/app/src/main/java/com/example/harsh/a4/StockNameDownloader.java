package com.example.harsh.a4;

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


/**
 * Created by harsh on 3/14/17.
 */

public class StockNameDownloader extends AsyncTask<String, Void, List<String>> {
    TextView dataDisplay;
    private MainActivity mainActivity;
    private int count;
    private List<String> stockList = new List<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    private static final String TAG = "StockNameDownloader";
    private final String dataURL = "http://stocksearchapi.com/api/?api_key=09da86854c9a074fc3accaf914617e11b85ea0f4&search_text=";

    public StockNameDownloader(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: " + params[0]);

        Uri datauri = Uri.parse(dataURL + params[0]);
        String urlToUse = datauri.toString();

        Log.d(TAG, "doInBackground: " + urlToUse);

        //stringBuilder sb
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
            //Log.d(TAG, "doInBackground: " + sb.toString());
        } catch (Exception e) {
            ArrayList<String> stock = new ArrayList<>();
            Log.e(TAG, "doInBackground: ", e);
            return stock;
        }

        List<String> stock = parseJSON(sb.toString());
        return stock;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mainActivity, "Loading Stock Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(List<String> postlist) {
        Toast.makeText(mainActivity, "Loading Stock Data...", Toast.LENGTH_SHORT).show();
        String[] stringarray = new String[postlist.size()];
        String delimiter = "-";
        for (int j = 0; j < postlist.size(); j++) {
            stringarray[j] = postlist.get(j).toString();
            Log.d(TAG, j +"j" +stringarray[j]);
        }
        List<String> stringArrayList = new ArrayList<String>(Arrays.asList(stringarray));
        Log.d(TAG, "ArrayList" +stringArrayList);
        if(stringArrayList.size() > 0)
        {
            mainActivity.updateData(stringArrayList);
        }
        else
        mainActivity.showToast4();
    }


    private List<String> parseJSON(String s)
    {
        ArrayList<String> backlist = new ArrayList<>();
        try {
            JSONArray jr = new JSONArray(s);
            count = jr.length();


            for (int i = 0; i < jr.length(); i++) {
                JSONObject jCountry = (JSONObject) jr.get(i);
                String code = jCountry.getString("company_name").toString();
                String symbol = jCountry.getString("company_symbol").toString();
                String sansex = jCountry.getString("listing_exchange").toString();


                Log.d(TAG, "1: " + code);
                Log.d(TAG, "2: " + symbol);
                Log.d(TAG, "3: " + sansex);

                String stringlist1 = jCountry.getString("company_symbol").toString() +" - " + jCountry.getString("company_name").toString();
                Log.d(TAG, "4: " + stringlist1);

                backlist.add(stringlist1.toString());
                Log.d(TAG, "5: " + backlist.toString());
            }
            Log.d(TAG, "6: " + backlist.toString());
            return backlist;
        }
        catch (JSONException e)
        {
            Toast.makeText(mainActivity, "No....", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
    return null;
    }

}
