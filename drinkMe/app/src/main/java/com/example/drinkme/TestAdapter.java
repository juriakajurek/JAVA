package com.example.drinkme;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.drinkme.SearchProductsActivity.rodzaj;
import static com.example.drinkme.SearchProductsActivity.search;




public class TestAdapter {
    protected static final String TAG = "DataAdapter";


    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
            mDb = mDbHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getTestData()
    {
            String sql ="SELECT * FROM Produkt";
            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
    }

    public Cursor getProducts(){
            Cursor mCur = mDb.rawQuery("SELECT * FROM Produkt WHERE Nazwa LIKE '"+search+"%'",null);
            return mCur;

    }

    public void updateLiczbaButelek (int poprawiona, int id) {
        String query = "UPDATE Produkt SET Liczba_butelek = "+poprawiona+" WHERE Produkt_ID = "+id;
        mDb.execSQL(query);
    }
}