package com.example.yuvaparivarthan;

import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockActivity;

public class Report extends SherlockActivity {
	
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		webview = (WebView) findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://yuva.byethost5.com/php/charting/libchart/demo/Jhol.php");
	}
	
	

}
