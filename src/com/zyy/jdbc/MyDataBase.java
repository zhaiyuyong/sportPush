package com.zyy.jdbc;

import java.util.ArrayList;

import com.zyy.po.Book;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MyDataBase implements DailyDao {

	MyDataBaseHelper dbHelper;
	Context context;

	public MyDataBase(Context context) {
		dbHelper = new MyDataBaseHelper(context);
		this.context = context;
	}
	
	@Override
	public ArrayList<Book> findAllDaily() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Book daily = null;
		ArrayList<Book> books = new ArrayList<Book>();
		
		Cursor cursor = db.rawQuery("select * from daily", null);
		while (cursor.moveToNext()) {
			daily = new Book();
			daily.setBid(cursor.getInt(0));
			daily.setContentURL(cursor.getString(1));
			daily.setCover(cursor.getBlob(2));
			daily.setCreateTime(cursor.getString(3));
		    books.add(daily);
		}
		cursor.close();
		db.close();
		return books;
	}

	@Override
	public void insertDaily(Book daily) {
		SQLiteDatabase db =  dbHelper.getWritableDatabase();
		try{
			db.execSQL("insert into daily (contentURL,cover,createTime) values (?,?,?)",
					new Object[]{daily.getContentURL(),daily.getCover(),daily.getCreateTime()});
		}catch (Exception e) {
			Toast.makeText(context, "≤Â»Î ß∞‹..", 1).show();
		}finally{
			db.close();
		}
	}

	@Override
	public void close() {
		if(dbHelper!=null){
			dbHelper.close();
			
		}
		
	}

	@Override
	public boolean haveDaily(String date) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try{
			Cursor cursor = db.rawQuery("select * from daily where createTime=?", new String[]{date});
			if(cursor.moveToNext()){
				cursor.close();
				return true;
			}else{
				cursor.close();
				return false;
			}
		}catch (Exception e) {
			Toast.makeText(context, "≤È—Ø ß∞‹..", 1).show();
			db.close();
		}
		return false;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		
		db.delete("daily", null, null);
	}

}
