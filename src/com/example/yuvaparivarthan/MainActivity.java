package com.example.yuvaparivarthan;


import org.json.JSONObject;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;


public class MainActivity extends SherlockActivity {
	
	EditText dateEntry;
	EditText LocationEntry;
	EditText AttendanceEntry;
	EditText CampCodeEntry;
	EditText feesCollectedEntry;
	Button submit;
	
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
		submit = (Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Logging:click");
				new Thread(new SendDataToServer()).start();
			}
		});
		
	}
	
	class SendDataToServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("came inside send data to server");
			UserFunctions userFunction = new UserFunctions();
			System.out.println("Date:"+AttendanceEntry.getText().toString());
			JSONObject json = userFunction.sendDataToServer(dateEntry.getText().toString(), LocationEntry.getText().toString(), AttendanceEntry.getText().toString(), CampCodeEntry.getText().toString(), feesCollectedEntry.getText().toString());
			
		}
		
	}
	
}