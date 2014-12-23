package com.example.quickreport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maindisplay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void alltwitter(View v){
		tweet("https://twitter.com/search?q=from%3Aayanareports");
	}
	public void nearyou(View v){
		tweet("https://twitter.com/search?q=%23ayanachildlabor%20from%3Aayanareports");
	}
	public void bycategory(View v){
		tweet("https://twitter.com/search?q=%23ayanachildlabor%20from%3Aayanareports");
	}
	public void tweet(String t){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(t));
		startActivity(browserIntent);
	}

}
