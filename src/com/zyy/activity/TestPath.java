package com.zyy.activity;

import com.zyy.util.FileUtil;

import android.test.AndroidTestCase;

public class TestPath extends AndroidTestCase{
	
	
	public void testGetPath()throws Throwable{
		FileUtil fileUtil = new FileUtil();
		System.out.println("PATH="+fileUtil.getSDPATH());
	}

}
