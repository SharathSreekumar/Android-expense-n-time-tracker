package com.example.timetrial;

import java.io.FileOutputStream;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

public class AddexActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private String file_image="myimage",id;
	private String string;
	private DBHelperex mHelper;
	private SQLiteDatabase dataBase;
	private boolean isUpdate;
	
	ImageButton savebtn,camerabtn;
	EditText exdate,exdescription,examount,exreceipt;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add);
		
		ImageButton savebtn=(ImageButton)findViewById(R.id.save);
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				exdate=(EditText)findViewById(R.id.date);
				examount=(EditText)findViewById(R.id.amount);
				exdescription=(EditText)findViewById(R.id.description);
				exreceipt=(EditText)findViewById(R.id.receipt);
				
				if(exdate.getText().toString()!="" && examount.getText().toString()!="" &&  exdescription.getText().toString()!="" && exreceipt.getText().toString()!="" )
				{
					saveData();
				}
				else
				{
					AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddexActivity.this);
					alertBuilder.setTitle("Invalid Data");
					alertBuilder.setMessage("Please, Enter valid data");
					alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
							
						}
					});
					alertBuilder.create().show();
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
	
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(DBHelperex.KEY_DATE,exdate.getText().toString());
		values.put(DBHelperex.KEY_DESCRIPTION,exdescription.getText().toString());
		values.put(DBHelperex.KEY_AMOUNT,examount.getText().toString());
		values.put(DBHelperex.KEY_RECEIPT,exreceipt.getText().toString());
		
		System.out.println("");
		if(isUpdate)
		{    
			//update database with new data 
			dataBase.update(DBHelperex.TABLE_NAME, values, DBHelperex.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DBHelperex.TABLE_NAME, null, values);
		}
		//close database
		dataBase.close();
		Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}
}
