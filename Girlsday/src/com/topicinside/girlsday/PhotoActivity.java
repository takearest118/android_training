package com.topicinside.girlsday;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class PhotoActivity extends SherlockActivity {
	
	enum SCREEN_MODE {
		FULL,
		NORMAL
	};
	
	public SCREEN_MODE mode = SCREEN_MODE.FULL;
	
	private static final String PREFIX_IMAGE_FILE = "girlsday";
	private static final String SUFFIX_IMAGE_FILE = ".jpg";
	
	private Image item;
	private ImageView itemView;
	private Button saveButton;
	private Bitmap bitmap;
	
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		this.mode = SCREEN_MODE.FULL;
		
		// Get the id of item from the intent
		Intent intent = getIntent();
		item = intent.getExtras().getParcelable("IMAGE");
		itemView = (ImageView) this.findViewById(R.id.item_image);
		itemView.setTag(item.getSource());
		new DownLoadImageBitmap().execute(itemView);
		
		saveButton = (Button) this.findViewById(R.id.photo_save);
		saveButton.setVisibility(View.GONE);

		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.app_name);
		actionBar.hide();
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public void clickImage(View view) {
		if(mode == SCREEN_MODE.FULL) {
			saveButton.setVisibility(View.VISIBLE);
			mode = SCREEN_MODE.NORMAL;
		}else if(mode == SCREEN_MODE.NORMAL) {
			saveButton.setVisibility(View.GONE);
			mode = SCREEN_MODE.FULL;
		}
	}
	
	public void clickSave(View view) {
		Integer epochtime = (int)System.currentTimeMillis();
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path.mkdirs();
		File file = new File(path, PREFIX_IMAGE_FILE + epochtime.toString() + SUFFIX_IMAGE_FILE);
		this.saveBitmapToFileCache(bitmap, file);
	}
	
	private void saveBitmapToFileCache(Bitmap bitmap, File file) {
		OutputStream os = null;
		
		try {
			os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, os);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				os.close();
				Toast.makeText(this.getApplicationContext(), R.string.photo_save_toast, Toast.LENGTH_SHORT).show();
				MediaScannerConnection.scanFile(this,
						new String[] { file.toString() }, null, 
						new MediaScannerConnection.OnScanCompletedListener() {
							
							@Override
							public void onScanCompleted(String path, Uri uri) {
								Log.i("ExternalStorage", "Scanned " + path + ":");
								Log.i("ExternalStorage", "-> uri=" + uri);
							}
						});
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class DownLoadImageBitmap extends AsyncTask<ImageView, Integer, Bitmap> {
		
		private static final String DEBUG_TAG = "DownLoadImageBitmap";
		
		private ImageView imageView = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(PhotoActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setTitle(R.string.loading_title);
			pd.setMessage("Please, wait...");
			pd.setProgress(0);
			pd.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			pd.setProgress(values[0]);
		}

		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
			
			// params comes from the execute() call: params[0] is the url.
			try {
				this.imageView = imageViews[0];
				return downloadUrl((String)this.imageView.getTag());
			}catch(IOException e) {
				return null;
			}
		}

		// onPostExecute displays the results of the AsyncTask
		@Override
		protected void onPostExecute(Bitmap result) {
			this.imageView.setImageBitmap(result);
			bitmap = result;
			pd.dismiss();
		}
		
		private Bitmap downloadUrl(String myurl) throws IOException {
			InputStream is = null;
			Bitmap bm = null;
			
			try {
				URL url = new URL(myurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				pd.setProgress(10);
				// Starts the query
				conn.connect();
				int response = conn.getResponseCode();
				pd.setProgress(60);
				Log.d(DEBUG_TAG, "The response is: " + response);
				is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				pd.setProgress(80);
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
				pd.setProgress(100);
			}finally {
				if(is != null) {
					is.close();
				}
			}
			return bm;
		}
	}

}
