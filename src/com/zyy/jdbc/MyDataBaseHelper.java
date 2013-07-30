package com.zyy.jdbc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "SportsDaily";
	public static final int DATABASE_VERSION = 1;

	public MyDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table daily (bid  INTEGER PRIMARY KEY,contentURL varchar(200),cover BLOB,createTime varchar(40))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int now) {
		db.execSQL("DROP TABLE IF EXISTS daily");
		db.execSQL("create table daily (bid  INTEGER PRIMARY KEY,contentURL varchar(200),cover BLOB,createTime varchar(40))");
	}

	
	
	
	

}
