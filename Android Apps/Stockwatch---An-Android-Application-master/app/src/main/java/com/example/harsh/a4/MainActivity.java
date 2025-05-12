package com.example.harsh.a4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Collections;
import android.net.NetworkInfo;
import java.util.HashMap;
import android.text.InputType;
import android.text.InputFilter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener
{
    private static final String TAG = "MainActivity";
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("EEE MMM d, hh:mm:ss a");
    String strDate = mdformat.format(calendar.getTime());
    private static final int NEW = 1;
    private SwipeRefreshLayout swiper;
    public TextView title, time, description;
    private RecyclerView recyclerView;
    private StockAdapter stockadapter;
    //private List<Stock> stockList = new ArrayList<>();
    private ArrayList<StockData> stockList1 = new ArrayList<>();//Main Content save here
    StockData stock1;
    StockNameDownloader sk1;
    String s1 = null;
    private DatabaseHandler databaseHandler;
    private static final int ADD_CODE = 1;
    private static final int UPDATE_CODE = 2;
    String part2 = null;
    String part1 = null;
    final String[] et2 = {null};
    NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final String TAG = "MainActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        stockadapter = new StockAdapter(stockList1, this);
        recyclerView.setAdapter(stockadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (isNetworkConnected() == true)
                {
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
                    {
                        showToast();

                    }
                    else
                        showToast1();
                }
                swiper.setRefreshing(false);
            }
        });
        //databaseHandler in MainActivity

        //if network is connected
        if(isNetworkConnected() == true);
        {
          if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
          {
              databaseHandler = new DatabaseHandler(this);
              databaseHandler.dumpLog();
              ArrayList<StockData> stockList = databaseHandler.loadStocks();
              for (int i = 0; i < stockList.size(); i++)
              {
                  new StockDataDownloader(MainActivity.this).execute(stockList.get(i).getT() + "-" + stockList.get(i).getCompanyName());
              }
          }
          else
          {
              databaseHandler = new DatabaseHandler(this);
              databaseHandler.dumpLog();
              ArrayList<StockData> stockList = databaseHandler.loadStocks();
              showToast1();
              Log.d(TAG, "Database Loaded but Net Connection needs to available! SORRY:"+ stockList);
          }
        }
        //stockList1.addAll(stockList);
    }

    public void newSymbol(String s1)
    {
        new StockDataDownloader(MainActivity.this).execute(s1);
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();

        return true;
    }

    public void showToast()
    {
        Toast.makeText(this, "Refresh!!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Net is connected!", Toast.LENGTH_SHORT).show();
        ArrayList<StockData> stocklist2 = new ArrayList<StockData>(stockList1);
        stockList1.clear();
        Log.d(TAG, "Stock2 data:"+ stocklist2);
        Log.d(TAG, "Stock2 data:"+ stockList1.size());
        Log.d(TAG, "Stock2 data:"+ stocklist2.get(0).getT());
        Log.d(TAG, "Stock2 data:"+ stocklist2.get(0).getCompanyName());
        for (int i = 0; i < stocklist2.size(); i++)
        {
            //stockList1.remove(stock1);
            new StockDataDownloader(MainActivity.this).execute(stocklist2.get(i).getT() + "-" + stocklist2.get(i).getCompanyName());
        }
    }

    public void showToast1()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Stocks cannot be updated without A Network Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast11()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Stocks cannot be Added without A Network Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast2()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Stock Foiund");
        builder.setMessage("No Saved Stock Found! Sorry ");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast3()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dulicate Stock");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("Stock Symbol "+part1+"is already displayed");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast4()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Symbol Not Found: "+ et2[0]);
        builder.setMessage("Data for Stock Symbol");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int drawableResourceId1 = R.drawable.ic_action_name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.findItem(R.id.menuA);
        return true;
    }
    //done menu option

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuA)
        {
            if (isNetworkConnected()) ;
            {
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
                {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    final View view = inflater.inflate(R.layout.entervalue, null);
                    //alertDilaogBox
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    Log.d(TAG, "Intent Started!!!!!!!!!!!!!!!!!!!!!!!!");
                    builder.setMessage("Please enter a Stock Symbol:");
                    builder.setTitle("Stock Selection");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText et1 = (EditText) view.findViewById(R.id.textL);
                            if (et1.getText().length() != 0) {
                                et2[0] = et1.getText().toString();
                            }
                            // Intent data = new Intent(MainActivity.this, StockNameDownloader.class);
                            Log.d(TAG, "et2 : =" + et2[0]);
                            new StockNameDownloader(MainActivity.this).execute(et2[0]);
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setMessage("Please enter a Stock Symbol:");
                    builder.setTitle("Stock Selection");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                    showToast11();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {
        Log.d(TAG, "Working Viewwwwwwwwwwfffffffffffffff");
        int pos = recyclerView.getChildLayoutPosition(v);
        StockData stock2 = stockList1.get(pos);
        Log.d(TAG, "Stock2 data:"+ stock2.getT());
        Uri uri = Uri.parse("http://www.marketwatch.com/investing/stock/"+stock2.getT());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    HashMap<String, StockData> map = new HashMap<String, StockData>();
    @Override
    public boolean onLongClick(View v)
    {
        final int pos = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final StockData stock2 = stockList1.get(pos);
        builder.setTitle("Delete Stock");
        builder.setIcon(R.drawable.delete);
        builder.setMessage("Delete Stock Symbol" + " '" + stockList1.get(pos).getT() + "'?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                stockList1.remove(stock2);
                Log.d(TAG, stock2.getT().toString());
                databaseHandler.deleteStock(stock2.getT().toString());
                newList.remove(stock2);
                stockadapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult:");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("EEE MMM d, hh:mm:ss a");
                String strDate = mdformat.format(calendar.getTime());
                StockData stock = (StockData) data.getSerializableExtra("myinfo");
                stockList1.add(0, stock);
                stockadapter.notifyDataSetChanged();
            }
        }
    }

    ArrayList<StockData> newList = new ArrayList<>();
    public void addStock(ArrayList<StockData> stockdataList1)
    {
        //main page data reaveled
        String name = stockdataList1.get(0).getT();
        Log.d(TAG, "l1"+name);
        {
            stockList1.addAll(stockdataList1);
            newList = new ArrayList<>(stockList1);
            Log.d(TAG, "StockList" + newList.toString());
            Collections.sort(stockList1, new CustomComparator());
            databaseHandler.addstock(stockdataList1.get(0));
            //stockadapter.notifyDataSetChanged();
        }
    }



    public void updateData(final List<String> stringArrayList) {
        final List<String> stockList1 = new ArrayList<>();
        final CharSequence[] sArray = new CharSequence[50];
        stockList1.addAll(stringArrayList);
        Log.d(TAG, "l" + stockList1.toString());
        stockList1.toString();
        for (int i = 0; i < stockList1.size(); i++)
        {
            sArray[i] = stockList1.get(i);
            Log.d(TAG, "i" + sArray[i].toString());
        }

        if (stockList1.size() > 1)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Make a selection");
            builder.setItems(stockList1.toArray(new String[stockList1.size()]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    s1 = stockList1.toArray(new String[stockList1.size()])[which].toString();
                    Log.d(TAG, "Location" + s1);
                    String[] parts = s1.split("-");
                    part1 = new String(parts[0].toString());
                    Log.d(TAG, "Location" + part1);
                    part2 = parts[1];
                    Log.d(TAG, "doInBackground1: " + part1);
                    ArrayList<StockData> stockList2 = new ArrayList<>();
                    stockList2.addAll(newList);
                    map = new HashMap<String, StockData>();
                    for (StockData sd : stockList2) {
                        map.put(sd.getT(), sd);
                    }
                    Log.d(TAG, "HashMAp: " + map);
                    Log.d(TAG, "HashMAp: " + map.keySet());
                    Log.d(TAG, "HashMAp: " + map.containsKey(part1.trim()));
                    if (map.containsKey(part1.trim())) {
                        Log.d(TAG, "containkey");
                        showToast3();
                    } else
                        newSymbol(s1);
                }
            });
            builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(stockList1.size() == 1) {
            s1 = stockList1.toArray(new String[stockList1.size()])[0].toString();
            Log.d(TAG, "Location" + s1);
            String[] parts = s1.split("-");
            part1 = new String(parts[0].toString());
            Log.d(TAG, "Location" + part1);
            part2 = parts[1];
            Log.d(TAG, "doInBackground1: " + part1);
            ArrayList<StockData> stockList2 = new ArrayList<>();
            stockList2.addAll(newList);
            HashMap<String, StockData> map = new HashMap<String, StockData>();
            for (StockData sd : stockList2) {
                map.put(sd.getT(), sd);
            }
            Log.d(TAG, "HashMAp: " + map);
            Log.d(TAG, "HashMAp: " + map.keySet());
            Log.d(TAG, "HashMAp: " + map.containsKey(part1.trim()));
            if (map.containsKey(part1.trim())) {
                Log.d(TAG, "containkey");
                showToast3();
            } else
                newSymbol(s1);
        }

    }



    private class CustomComparator implements java.util.Comparator<StockData>
    {
        @Override
        public int compare(StockData o1, StockData o2) {
            return o1.getT().compareTo(o2.getT());
        }
    }
}