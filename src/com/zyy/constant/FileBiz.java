package com.zyy.constant;

import java.util.ArrayList;
import java.util.List;

import com.zyy.po.Store;
import com.zyy.util.DOMMagazineService;
import com.zyy.util.FileUtil;
import com.zyy.util.GetPicture;

import java.io.*;

public class FileBiz {
	private FileUtil fileUtil = new FileUtil();

	//从解析plist.xml文件
	public List<Store> getInformationFromPlist(){
		List<Store> stores = new ArrayList<Store>();
		File  file = new File(fileUtil.getSDPATH()+ "SportsDaily/"+"store.plist");
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			stores = DOMMagazineService.getStores(inputStream);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stores;
	}
	
	public boolean writePicture2SDCard(List<Store> stores){
		GetPicture getPicture = new GetPicture();
		boolean flag = false;
		InputStream inputStream = null;
		for(int i=0;i<6;i++){
			String pictureUrl = stores.get(i).getCover_URL();
			try {
				 inputStream = getPicture.getInputStreamFromUrl(pictureUrl);
				 if (inputStream!=null) {
					int flag1 = stores.get(i).getCover_URL().lastIndexOf("/");
					String name = stores.get(i).getCover_URL().substring(
							flag1 + 1);
					fileUtil.write2SDCard(inputStream, "SportsDaily", name);
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
				}
			}
		}
		if(fileUtil.isFileExist("SportsDaily/monday.png")&&fileUtil.isFileExist("SportsDaily/friday.png")
				&&fileUtil.isFileExist("SportsDaily/tuesday.png")&&fileUtil.isFileExist("SportsDaily/weekend.png")
				&&fileUtil.isFileExist("SportsDaily/wednesday.png")&&fileUtil.isFileExist("SportsDaily/thursday.png")){
			flag = true;
		}
		return flag;
		
		
	}
}
