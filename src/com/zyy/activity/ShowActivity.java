package com.zyy.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.Button;

public class ShowActivity extends Activity{

	private ProgressDialog dialog;
	private WebView webView;
	private Button backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.show_book);
		String url = this.getIntent().getStringExtra("URL");
		
		webView = (WebView) this.findViewById(R.id.show_view);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,
					String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				dialog.dismiss();
			}
		});
		
		WebSettings settings = webView.getSettings();
		settings.setDefaultZoom(ZoomDensity.FAR);
		settings.setSavePassword(false);
		settings.setSaveFormData(false);
		settings.setJavaScriptEnabled(true);

		webView.loadUrl("file:///"+url);
		//打开一个转圈的进度条
		dialog = ProgressDialog.show(this,null,"杂志加载中，请稍后..");
		
		backbtn =(Button)this.findViewById(R.id.show_view_back);
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ShowActivity.this, TabActivity.class);
				TabActivity.tabs.setCurrentTab(1);
				startActivity(intent);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		Intent intent = new Intent();
		intent.setClass(ShowActivity.this, TabActivity.class);
		TabActivity.tabs.setCurrentTab(1);
		startActivity(intent);
		this.finish();
		return;
	}
}
