package com.example.timetrial;

import java.io.FileOutputStream;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore;

public class AddActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private String file_image="myimage";
	private String string;
	DisplayActivity n;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add);
		
		ImageButton savebtn=(ImageButton)findViewById(R.id.save);
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				EditText edate=(EditText)findViewById(R.id.date);
				EditText eamount=(EditText)findViewById(R.id.amount);
				EditText edescription=(EditText)findViewById(R.id.description);
				EditText ereceipt=(EditText)findViewById(R.id.receipt);
				
				if(edate.getText().toString()!=""&& eamount.getText().toString()!="" &&  edescription.getText().toString()!="" && ereceipt.getText().toString()!="" )
				{
					saveDataToContactDb(edate.getText().toString(),Float.parseFloat(eamount.getText().toString()),edescription.getText().toString(),ereceipt.getText().toString());
					//n.count=1;
				}
			}});
		
		
		ImageButton camera_button=(ImageButton)findViewById(R.id.camera);
		
		camera_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    
				    Object MEDIA_TYPE_IMAGE = null;
					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
				    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

				    // start the image capture Intent
				    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				    try{
				    FileOutputStream outputStream_image = openFileOutput(file_image, MODE_WORLD_READABLE);
					outputStream_image.write(string.getBytes());
					outputStream_image.close();
					Toast.makeText(getBaseContext(),"location of image saved",Toast.LENGTH_SHORT).show();
				    }catch(Exception e){
				    	 e.printStackTrace();
				    }
				    //finish();
				}

			private Uri getOutputMediaFileUri(Object MEDIA_TYPE_IMAGE) {
				// TODO Auto-generated method stub
				return null;
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void saveDataToContactDb(String date,float amount,String description,String receipt)
	{
		DbHelper dbOpenHelper= new DbHelper(getApplicationContext());
		SQLiteDatabase database=dbOpenHelper.getWritableDatabase();
		if(database!=null)
		{
			ContentValues data=new ContentValues();
			data.put(Constants.DATE_NAME, date);
			data.put(Constants.AMOUNT_NAME,amount);
			data.put(Constants.DESCRIPTION_NAME, description);
			data.put(Constants.RECEIPT_NAME,receipt);
			database.insert(Constants.TABLE_NAME,null,data);
			database.close();
			Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();		
		}
	}
}
