package com.example.yuvaparivarthan;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;

public class Logging extends SherlockActivity {

	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log);
		listView = (ListView) findViewById(R.id.listview);
		DatabaseHandler db = new DatabaseHandler(Logging.this);				
		final List<Data> datas = db.getAllData();
		String [] campCode = new String[datas.size()];
		List<String> codeCamps = new ArrayList<String>();
		for(Data d: datas) {
			System.out.println("first data "+d.getCampCode());
			codeCamps.add(d.getCampCode());
		}
		
		final List<String> FinalCampCode = codeCamps;
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Logging.this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, FinalCampCode);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				 int itemPosition     = position;
                 
				 AlertDialog.Builder adb=new AlertDialog.Builder(Logging.this);
			        adb.setTitle("Delete?");
			        adb.setMessage("Are you sure you want to delete " + position);
			        final int positionToRemove = position;
			        adb.setNegativeButton("Cancel", null);
			        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {			            
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							FinalCampCode.remove(positionToRemove);
							DatabaseHandler db = new DatabaseHandler(Logging.this);
							System.out.println("count is "+datas.size());
							db.deleteContact(datas.get(positionToRemove));
			                adapter.notifyDataSetChanged();
						}});
			        adb.show();
                 
			}
			
		});
	}

}
