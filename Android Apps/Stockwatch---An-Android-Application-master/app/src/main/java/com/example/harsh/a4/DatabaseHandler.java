package com.example.harsh.a4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by harsh on 3/10/17.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHandler";
    MainActivity mainActivity;
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    // DB Name
    private static final String DATABASE_NAME = "StockAppDB";
    // DB Table Name
    private static final String TABLE_NAME = "StockWatchTable";
    ///DB Columns
    private static final String CompanyName = "CompanyName";
    private static final String Symbol = "CompanySymbol";
    private SQLiteDatabase database;


    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " (" +
                    CompanyName + " TEXT not null," +
                    Symbol + " TEXT not null unique)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        Log.d(TAG, "DatabaseHandler: C'tor DONE");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // onCreate is only called is the DB does not exist
        Log.d(TAG, "onCreate: Mking New DB");
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addstock(StockData stock)
    {
        try
        {
            Log.d(TAG, "addStock: Adding1 " + stock.getT());
            Log.d(TAG, "addStock: Adding2 " + stock.getCompanyName());
            //ContentValues values = new ContentValues(); values.put(SYMBOL, stock.getSymbol()); values.put(COMPANY, stock.getCompany());
            //database.insert(TABLE_NAME, null, values); Log.d(TAG, "addStock: Add Complete”);

            ContentValues values = new ContentValues();
            values.put(Symbol, stock.getT());
            values.put(CompanyName, stock.getCompanyName());
            values.put(CompanyName, stock.getCompanyName());
            database.insert(TABLE_NAME, null, values);
            Log.d(TAG, "addStock: Add Complete");
        }
        catch (SQLiteConstraintException e)
        {
            e.getCause();
            mainActivity.showToast3();
        }
    }

    public void deleteStock(String symbol) {
        Log.d(TAG, "deleteStock: Deleting Stock " + symbol);
        int cnt = database.delete(
                TABLE_NAME, "CompanySymbol = ?", new String[]{symbol});
        Log.d(TAG, "deleteStock: " + cnt);
    }

    //load the samople-compabny entries into Database
    public ArrayList<StockData> loadStocks()
    {
        Log.d(TAG, " loadStocks: Load all symbol-company entries from DB");
        //Make a ArrayList
        ArrayList<StockData> stocks = new ArrayList<>();
        Cursor cursor = database.query
                (
                        TABLE_NAME, //the table to query
                        new String[]{Symbol, CompanyName},  //the column to return
                        null,
                        null,
                        null,
                        null,
                        null
                );
        if (cursor != null)
        {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++)
            {
                String symbol = cursor.getString(0);
                String companyName = cursor.getString(1);// 1st returned column String company = cursor.getString(1); // 2nd returned column
                stocks.add(new StockData(symbol, companyName));
                cursor.moveToNext();
            }
            cursor.close();
        }
        Log.d(TAG, "loadStocks: DONE LOADING Stocks DATA FROM DB");
        return stocks;
    }

    public void dumpLog()
    {
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Log.d(TAG, "dumpLog: vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            for (int i = 0; i < cursor.getCount(); i++) {
                String symbol = cursor.getString(0);
                String companyName = cursor.getString(1);
                Log.d(TAG, "dumpLog: " +
                        String.format("%s %-18s", Symbol + ":", symbol) +
                        String.format("%s %-18s", CompanyName + ":", companyName));
                cursor.moveToNext();
            }
            cursor.close();
        }

        Log.d(TAG, "dumpLog: ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}
