package com.zyy.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPicture {
	
	private URL url = null;

	public InputStream getInputStreamFromUrl(String urlStr)throws MalformedURLException, IOException{
		url = new URL(urlStr);
		
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		if(urlConn.getReadTimeout() == 5){
			return null;
		}
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	
}
