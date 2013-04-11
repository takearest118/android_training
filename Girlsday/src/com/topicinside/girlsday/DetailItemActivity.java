package com.topicinside.girlsday;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailItemActivity extends Activity {

	private Image item;
	
	private ImageView itemView;
	private TextView itemTitle;
	private TextView writerDate;
	private TextView writerName;
	private ImageView writerPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detail_item);

		// Get the id of item from the intent
		Intent intent = getIntent();
		item = intent.getExtras().getParcelable("IMAGE");
		itemView = (ImageView) this.findViewById(R.id.item_image);
		
		itemTitle = (TextView) this.findViewById(R.id.item_title);
		itemTitle.setText(item.getName());
		
		writerDate = (TextView) this.findViewById(R.id.writer_date);
		writerDate.setText(item.getCreatedtime());
		
		/*
		writerName = (TextView) this.findViewById(R.id.writer_name);
		writerName.setText(item.getWriterName());
		*/
		
		itemView.setTag(item.getSource());
		new DownLoadImageBitmap().execute(itemView);
		
		/*
		writerPhoto.setTag(item.getWriterPhoto());
		new DownLoadImageBitmap().execute(writerPhoto);
		*/

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.app_name);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_item, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
			break;
		case R.id.detail_item_action_like:
			Toast.makeText(this, R.string.detail_item_action_like_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_item_action_reply:
			Toast.makeText(this, R.string.detail_item_action_reply_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_item_action_info:
			Toast.makeText(this, R.string.detail_item_action_info_toast, Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickItemImage(View view) {
		Intent intent = new Intent(DetailItemActivity.this, PhotoActivity.class);
//		intent.putExtra("image_id", image_id);
		intent.putExtra("IMAGE", item);
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
	}
	
	public class DownLoadImageBitmap extends AsyncTask<ImageView, Integer, Bitmap> {
		
		private static final String DEBUG_TAG = "DownLoadImageBitmap";
		
		private ImageView imageView = null;

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
