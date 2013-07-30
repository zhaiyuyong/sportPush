package com.zyy.activity;




import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PushActivity extends Activity {
	
	private static final String LOG_TAG = "PushActivity";
	 String message;
	
	private String mDeviceID;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push);
        
        Log.i(LOG_TAG, "onCreate.....................");
        
        
         message = this.getIntent().getStringExtra("message");
        this.getIntent().removeExtra("message");
        Log.i(LOG_TAG, "message in onCreate==="+message);
        
        mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);         
  	  	((TextView) findViewById(R.id.target_text)).setText(mDeviceID+message);
 
  	  	final Button startButton = ((Button) findViewById(R.id.start_button));
  	  	final Button stopButton = ((Button) findViewById(R.id.stop_button));
  	  	startButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		    	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
		    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
		    	editor.commit();
				PushService.actionStart(getApplicationContext());		        
		  		startButton.setEnabled(false);
		  		stopButton.setEnabled(true);				
			}
		});
  	  	stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PushService.actionStop(getApplicationContext());		        								
		  		startButton.setEnabled(true);
		  		stopButton.setEnabled(false);				
			}
		});
  	  	if(message!=null){
  	  		Toast.makeText(PushActivity.this, message, Toast.LENGTH_LONG).show();
  	  	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	message = this.getIntent().getStringExtra("message");
        
        Log.i(LOG_TAG, "message in onResume==="+message);
  	  	
    	Log.i(LOG_TAG, "onResume..............................");
    	
  	  	SharedPreferences p = getSharedPreferences(PushService.TAG, MODE_PRIVATE);
  	  	boolean started = p.getBoolean(PushService.PREF_STARTED, false);
  	  	
  		((Button) findViewById(R.id.start_button)).setEnabled(!started);
  		((Button) findViewById(R.id.stop_button)).setEnabled(started);
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(LOG_TAG, "onDestroy..............................");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(LOG_TAG, "onPause..............................");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i(LOG_TAG, "onRestart..............................");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(LOG_TAG, "onStart..............................");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(LOG_TAG, "onStop..............................");
	}
    
    
    
    
    
    
    
    
    
}
