package com.zyy.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabActivity extends ActivityGroup{
	
	private static final String LOG_TAG = "TabActivity";
	public static TabHost tabs;


	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.setContentView(R.layout.tab);
	        
	        
	        
	        
	        
	        tabs  = (TabHost)this.findViewById(R.id.tabhost);
	
	        tabs.setBackgroundColor(Color.argb(150, 22, 70, 150));
	        
	        
	        
	        
	        
	       
	        tabs.setup(this.getLocalActivityManager());
	       
	        TabSpec ts1 = tabs.newTabSpec("TAB_INTERNET");
	        ts1.setIndicator("天天体坛",getResources().getDrawable(R.drawable.store));
	       
	        Intent intent = new Intent();
	        ts1.setContent(new Intent(this, Project1Activity.class));
	        tabs.addTab(ts1);
	        
	        TabSpec ts2 = tabs.newTabSpec("TAB_MY");
	        ts2.setIndicator("我的体坛",getResources().getDrawable(R.drawable.order));
	       
	        ts2.setContent(new Intent(this, BooksActivity.class));
	        tabs.addTab(ts2);
	        
	        tabs.setBackgroundColor(Color.argb(0, 0, 0, 0));
	        
	        tabs.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					// TODO Auto-generated method stub
					if(tabId.equals("TAB_INTERNET")){
						
					}else if(tabId.equals("TAB_MY")){
						
					}
				}
			});
	   
	        final TabWidget tabWidget = tabs.getTabWidget();
	        tabWidget.getChildAt(0).setBackgroundColor(Color.BLUE);
	        tabWidget.getChildAt(1).setBackgroundColor(Color.GREEN);
	        tabs.setCurrentTab(0);
	        
	    tabs.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if("TAB_MY".equals(tabId)){
					tabWidget.getChildAt(1).setBackgroundColor(Color.YELLOW);
				}
			}
		});
	        
	    }
}
