package com.zyy.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DownLoadService extends Service{
	private static final String LOG_TAG = "DownLoadService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "onBind*********************");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(LOG_TAG, "onCreate*********************");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(LOG_TAG, "onDestroy*********************");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "onStartCommand*********************");
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	

}
