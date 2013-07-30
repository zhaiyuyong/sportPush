package com.zyy.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;






import com.zyy.jdbc.DailyDao;
import com.zyy.jdbc.MyDataBase;
import com.zyy.po.Book;
import com.zyy.po.Store;
import com.zyy.util.DOMMagazineService;
import com.zyy.util.FileUtil;
import com.zyy.util.GetPicture;
import com.zyy.util.GetPlist;
import com.zyy.util.NetHelper;
import com.zyy.util.XZip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Project1Activity extends Activity {
	
	private static final String LOG_TAG = "Project1Activity";
  
	int success_or_fail = 0;

	private static final int UPDATE_TIME = 25;

	

	private MyHandler myHandler = new MyHandler();
	private MyThead myThead = new MyThead();
	private static final int MSG_SUCCESS = 0;//获取图片成功的标识  
    private static final int MSG_FAILURE = 1;//获取图片失败的标识  
    private static final int CHECK_UPDATE = 3;//有更新时候提示消息
    private static final int DOWN_ProgressDialog = 4;//关闭加载提示信息
    private static final int CHUSHIHUA = 5;
	
	//6期杂志下载按钮
	private ImageButton item0 = null;
	private ImageButton item1 = null;
	private ImageButton item2 = null;
	private ImageButton item3 = null;
	private ImageButton item4 = null;
	private ImageButton item5 = null;
	
	private ImageButton shezhi = null;//(ImageButton) this.findViewById(R.id.ib_Subscribe);
	


	
	private ImageView[] imageViews = new ImageView[6];
	
	 InputStream inStream = null;
	 //从本地store.plist文件获取杂志信息
	 List<Store> stores = new ArrayList<Store>();
	 FileUtil fileUtil  = new FileUtil();
	 
	 private ProgressDialog progressDialog;
	 
	 
	 
	 private ProgressBar[] progressBars =  new ProgressBar[6];
	 
	
	 
	 

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			Log.i(LOG_TAG, "onPause()..................");
		}

		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			super.onRestart();
			Log.i(LOG_TAG, "onRestart()..................");
		}

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			Log.i(LOG_TAG, "onResume()..................");
		}

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			Log.i(LOG_TAG, "onStart()..................");
		}

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			Log.i(LOG_TAG, "onStop()..................");
		}

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.i(LOG_TAG, "onDestroy()..................");
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
   
        Log.i(LOG_TAG, "onCreate.................");
        
        
        TouchListener  touchListener = new TouchListener();
        
        imageViews[0] = (ImageView) findViewById(R.id.imageview0);
		imageViews[1] = (ImageView) findViewById(R.id.imageview1);
		imageViews[2] = (ImageView) findViewById(R.id.imageview2);
		imageViews[3] = (ImageView) findViewById(R.id.imageview3);
		imageViews[4] = (ImageView) findViewById(R.id.imageview4);
		imageViews[5] = (ImageView) findViewById(R.id.imageview5);
		
		item0 = (ImageButton) this.findViewById(R.id.item0);
		item1 = (ImageButton) this.findViewById(R.id.item1);
		item2 = (ImageButton) this.findViewById(R.id.item2);
		item3 = (ImageButton) this.findViewById(R.id.item3);
		item4 = (ImageButton) this.findViewById(R.id.item4);
		item5 = (ImageButton) this.findViewById(R.id.item5);
		item0.setOnTouchListener(touchListener);
		item1.setOnTouchListener(touchListener);
		item2.setOnTouchListener(touchListener);
		item3.setOnTouchListener(touchListener);
		item4.setOnTouchListener(touchListener);
		item5.setOnTouchListener(touchListener);
		
		
		progressBars[0] = (ProgressBar)this.findViewById(R.id.progressBar0);
		progressBars[1] = (ProgressBar)this.findViewById(R.id.progressBar1);
		progressBars[2] = (ProgressBar)this.findViewById(R.id.progressBar2);
		progressBars[3] = (ProgressBar)this.findViewById(R.id.progressBar3);
		progressBars[4] = (ProgressBar)this.findViewById(R.id.progressBar4);
		progressBars[5] = (ProgressBar)this.findViewById(R.id.progressBar5);
		
		
		
		
       
			/**
			 * 读取store.plist文件获取信息
			 */
			getInformation();
			/**
			 * 测试自动更新
			 */
    		this.setOnclickListener();
			
			this.getPictureFromSDCard();//从文件夹中取得杂志封面 
			//this.checkUpdate();//启动检测更新线程
			
			DailyDao dao = new MyDataBase(getApplicationContext());
			List<Book> books = dao.findAllDaily();
			
		shezhi = (ImageButton)this.findViewById(R.id.ib_Subscribe);
		shezhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Project1Activity.this, PushActivity.class);
				startActivity(intent);
			}
		});
			
			
		
    }

	private void setOnclickListener() {
		item0.setOnClickListener(new DownLoadListener(stores.get(0),0));
		item1.setOnClickListener(new DownLoadListener(stores.get(1),1));
		item2.setOnClickListener(new DownLoadListener(stores.get(2),2));
		item3.setOnClickListener(new DownLoadListener(stores.get(3),3));
		item4.setOnClickListener(new DownLoadListener(stores.get(4),4));
		item5.setOnClickListener(new DownLoadListener(stores.get(5),5));
	}

	private void getInformation() {
		try {
			File file = new File(new FileUtil().getSDPATH() + "SportsDaily/"
					+ "store.plist");
			InputStream inputStream;
			inputStream = new FileInputStream(file);
			stores = DOMMagazineService.getStores(inputStream);
			/*for (Store store : stores) {
				System.out.println(store.getCover_URL());
			}*/
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * 当时间未到时候启动检测线程
     * 当更新时间到达时候启动更新线程
     */
	private void checkUpdate() {
		Date date = new Date();
		int hour = date.getHours();
		int minute = date.getMinutes();
		/*System.out.println("hour=="+hour);
		System.out.println("minute"+minute);*/
		if(minute<UPDATE_TIME){
			new Thread(new CheckThead()).start();
//			System.out.println("监控线程启动。。。。");
		}else{
			new GetPlist().getPlist("http://www.softechallenger.com/media/store.plist");
			new Thread(myThead).start();
			/*System.out.println("时间到");*/
		}
	}

	
	/**
	 * 从网络中获得最新的杂志信息
	 */
	private void writePicture2SDCard() {
		/**
		 * 将图片写到SDCard
		 */
		for(int i=0;i<6;i++){
			 try {
				/* System.out.println("stores.get(i)==="+(stores.get(i)==null));*/
				 inStream = new GetPicture().getInputStreamFromUrl(stores.get(i).getCover_URL()); 
				 //System.out.println(stores.get(i).getCover_URL());
				 //System.out.println(inStream==null);
				 FileUtil fileUtil = new FileUtil();
				 int flag = stores.get(i).getCover_URL().lastIndexOf("/");
				 String name = stores.get(i).getCover_URL().substring(flag+1);
				 //File file =fileUtil.write2SDFromInput("project", name, inStream);
				try {
					/*System.out.println("ddd");*/
					fileUtil.write2SDCard(inStream, "SportsDaily", name);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}








	/**
	 * 从文件中获取杂志封面并且更新UI
	 */
	private void getPictureFromSDCard() {
		InputStream inputStream = null;
		for(int i=0;i<6;i++){
			/* System.out.println("stores.get(i)==="+(stores.get(i)==null));*/
			int flag = stores.get(i).getCover_URL().lastIndexOf("/");
			 String name = stores.get(i).getCover_URL().substring(flag+1);
			File file = new File(new FileUtil().getSDPATH() + "SportsDaily/"+ name);
			
			try {
				inputStream = new FileInputStream(file);
				imageViews[i].setBackgroundDrawable(new BitmapDrawable(inputStream));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @author SW
	 * Handler异步更新UI
	 * 获得从线程得到的更新信息
	 * 
	 *
	 */
	private class MyHandler extends Handler{
		int i=0;
		@Override
		public void handleMessage(Message msg) {//此方法在ui线程运行
		
			switch (msg.what) {
			case MSG_SUCCESS:	
				
				//imageViews[i].setImageBitmap((Bitmap) msg.obj);
				imageViews[i].setBackgroundDrawable((Drawable) msg.obj);
				i++;
				
				break;
			case MSG_FAILURE:
				Toast.makeText(Project1Activity.this, "更新失败", Toast.LENGTH_LONG);
				break;
			case CHECK_UPDATE:
				
				Toast.makeText(Project1Activity.this, "有更新", Toast.LENGTH_LONG);
			
			case DOWN_ProgressDialog:
				progressDialog.dismiss();
				break;
			case CHUSHIHUA:
				
				new GetPlist().getPlist("http://www.softechallenger.com/media/store.plist");
				getInformation();
				setOnclickListener();
				writePicture2SDCard();
				getPictureFromSDCard();
				break;
			default:
				break;
			}
			
		}
	
		
	}

	/**
	 * 更新UI线程
	 */
	private class MyThead implements Runnable{

		int i=0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			if(NetHelper.IsHaveInternet(getApplicationContext())){
				Project1Activity.this.writePicture2SDCard();
				while(i<6){
					int flag = stores.get(i).getCover_URL().lastIndexOf("/");
					 String name = stores.get(i).getCover_URL().substring(flag+1);
					File file = new File(new FileUtil().getSDPATH() + "SportsDaily/"+ name);
					InputStream inputStream = null;
					try {
					inputStream = new FileInputStream(file);
					} catch (Exception e) {
						// TODO: handle exception
					}
					if(inputStream!=null){
						BitmapDrawable bitmapDrawable = new BitmapDrawable(inputStream);
						myHandler.obtainMessage(MSG_SUCCESS, bitmapDrawable).sendToTarget();//获取图片成功，向ui线程发送MSG_SUCCESS标识和bitmap对象  
						i++;
					
					}else{
						myHandler.obtainMessage(MSG_FAILURE).sendToTarget();//获取图片失败  
						
						return;
					}
					
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}


	
    
     
	
	/**
	 * UI事件监听器
	 * @author SW
	 *
	 */
	class DownLoadListener implements OnClickListener{

		private Store store;
		private int no;
		
		public DownLoadListener(Store store,int no) {
			super();
			this.store = store;
			this.no = no;
		}

		@Override
		public void onClick(View v) {
			
			DailyDao dao = new MyDataBase(getApplicationContext());
			if(!dao.haveDaily(store.getRelease_date())){
				dao.close();
				MagazineTask magazineTask = new MagazineTask(Project1Activity.this, store,this.no);
				magazineTask.execute(store.getDownload_URL());
			}else{
				dao.close();
				Toast.makeText(getApplicationContext(), "您已经下载过本杂志了", Toast.LENGTH_LONG).show();
				TabActivity.tabs.setCurrentTab(1);
			}
		}
	}
	

	

	/**
	 * 检测线程 
	 * 每个1分钟去检测是否到达更新杂志的事件
	 * @author SW
	 *
	 */
	class CheckThead implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				
				if(new Date().getMinutes()>UPDATE_TIME){
					new Thread(myThead).start();
					//Toast.makeText(Project1Activity.this, "现在有更新", Toast.LENGTH_LONG).show();
					break;
				}
				try {
					Thread.sleep(600000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
/* private class TheFirstGetServiceThead implements Runnable{

	 int item = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(item<=5){
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item++;
			if(item==5){
				myHandler.obtainMessage(DOWN_ProgressDialog).sendToTarget();
			}else if(3==item){
				myHandler.obtainMessage(CHUSHIHUA).sendToTarget();
			}
		}
	}
	 
 }*/
	
 class TouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				((ImageButton) v)
						.setBackgroundResource(R.drawable.download_select);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				((ImageButton) v).setBackgroundResource(R.drawable.download);
			}
		
			return false;
		}

	}
 
 
 class MagazineTask extends AsyncTask<String, Integer, String>{

	 ProgressBar progressBar;
	 Context context ;
	 Store maga;
	 Boolean debug = false;
	 String zipURI;

	public MagazineTask(Context context, Store maga,int i) {
		super();
		this.context = context;
		this.maga = maga;
		this.progressBar = progressBars[i];
	
		
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.setVisibility(View.VISIBLE);
	}




	@Override
	protected String doInBackground(String... params) {
		// 第一个参数就是URL

		try {
			InputStream inputStream = null;
			int length;

			if (debug) {
				System.out.println(params[0]); // 打印一下
			}

			URL url = new URL(params[0]);
			URLConnection connection = url.openConnection();
	
			inputStream = connection.getInputStream();
			
			length = connection.getContentLength();

			// 生成一个文件，以备存入SD卡		
			/*String savePAth = Environment.getExternalStorageDirectory()
					+ "/SportsDaily/";*/
			String savePAth = Environment.getExternalStorageDirectory()
			+ "/SportsDaily/";
			File tFile = new File(savePAth);
			// FileOutputStream fos = new FileOutputStream(tFile);
			if (!tFile.exists()) {
				tFile.mkdirs();
			}

			if (debug) {
				System.out.println("准备开始写入"); // 打印一下
			}
			// 开始写入文件
			int len = 0;
			int nowSize = 0;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			while ((len = inputStream.read(buffer)) != -1) {
				if (debug) {
					System.out.println("写入"); // 打印一下
				}
				bos.write(buffer, 0, len);
				bos.flush();
				nowSize += len;
				// 如果知道响应的长度，调用publishProgress（）更新进度,传递进度给onProgressUpdate（...）方法
				publishProgress((int) ((nowSize / (float) length) * 100));
			}
			// publishProgress((int) ((count / (float) length) * 100));

			// 写入SD卡
			FileOutputStream fos = new FileOutputStream(
					Environment.getExternalStorageDirectory()
							+ "/SportsDaily/" + maga.getFolder() + ".zip");
			fos.write(bos.toByteArray());
			fos.flush();
			fos.close();

			// 解压缩
			File zipFile = new File(
					Environment.getExternalStorageDirectory()
							+ "/SportsDaily/" + maga.getFolder() + ".zip");
			System.out.println("ZipFile!="+zipFile.getAbsolutePath());
			XZip.UnZipFolder(Environment.getExternalStorageDirectory()
					+ "/SportsDaily/" + maga.getFolder() + ".zip",
					Environment.getExternalStorageDirectory()
							+ "/SportsDaily/" + maga.getFolder());
			zipURI = Environment.getExternalStorageDirectory()
					+ "/SportsDaily/" + maga.getFolder() +"/"+ maga.getFolder() +"/index.html";
			
			zipFile.delete();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			 Toast.makeText(context, "网站链接异常", 1).show();
			//pdialog.cancel();
			return "UnknownHost";
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "您的网络连接异常", 1).show();
			Thread.interrupted();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "BAD";
	}	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		System.out.println("result==="+result);
		if (result == "UnknownHost") {
			Toast.makeText(context, "网站链接异常", 1).show();
			//pdialog.cancel();
		} else if(result == "BAD"){
			
			// 存入数据库
			DailyDao dao = new MyDataBase(Project1Activity.this);
			Book daily = new Book();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 读取PNG
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(Environment.getExternalStorageDirectory()+"/SportsDaily/"+maga.getId().toLowerCase()+".png");
				System.out.println("File:"+Environment.getExternalStorageDirectory()+"/SportsDaily/"+maga.getId().toLowerCase()+".png");
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				bos.flush();
				bos.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(zipURI);
			daily.setContentURL(zipURI);
			daily.setCover(bos.toByteArray());
			daily.setCreateTime(maga.getRelease_date());
			dao.insertDaily(daily);
			dao.close();
			
			Toast.makeText(context, "杂志下载成功", 1).show();
		}
		
	}

	
	
  


	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
		if(values[0]==100){
			Toast.makeText(context, "下载完成", 1).show();
			progressBar.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
	 
 }
	
	
    
}