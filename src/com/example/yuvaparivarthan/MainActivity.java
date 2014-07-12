package com.example.yuvaparivarthan;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends SherlockActivity {
	
	private static final String TAG = "sendData";
	EditText dateEntry;
	EditText LocationEntry;
	EditText AttendanceEntry;
	EditText CampCodeEntry;
	EditText feesCollectedEntry;
	Button submit;
	final String URL = "/volley/resource/12";
    BroadcastReceiver broadcastReceiver;
    
        
    
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dateEntry = (EditText) findViewById(R.id.dateEntry);
		LocationEntry = (EditText) findViewById(R.id.locationEntry);
		AttendanceEntry = (EditText) findViewById(R.id.AttendanceEntry);
		CampCodeEntry = (EditText) findViewById(R.id.CampCodeEntry);
		feesCollectedEntry = (EditText) findViewById(R.id.FeesCollectedEntry);
		
		final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnReceiver,intentFilter);

        
        
		DatabaseHandler db = new DatabaseHandler(MainActivity.this);				
		List<Data> datas = db.getAllData();
		if(datas.size() > 0) {
			Toast toast = Toast.makeText(MainActivity.this, "There are items ready to be Synched :)", Toast.LENGTH_SHORT);
			toast.show();
		}
		submit = (Button) findViewById(R.id.submit);
		
    			submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String DateField = dateEntry.getText().toString();
				String LocationField = LocationEntry.getText().toString();
				String AttendanceField = AttendanceEntry.getText().toString();
				String feesCollectedField = feesCollectedEntry.getText().toString(); 
				String CampCodeField = CampCodeEntry.getText().toString();
				String FeesCollectedEntry = feesCollectedEntry.getText().toString();
				
				//adding required validations.
				if(DateField.matches("[0-9][0-9]/[0-1][0-2]/[0-9][0-9][0-9][0-9]")) {
					System.out.println("matches");
				} else {
					Toast toast = Toast.makeText(MainActivity.this, "Date Doesn't seem to match :(", Toast.LENGTH_SHORT);
					toast.show();
					return;				
				}
				
				
				if(LocationField.matches("[a-zA-Z]*[0-9]+[a-zA-Z]")) {
					Toast toast = Toast.makeText(MainActivity.this, "Location Doesn't Match :(", Toast.LENGTH_SHORT);
					toast.show();
					return;
				} else
				{
					System.out.println("Location matches");
				}
				
				if(AttendanceField.matches("[1-9][1-9]")) {
					System.out.println("matches Attendance");
				} else {
					Toast toast = Toast.makeText(MainActivity.this, "Attendance Doesn't Match :(", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				if(CampCodeField.matches("[a-zA-Z0-9]{10}")) {
					System.out.println("matches Camp Code Field");
				} else {
					Toast toast = Toast.makeText(MainActivity.this, "Camp Code Doesn't Match :(", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				if(feesCollectedField.matches("[1-9][0-9]{1,8}")) {
					System.out.println("matches Fees Collected Field");
				} else {
					Toast toast = Toast.makeText(MainActivity.this, "Fees Collected Doesn't Match :(", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				ConnectionDetector cd = new ConnectionDetector(getApplicationContext());				 
				Boolean isInternetPresent = cd.isConnectingToInternet();
				if(isInternetPresent) {
					System.out.println("Logging:click");
					new Thread(new SendDataToServer()).start();
					
				}
				else {
					Log.d("Insert: ", "Inserting .."); 
					Data data = new Data(dateEntry.getText().toString(), LocationEntry.getText().toString(), AttendanceEntry.getText().toString(), CampCodeEntry.getText().toString(), feesCollectedEntry.getText().toString());
					DatabaseHandler db = new DatabaseHandler(MainActivity.this);
					db.addData(data);		
					List<Data> datas = db.getAllData();
					System.out.println("list count:"+datas.size());
					for(Data d : datas) {
						
						Log.d("Camp Code",d.getCampCode());
					}
					
				}
			}
		});
		
	}
	
	
	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

		 	Bundle extras = intent.getExtras();

            NetworkInfo info = (NetworkInfo) extras
                    .getParcelable("networkInfo");

            State state = info.getState();
            Log.d("InternalBroadcastReceiver", info.toString() + " "
                    + state.toString());

            if (state == State.CONNECTED) {
            	DatabaseHandler db = new DatabaseHandler(MainActivity.this);
    			List<Data> datas = db.getAllData();
            	if(datas.size() > 0) {
            		new Thread(new AddRemainingData()).start();
            	}
                

            } else {

                

            }

        }
    };
    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case R.id.about_refresh:
			new Thread(new AddRemainingData()).start();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		 com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		 inflater.inflate(R.menu.main, menu);	
		 super.onCreateOptionsMenu(menu);		    
		 return true;
	}


	class AddRemainingData implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DatabaseHandler db = new DatabaseHandler(MainActivity.this);
			List<Data> datas = db.getAllData();
			for(final Data b : datas) {
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.sendDataToServer(b.getDate(), b.getLocation(),b.getAttendance(), b.getCampCode(), b.feesCollected);
				try {
					if(json.getString("success")!=null) {
						String res = json.getString("success");
						System.out.println("in here");
						System.out.println("result status:"+res);
						if(Integer.parseInt(res) == 1) {
							MainActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									
									Toast toast = Toast.makeText(MainActivity.this, "Successfully added entry", Toast.LENGTH_SHORT);
									toast.show();
									DatabaseHandler db = new DatabaseHandler(MainActivity.this);
									db.deleteContact(b);
									
								}
								
							});
							
						}
					} else {
						
					}
				}catch(Exception e) {
					
				}
				
			}
			
			
		}
		
	}
	
	class SendDataToServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("came inside send data to server");
			UserFunctions userFunction = new UserFunctions();
			System.out.println("Date:"+AttendanceEntry.getText().toString());
			JSONObject json = userFunction.sendDataToServer(dateEntry.getText().toString(), LocationEntry.getText().toString(), AttendanceEntry.getText().toString(), CampCodeEntry.getText().toString(), feesCollectedEntry.getText().toString());
			try {
				if(json.getString("success")!=null) {
					String res = json.getString("success");
					System.out.println("in here");
					System.out.println("result status:"+res);
					if(Integer.parseInt(res) == 1) {
						MainActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast toast = Toast.makeText(MainActivity.this, "Successfully added entry", Toast.LENGTH_SHORT);
								toast.show();
							}
							
						});
						
					} else {
						MainActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
							
								Toast toast = Toast.makeText(MainActivity.this, "Error in submitting,caching..", Toast.LENGTH_SHORT);
								toast.show();
							}
							
						});
						Log.d("Insert: ", "Inserting .."); 
						Data data = new Data(dateEntry.getText().toString(), LocationEntry.getText().toString(), AttendanceEntry.getText().toString(), CampCodeEntry.getText().toString(), feesCollectedEntry.getText().toString());
						DatabaseHandler db = new DatabaseHandler(MainActivity.this);
						db.addData(data);
					}
				}
			}catch(Exception e) {
				
			}
		}
		
	}
	
}