package com.example.timetrial;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DisplaytActivity extends Activity {

	private DBHelpert mHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> keyId = new ArrayList<String>();
	private ArrayList<String> task = new ArrayList<String>();
	private ArrayList<String> date = new ArrayList<String>();
	private ArrayList<String> time = new ArrayList<String>();

	private ListView taskList;
	private ImageButton add;
	
	private AlertDialog.Builder build ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_displayt);

		taskList = (ListView) findViewById(R.id.taskList);

		mHelper = new DBHelpert(this);
		add=(ImageButton)findViewById(R.id.addt);
		
		add.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TimedTrialActivity.class);
				i.putExtra("update", false);
				startActivity(i);
			}
		});
		
		//click to update data
        taskList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent i = new Intent(getApplicationContext(), TimedTrialActivity.class);
                
                i.putExtra("Task", task.get(arg2));
                i.putExtra("Date", date.get(arg2));
                i.putExtra("Time", time.get(arg2));
                i.putExtra("ID", keyId.get(arg2));
                i.putExtra("update", true);
                startActivity(i);

            }
        });
		
		//long click to delete data
				taskList.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							final int arg2, long arg3) {

						build = new AlertDialog.Builder(DisplaytActivity.this);
						build.setTitle("Delete " + task.get(arg2) + " "+ date.get(arg2)+" "+time.get(arg2));
						build.setMessage("Do you want to delete ?");
						build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int which) {

										Toast.makeText(
												getApplicationContext(),task.get(arg2) + " "+ date.get(arg2) +" "+ time.get(arg2) + " is deleted.", 3000).show();

										dataBase.delete(DBHelpert.TABLE_NAME,DBHelpert.KEY_ID + "="+ keyId.get(arg2), null);displayData();
										dialog.cancel();
									}
								});
						build.setNegativeButton("No",new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,int which) {
										dialog.cancel();
									}
								});
						AlertDialog alert = build.create();
						alert.show();

						return true;
					}
				});
		
	}
	
	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}
	
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "+ DBHelpert.TABLE_NAME, null);

		keyId.clear();
		task.clear();
		time.clear();
		if (mCursor.moveToFirst()) {
			do {
				keyId.add(mCursor.getString(mCursor.getColumnIndex(DBHelpert.KEY_ID)));
				task.add(mCursor.getString(mCursor.getColumnIndex(DBHelpert.KEY_TASK)));
				date.add(mCursor.getString(mCursor.getColumnIndex(DBHelpert.KEY_DATE)));
				time.add(mCursor.getString(mCursor.getColumnIndex(DBHelpert.KEY_TIME)));

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(DisplaytActivity.this,keyId, task, date, time);
		taskList.setAdapter(disadpt);
		mCursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.displayt, menu);
		return true;
	}
}
