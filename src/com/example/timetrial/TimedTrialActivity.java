package com.example.timetrial;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Build;

public class TimedTrialActivity extends Activity {

	private ImageButton set;
	private EditText edit_task,edit_date,edit_time;
	private DBHelpert mHelper;
	private SQLiteDatabase dataBase;
	private String id,ttask,tdate,ttime;
	private boolean isUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_timed_trial);
		
		set = (ImageButton)findViewById(R.id.set);
		
		set.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edit_task=(EditText)findViewById(R.id.editText1);
					edit_date=(EditText)findViewById(R.id.editText2);
					edit_time=(EditText)findViewById(R.id.editText3);
				
					if(edit_task.getText().toString()!="" && edit_date.getText().toString()!="" && edit_time.getText().toString()!="")
						{
							saveData();
						}
					else
						{
							AlertDialog.Builder alertBuilder=new AlertDialog.Builder(TimedTrialActivity.this);
							alertBuilder.setTitle("Invalid Data");
							alertBuilder.setMessage("Please, Enter valid data");
							alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
									
								}
							});
							alertBuilder.create().show();
						}				
				}
			});
        
        mHelper=new DBHelpert(this);	
	}
	
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(DBHelpert.KEY_TASK,edit_task.getText().toString());//ttask);
		values.put(DBHelpert.KEY_DATE,edit_date.getText().toString());//tdate );
		values.put(DBHelpert.KEY_TIME,edit_time.getText().toString());//ttime );
		
		System.out.println("");
		if(isUpdate)
		{    
			//update database with new data 
			dataBase.update(DBHelpert.TABLE_NAME, values, DBHelpert.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DBHelpert.TABLE_NAME, null, values);
		}
		//close database
		dataBase.close();
		Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timed_trial, menu);
		return true;
	}
}
