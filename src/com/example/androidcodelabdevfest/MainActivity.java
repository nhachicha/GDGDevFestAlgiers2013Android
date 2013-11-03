package com.example.androidcodelabdevfest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidcodelabdevfest.io.UploadStream;

public class MainActivity extends Activity implements OnClickListener {
	private final int REQUEST_IMAGE_FROM_GALLERY = 0;
	private final int REQUEST_IMAGE_FROM_CAMERA = REQUEST_IMAGE_FROM_GALLERY + 1;

	Button mBtnImgFromGallery;
	Button mBtnImgFromCamera;
	Button mBtnSend;
	ImageView mImgToUpload;
	EditText mTxtDescription;

	private String mFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnImgFromGallery = (Button) findViewById(R.id.btnGallery);
		mBtnImgFromCamera = (Button) findViewById(R.id.btnCamera);
		mBtnSend = (Button) findViewById(R.id.btnSend);
		mImgToUpload = (ImageView) findViewById(R.id.imgToUpload);
		mTxtDescription = (EditText) findViewById(R.id.txtDescription);

		mBtnImgFromGallery.setOnClickListener(this);
		mBtnImgFromCamera.setOnClickListener(this);
		mBtnSend.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.action_list).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Intent intent = new Intent (MainActivity.this, 
						ListOfProducts.class);
				startActivity(intent);
				return false;
			}
		});
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGallery: {
			onBtnGalleryClicked();
			break;
		}

		case R.id.btnCamera: {
			onBtnCameraClicked();
			break;
		}

		case R.id.btnSend: {
			onBtnSendClicked();
			break;
		}
		}
	}

	private void onBtnGalleryClicked() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media
				.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
	}

	private void onBtnCameraClicked() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, REQUEST_IMAGE_FROM_CAMERA);// may not
																	// work
																	// under
																	// emulator,
																	// TODO add
																	// intent
																	// chooser
																	// dialog
																	// (check)
	}

	private void onBtnSendClicked() {
		if (!TextUtils.isEmpty(mTxtDescription.getText().toString())
				&& !TextUtils.isEmpty(mFilePath)) {
			// upload
			UploadStream.uploadFile(mFilePath, mTxtDescription.getText().toString());

		} else {
			Toast.makeText(this, "We need a photo and a description",
					Toast.LENGTH_LONG).show();
		}
	}

	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (Activity.RESULT_OK == resultCode) {
			if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {

				if (null != data) {
					Uri uri = data.getData();

					Cursor cursor = getContentResolver()
							.query(uri,
									new String[] { android.provider.
									MediaStore.Images.ImageColumns.DATA },
									null, null, null);
					cursor.moveToFirst();

					final String imageFilePath = cursor.getString(0);

					Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
					Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 200, 200,
							true);

					mImgToUpload.setImageBitmap(scaled);
					bitmap.recycle();
					
					mFilePath = imageFilePath;

				}

			} else if (requestCode == REQUEST_IMAGE_FROM_CAMERA) {

				if (null != data) {

				}

			} else {
				// no such code
			}
		}
	}
}
