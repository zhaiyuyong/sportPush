package com.zyy.activity;

import java.io.InputStream;
import java.util.List;

import com.zyy.po.Magazine;
import com.zyy.po.Store;
import com.zyy.util.DOMMagazineService;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase{

	
	public void testDOMgetMagazines()throws Throwable{
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("Magazine.xml");
		List<Magazine> magazines = DOMMagazineService.getMagazines(inStream);
		for(Magazine magazine:magazines){
			System.out.println(magazine.toString());
		}
	}
	public void testGetStores()throws Throwable{
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("store.xml");
		List<Store> stores = DOMMagazineService.getStores(inStream);
		for(Store store:stores){
			//System.out.println(store.toString());
			System.out.println("ID==="+store.getId());
			System.out.println("title"+store.getTitle());
			System.out.println("Release_date"+store.getRelease_date());
			System.out.println("getCover_URL"+store.getCover_URL());
			System.out.println("isFree"+store.isFree());
			System.out.println("getDownload_URL"+store.getDownload_URL());
			System.out.println("getFolder"+store.getFolder());
			System.out.println("getBundle_identifier"+store.getBundle_identifier());
			System.out.println("getDownload_date"+store.getDownload_date());
		}
	}
}
