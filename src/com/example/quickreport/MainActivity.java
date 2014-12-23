package com.example.quickreport;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity implements LocationListener{
public String[] types={"Bribe","Traffic Violation","Eve Teasing"};
public String selectedType="Bribe";
Location loc;
double latitude;
double longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dispatchTakePictureIntent();
		setContentView(R.layout.activity_main);
LocationManager mlocManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
	//	EditText e1=(EditText)findViewById(R.id.editText2);
	//	e1.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	String mCurrentPhotoPath;

	private File createImageFile() throws IOException {
	// Create an image file name
	//    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_test";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath =  image.getAbsolutePath();
	    return image;
	}
	static final int REQUEST_TAKE_PHOTO = 1;

	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }
	    }
	}
	public void report(View v){
		EditText e1=(EditText)findViewById(R.id.editText2);
	//	e1.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
	//	e1.setText(mCurrentPhotoPath);
		AsyncPost.post(
                "http://ayana-report.herokuapp.com/posts", //url
                e1.getText().toString(), //description e1.getText().toString()
                mCurrentPhotoPath, //filepath
//                adding a file like: new File("/home/alok/Desktop/kentbrockman1.jpg"), also works
                latitude, //lattitude
                longitude //longitude
            );
		//Intent i=new Intent(MainActivity.this,MainDisplay.class);
		//startActivity(i);
		while(!AsyncPost.done);
		Intent i=new Intent(MainActivity.this,MainDisplay.class);
		startActivity(i);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        ImageView v1=(ImageView)findViewById(R.id.imageView1);
	        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
	        int h,w;
	        h=bitmap.getHeight();
	        w=bitmap.getWidth();
	      //  h=(h>w)?h:w;
	        bitmap=bitmap.createScaledBitmap(bitmap, 500, 500, true);
	       
	      //bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter)
	      //  mImageView.setImageBitmap(bitmap);
	      //   LayoutParams params=
	      // v1.setLayoutParams(params);
	      //  v1.getLayoutParams().width=h;
	      //  v1.getLayoutParams().height=h;
	        v1.setImageBitmap(bitmap);

	    
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
loc=location;
latitude=loc.getLatitude();
longitude=loc.getLongitude();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


}
