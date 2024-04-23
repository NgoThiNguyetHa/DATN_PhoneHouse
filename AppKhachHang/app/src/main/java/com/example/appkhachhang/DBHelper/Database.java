package com.example.appkhachhang.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="DB_Name";
    public static final String CREATE_TABLE_CART="create table ChiTietGioHang" +
            "( id  integer not null primary key autoincrement," +
            " masp integer not null,"+
            " mactsp integer not null,"+
            " masize integer not null,"+
            " macolor integer not null,"+
            " soluong integer not null);";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +DATABASE_NAME;

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cart");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
