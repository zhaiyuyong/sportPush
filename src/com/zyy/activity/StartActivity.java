package com.zyy.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.zyy.constant.FileBiz;
import com.zyy.constant.ProjectType;
import com.zyy.jdbc.DailyDao;
import com.zyy.jdbc.MyDataBase;
import com.zyy.po.Store;
import com.zyy.util.FileUtil;
import com.zyy.util.GetPlist;
import com.zyy.util.NetHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends Activity {

	private static final int ERROR = 101;
	private static final int INFORMATION_DOWN_SUCCESS = 100;
	
	
	private static final String GET_ANDROID_ID_TO_SERVER = "http://www.softechallenger.com/admin/index/register"; //根据get提交ANDROID手机唯一ID给服务器RUL
	private String mDeviceID;// ANDROID手机的ID

	// Connectivity manager to determining, when the phone loses connection
	private ConnectivityManager mConnMan;

	// 从本地store.plist文件获取杂志信息
	List<Store> stores = new ArrayList<Store>();

	int[] progress_dot_array = { R.id.dot1, R.id.dot2, R.id.dot3, R.id.dot4,
			R.id.dot5, R.id.dot6 };

	private ImageView dotBtn1, dotBtn2, dotBtn3;
	int position1, position2, position3;
	boolean flag;
	MyHandler myHandler;
	MyThread myThread;
	FileUtil fileUtil = new FileUtil();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
		
		/*DailyDao dao = new MyDataBase(this);
		dao.delete();
		dao.close();*/
		
		
		
		mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID); //获手机ID

		mConnMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		Log.i("zyy", "网络是否可用：" + isNetworkAvailable());

		System.out.println("isWifiConnected===" + isWifiConnected());
		System.out.println("isGprsConnected===" + isGprsConnected());

		
		myHandler = new MyHandler(StartActivity.this);
		flag = true;
		myThread = new MyThread(myHandler);
		myThread.start();
		if (!fileUtil.isFileExist("SportsDaily/store.plist")) {// 文件夹中不存在文件，第一次使用该应用
			if (!NetHelper.IsHaveInternet(getApplicationContext())) {// 判断网络是否可用
				// 不可用的网络
				Toast.makeText(StartActivity.this, "不可用的网络请检查网络设置",
						Toast.LENGTH_LONG).show();
			}
			// 可用的网络
			// 从网络上读取配置文件并且写到文件夹中
			new Thread(new MyTheadTheFirstUse()).start();
		} else {
			System.out.println("have this plist");
			myHandler.sendEmptyMessageDelayed(100, 5000);
		}

	}

	 
	/**
	 * Check if we are online
	 */
	private boolean isNetworkAvailable() {
		NetworkInfo info = mConnMan.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}

		return info.isConnected();
	}

	/**
	 * 判断是否连接上wifi
	 * 
	 * @return
	 */
	public boolean isWifiConnected() {
		return mConnMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState() == NetworkInfo.State.CONNECTED ? true : false;
	}

	/**
	 * 是否连接上gprs
	 * 
	 * @return
	 */
	public boolean isGprsConnected() {
		return mConnMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED ? true : false;
	}

	private class MyHandler extends Handler {

		Context context;
		Intent intent = new Intent();
			
		public MyHandler(Context context) {
			super();
			this.context = context;
		}
		@Override
		public void handleMessage(Message msg) {
			int position = msg.what;
			if (position == ERROR) {        //应用安装出现异常的时候
				/*intent.putExtra("success_or_fail", 0);
				intent.setClass(StartActivity.this, TabActivity.class);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();// 摧毁开机页面*/
				Toast.makeText(context, "当前网络出现问题，请稍后再安装", 1).show();
				StartActivity.this.finish();   //退出应用
			} else if (position == 100) {
				flag = false;
				intent.setClass(StartActivity.this, TabActivity.class);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();// 摧毁开机页面
				return;
			} else {
				position1 = position - 1;
				position2 = position;
				position3 = position + 1;
				if (position1 == -1) {
					position1 = 5;
				}
				if (position3 == 6) {
					position3 = 0;
				}
				dotBtn1 = (ImageView) findViewById(progress_dot_array[position1]);
				dotBtn2 = (ImageView) findViewById(progress_dot_array[position2]);
				dotBtn3 = (ImageView) findViewById(progress_dot_array[position3]);
				dotBtn1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.initdot1));
				dotBtn2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.initdot2));
				dotBtn3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.initdot3));
			}
		}
	}

	private class MyThread extends Thread {

		Handler handler;

		int key;

		public MyThread(Handler handler) {
			super();
			this.handler = handler;
			key = 5;
		}

		@Override
		public void run() {
			while (flag) {
				if (key == 6) {
					key = 0;
				}
				handler.sendEmptyMessage(key);
				try {
					Thread.sleep(180);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				key++;
			}
		}

	}

	/**
	 * 安装应用时候启动的线程
	 * 1、用于从服务器端下载store.plist文件
	 * 2、解析store.plist文件
	 * 3、根据解析后文件中的图片url来从网上下载图片并且写入到SD卡中
	 * @author SW
	 *
	 */
	private class MyTheadTheFirstUse implements Runnable {

		GetPlist getPlist = new GetPlist();
		FileBiz fileBiz = new FileBiz();
		boolean success = false;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean ret = getPlist.getPlist(ProjectType.plistUrl);//从网上获取store.plist文件
			if (ret == true) {
				stores = fileBiz.getInformationFromPlist();//解析store.plist文件返回一个store对象集合
				if (!stores.isEmpty()) {
					success = fileBiz.writePicture2SDCard(stores);//根据集合里面封面的url来从网上下载图片并且写入到文件夹中
				} else {
					System.out.println("解析xml文件出现问题");
					myHandler.obtainMessage(ERROR).sendToTarget();//解析store.plist文件出现问题
				}
			} else {
				System.out.println("plist下载出现问题");
				myHandler.obtainMessage(ERROR).sendToTarget(); //下载store.plist文件出现问题 和UI主线程通信
			}
			if (success == true) {     //判断图片是否下载成功，成功返回true 反之
				IDPostService iPostService = new IDPostService();
				if(iPostService.saveId(mDeviceID, GET_ANDROID_ID_TO_SERVER)){//将手机ID发送到服务器端
					myHandler.obtainMessage(INFORMATION_DOWN_SUCCESS)
					.sendToTarget();
				}else {
					myHandler.obtainMessage(ERROR).sendToTarget();
				}
			}

		}

	}
	/**
	 * 应用第一次安装的时候
	 * 当安装成功的时候将用户手机唯一ID通过GET方式传给后台服务器保存起来
	 * @author SW
	 *
	 */
	private class IDPostService {
		public boolean saveId(String id,String path){
			
			try {
				return this.sendGETRequest(path, id);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		public boolean sendGETRequest(String path,String id) throws MalformedURLException, IOException{
			StringBuffer url = new StringBuffer(path);
			url.append("?");
			url.append("dozen=").append(id);
			HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200 ){
				conn.disconnect();
				return true;
			}
			conn.disconnect();
			return false;
		}
	}
	
	

}
