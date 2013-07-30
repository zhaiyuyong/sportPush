package com.zyy.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zyy.jdbc.DailyDao;
import com.zyy.jdbc.MyDataBase;
import com.zyy.po.Book;

public class BooksActivity extends Activity {
	private static final String LOG_TAG = "BooksActivity";
	private int intWidth, intHeight;

	MyHandler myHandler;

	private static final int FLAG1 = 1;
	private static final int FLAG2 = 2;
	private ListView bookList;

	Book[][] books;
	View[] views;
	int ln; // 行数
	int fn; // 最后一行余数

	DailyDao dao;// = new MyDataBase(getApplicationContext());
	ArrayList<Book> mybooks;// = dao.findAllDaily();

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
		/**
		 * 注意 Activity的生命周期
		 */
		init();
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		Log.i(LOG_TAG, "onCreate.................");

		this.init();

	}

	/**
	 * 初始化数据 通过适配器加载到listview
	 */
	private void init() {
		setContentView(R.layout.book_shelf);
		dao = new MyDataBase(getApplicationContext());
		mybooks = dao.findAllDaily();
		dao.close();
		// 在这里设定bookSize，设已知书籍有N本
		int n = mybooks.size();
		System.out.println("mybooks.size()=================" + n);
		fn = (int) n % 3; // 取除3后的余数
		if (fn == 0) { // 取除3后的最大值,如5/3=2
			ln = n / 3;
		} else {
			ln = n / 3 + 1;
		}
		books = new Book[ln][3];
		views = new View[ln];

		Log.i("SPORT", ln + "" + fn);

		// 对book进行赋值
		/*
		 * for (int i = 0; i < ln - 1; i++) { for (int j = 0; j < 3; j++) {
		 * books[i][j] = mybooks.get(i * 3 + j); } }
		 * 
		 * for (int i = 0; i < fn; i++) { books[ln - 1][i] = mybooks.get((ln -
		 * 1) * 3 + fn - 1); }
		 */

		for (int i = 0; i < ln; i++) {
			for (int j = 0; j < 3; j++) {
				if ((i * 3 + j) == n) {
					break;
				} else {
					books[i][j] = mybooks.get(i * 3 + j);
				}
			}
		}

		// 获取显示图书的ListView
		try {
			bookList = (ListView) this.findViewById(R.id.shelf_list);
			bookList.setAdapter(new BookShelfAdapter());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class BookShelfAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (books.length < 5) {
				return 5;
			} else {
				return books.length + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return books[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			System.out.println("getView::" + position);

			View bookView;
			
			
			bookView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.list_book, null);
			if(position<(ln-1)){
				bookAdapt(bookView, R.id.button_click_1, 0, position);
				bookAdapt(bookView, R.id.button_click_2, 1, position);
				bookAdapt(bookView, R.id.button_click_3, 2, position);
			}else if(position==(ln-1)){
				if(fn == 1){
					bookAdapt(bookView, R.id.button_click_1, 0, position);
				}else if(fn == 2){
					bookAdapt(bookView, R.id.button_click_1, 0, position);
					bookAdapt(bookView, R.id.button_click_2, 1, position);
				}else if(fn == 0){
					bookAdapt(bookView, R.id.button_click_1, 0, position);
					bookAdapt(bookView, R.id.button_click_2, 1, position);
					bookAdapt(bookView, R.id.button_click_3, 2, position);
				}
			}
			

			return bookView;
		}

		private void bookAdapt(View bookView, int id, final int col,
				final int raw) {

			final ImageButton imageButton = (ImageButton) bookView
					.findViewById(id);
			try {
				
				Book myBook = books[raw][col];

				final byte[] buffer = myBook.getCover();
				// BitmapFactory.Options opts = new BitmapFactory.Options();
				// opts.inSampleSize = 4;
				imageButton.setImageBitmap(zoomBitmap(getPicFromBytes(buffer,
						null), 100, 160));
				imageButton.bringToFront();
				imageButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						System.out.println("raw==" + raw + "col=" + col);
						System.out.println(books[raw][col].getBid());
						System.out.println(books[raw][col].getCreateTime());

						v.bringToFront();
						imageButton.bringToFront();
						intWidth = v.getWidth();
						intHeight = v.getHeight();

						myHandler = new MyHandler(books[raw][col]
								.getContentURL(), imageButton);
						new Thread(new MyThead()).start();

					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

	}

	/**
	 * 从数据库取二进制图片转化成Bitmap
	 * 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	/**
	 * @param 图片缩放
	 * @param bitmap
	 *            对象
	 * @param w
	 *            要缩放的宽度
	 * @param h
	 *            要缩放的高度
	 * @return newBmp 新 Bitmap对象
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBmp;
	}

	void movePicture(ImageButton imageButton2) {
		// TODO Auto-generated method stub
		intWidth += 6;
		intHeight += 9;
		imageButton2.setLayoutParams(new RelativeLayout.LayoutParams(intWidth,
				intHeight));
	}

	class MyHandler extends Handler {
		String contentURL;
		ImageButton imageButton;

		public MyHandler(String contentURL, ImageButton imageButton) {
			super();
			this.contentURL = contentURL;
			this.imageButton = imageButton;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG1:
				movePicture(imageButton);
				break;
			case FLAG2:
				BooksActivity.this.setContentView(R.layout.big);
				Intent intent = new Intent();
				intent.putExtra("URL", contentURL);
				intent.setClass(BooksActivity.this, ShowActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

	class MyThead implements Runnable {

		@Override
		public void run() {
			while (intWidth <= 480) {

				myHandler.obtainMessage(FLAG1).sendToTarget();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (intWidth >= 480) {
				myHandler.obtainMessage(FLAG2).sendToTarget();
			}
		}
	}
}
