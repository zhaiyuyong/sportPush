package com.zyy.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetHelper {

	public static boolean IsHaveInternet(final Context context){
		try {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
