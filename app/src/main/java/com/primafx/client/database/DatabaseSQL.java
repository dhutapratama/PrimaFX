package com.primafx.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhuta on 17/02/2016.
 */
public class DatabaseSQL extends SQLiteOpenHelper {
    public static final String DB_NAME = "primafx.db";
    public static final int DB_VER = 4;
    public static final String TABLE_NAME = "userdata";
    public static final String FIELD_LOGIN_CODE = "login_code";
    public static final String FIELD_FIRST_TIME = "first_time";

    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String FIELD_ACCOUNT_NUMBER = "account_number";

    public DatabaseSQL(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public static void getInitialData(Context context) {

        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSQL.FIELD_LOGIN_CODE,
                DatabaseSQL.FIELD_FIRST_TIME
        };

        Cursor collected_data = db.query(
                DatabaseSQL.TABLE_NAME, // The table to query
                projection, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        collected_data.moveToFirst();
        GData.LOGIN_CODE = collected_data.getString(collected_data.getColumnIndexOrThrow(DatabaseSQL.FIELD_LOGIN_CODE));
        Log.i("GDATA", GData.LOGIN_CODE);
        GData.FIRST_TIME = collected_data.getString(collected_data.getColumnIndexOrThrow(DatabaseSQL.FIELD_FIRST_TIME));
        db.close();
    }

    public static void updateSecurityData(Context context, String field, String data) {
        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(field, data);
        db.update(TABLE_NAME, values, null, null);
        db.close();
    }

    public static void Logout(Context context) {
        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_LOGIN_CODE, "null");
        db.update(TABLE_NAME, values, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String login_data = "CREATE TABLE " + TABLE_NAME + " ( " +
                FIELD_LOGIN_CODE + " TEXT," +
                FIELD_FIRST_TIME + " TEXT )";
        db.execSQL(login_data);

        String account_data = "CREATE TABLE " + TABLE_ACCOUNTS + " ( " +
                FIELD_ACCOUNT_NUMBER + " TEXT )";
        db.execSQL(account_data);

        ContentValues values = new ContentValues();
        values.put(FIELD_LOGIN_CODE, "null");
        values.put(FIELD_FIRST_TIME, "true");
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public static List<String> getAccountData(Context context) {

        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseSQL.FIELD_ACCOUNT_NUMBER
        };

        Cursor collected_data = db.query(
                DatabaseSQL.TABLE_ACCOUNTS, // The table to query
                projection, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        List<String> array = new ArrayList<String>();
        while(collected_data.moveToNext()){
            String uname = collected_data.getString(collected_data.getColumnIndexOrThrow(DatabaseSQL.FIELD_ACCOUNT_NUMBER));
            array.add(uname);
        }
        db.close();

        return array;
    }

    public static void addAccount(Context context, String account) {
        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ACCOUNT_NUMBER, account);
        db.insert(TABLE_ACCOUNTS, null, values);

        db.close();
    }

    public static void deleteAccount(Context context, String number)
    {
        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(TABLE_ACCOUNTS, FIELD_ACCOUNT_NUMBER + "=" + number, null);
        db.close();
    }

    public static void removeAllAccount(Context context){
        DatabaseSQL mDbHelper = new DatabaseSQL(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(TABLE_ACCOUNTS, "", null);
        db.close();
    }
}
