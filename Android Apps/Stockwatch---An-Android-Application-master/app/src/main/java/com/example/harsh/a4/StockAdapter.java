package com.example.harsh.a4;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by harsh on 3/10/17.
 */
public class StockAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private static final String TAG = "StockAdapter";
    private List<StockData> stockList;
    private MainActivity mainActivity;

    public StockAdapter(List<StockData> stockList, MainActivity ma)
    {
        this.stockList = stockList;
        mainActivity = ma;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        view.setOnClickListener(mainActivity);
        view.setOnLongClickListener(mainActivity);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        StockData stock = stockList.get(position);
        Log.d(TAG, " getL()" + stock.getC());
        if(String.valueOf(stock.getC()).startsWith("-"))
        {
            holder.stock.setText(String.valueOf(stock.getL()));
            holder.stock.setTextColor(Color.RED);
            holder.code.setText(stock.getT());
            holder.code.setTextColor(Color.RED);
            holder.compName.setText(stock.getCompanyName());
            holder.compName.setTextColor(Color.RED);
            holder.percent.setText("(" + stock.getCp_fix() + "%)");
            holder.percent.setTextColor(Color.RED);
            holder.sanssex.setText(String.valueOf(stock.getC()));
            holder.sanssex.setTextColor(Color.RED);
            holder.sign.setText("▼");
            holder.sign.setTextColor(Color.RED);
        }
        else
        {
            holder.stock.setText(String.valueOf(stock.getL()));
            holder.stock.setTextColor(Color.GREEN);
            holder.code.setText(stock.getT());
            holder.code.setTextColor(Color.GREEN);
            holder.compName.setText(stock.getCompanyName());
            holder.compName.setTextColor(Color.GREEN);
            holder.sign.setText("▲");
            holder.sign.setTextColor(Color.GREEN);
            holder.percent.setText("(" + stock.getCp_fix() + "%)");
            holder.percent.setTextColor(Color.GREEN);
            holder.sanssex.setText(String.valueOf(stock.getC()));
            holder.sanssex.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
