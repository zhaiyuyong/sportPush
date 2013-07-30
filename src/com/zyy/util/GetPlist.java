package com.zyy.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class GetPlist {

	private URL url = null;
	private FileUtil fileUtil = new FileUtil();

	public InputStream getInputStreamFromUrl(String urlStr)throws MalformedURLException, IOException{
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setConnectTimeout(50000);//设置连接主机超时
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	public boolean getPlist(String urlStr){
		boolean flag = false;
		InputStream inputStream=  null;
		try {
			 inputStream = this.getInputStreamFromUrl(urlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		try {
			if (inputStream!=null) {
				new FileUtil().write2SDCard(inputStream, "SportsDaily",
						"store.plist");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		if(fileUtil.isFileExist("SportsDaily/store.plist")){
			flag = true;
		}
		
		return flag;
	}
}
