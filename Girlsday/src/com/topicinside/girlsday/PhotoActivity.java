package com.topicinside.girlsday;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhotoActivity extends Activity {
	
	enum SCREEN_MODE {
		FULL,
		NORMAL
	};
	
	public SCREEN_MODE mode = SCREEN_MODE.FULL;
	
	ActionBar actionBar;
	
	private Image item;
	private ImageView itemView;

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

		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.app_name);
		actionBar.hide();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			backDetailItemActivity();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickImage(View view) {
		backDetailItemActivity();
	}
	
	public void backDetailItemActivity() {
		Intent intent = new Intent(this, DetailItemActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
	}

	public class DownLoadImageBitmap extends AsyncTask<ImageView, Integer, Bitmap> {
		
		private static final String DEBUG_TAG = "DownLoadImageBitmap";
		
		private ImageView imageView = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
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
				// Starts the query
				conn.connect();
				int response = conn.getResponseCode();
				Log.d(DEBUG_TAG, "The response is: " + response);
				is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
			}finally {
				if(is != null) {
					is.close();
				}
			}
			return bm;
		}
	}

}
