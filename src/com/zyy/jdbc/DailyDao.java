package com.zyy.jdbc;

import java.util.ArrayList;

import com.zyy.po.Book;



public interface DailyDao {

	public void insertDaily(Book daily);
	
	public ArrayList<Book> findAllDaily();
	
	public boolean haveDaily(String date);
	
	public void close();
	
	public void delete();
	
}
