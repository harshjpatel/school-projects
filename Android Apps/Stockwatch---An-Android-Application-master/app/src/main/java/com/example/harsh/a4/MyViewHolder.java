package com.example.harsh.a4;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by harsh on 3/2/17.
 */
public class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView stock;
    public TextView code;
    public TextView compName;
    public TextView sanssex;
    public TextView percent;
    public TextView sign;

    public MyViewHolder(View view)
    {
        super(view);
        stock = (TextView) view.findViewById(R.id.stock);
        code = (TextView) view.findViewById(R.id.code);
        compName = (TextView) view.findViewById(R.id.description);
        sanssex = (TextView) view.findViewById(R.id.sansex);
        percent = (TextView) view.findViewById(R.id.percentage);
        sign = (TextView) view.findViewById(R.id.sign);
    }
}
