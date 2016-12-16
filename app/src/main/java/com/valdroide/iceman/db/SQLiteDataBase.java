package com.valdroide.iceman.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LEO on 2/12/2016.
 */
public class SQLiteDataBase extends SQLiteOpenHelper {


    String TABLA_CLIENT = "CREATE TABLE IF NOT EXISTS CLIENT(ID_CLIENT INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " NAME VARCHAR(200));";

    String TABLA_SALE= "CREATE TABLE IF NOT EXISTS SALE(ID_SALE INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_CLIENT INTEGER,"
            + " QUANTITY INTEGER,"
            + " AMOUNT INTEGER,"
            + " DATE_SALE VARCHAR(100));";

    public SQLiteDataBase(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CLIENT);
        db.execSQL(TABLA_SALE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CLIENT");
        db.execSQL(TABLA_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS SALE");
        db.execSQL(TABLA_SALE);
    }
}
