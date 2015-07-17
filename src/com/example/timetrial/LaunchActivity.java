package com.example.timetrial;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class LaunchActivity extends ActionBarActivity {
	
	TextView text;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_launch);

		text= (TextView)findViewById(R.id.receipt_l);
		
		new CountDownTimer(3000,1000){
			public void onTick(long illisUntilFinished) {
				if((illisUntilFinished/1000)>2)
				{text.setText("Loading.");}
				if((illisUntilFinished/1000)>1  && (illisUntilFinished/1000)==2)
				{text.setText("Loading..");}
				if((illisUntilFinished/1000)>0 && (illisUntilFinished/1000)==1)
				{text.setText("Loading...");}
			}
			public void onFinish() {
				text.setText("Click here to continue");
				}
		}.start();
		
		text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i=new Intent(getApplicationContext(),StartMenuActivity.class);
				startActivity(i);
			}
		});
		
	}
}
