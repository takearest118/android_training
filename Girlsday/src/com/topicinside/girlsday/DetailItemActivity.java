package com.topicinside.girlsday;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailItemActivity extends Activity {
	
	private static final String BASE_URL = "http://graph.facebook.com/";
	private static final String LINK_URL = "http://facebook.com/";
	private static final String PARAMS = "?fields=name,from,created_time,comments,likes,link";

	private Context context;
	
	private String id;
	private Image item;
	
	private ImageView itemView;
	private TextView itemTitle;
	private TextView writerDate;
	private TextView writerName;
	private ImageView writerPhoto;
	
	private MenuItem likeMenu;
	private MenuItem replyMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detail_item);
		
		context = this.getApplicationContext();

		// Get the id of item from the intent
		Intent intent = getIntent();
		id = intent.getStringExtra("ID");
		item = new Image(id);
		item.setSource(intent.getStringExtra("SOURCE"));
				
		itemView = (ImageView) this.findViewById(R.id.item_image);
		itemTitle = (TextView) this.findViewById(R.id.item_title);
		writerDate = (TextView) this.findViewById(R.id.writer_date);
		writerName = (TextView) this.findViewById(R.id.writer_name);
		writerName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetailItemActivity.this, WebViewActivity.class);
				intent.putExtra("LINK", LINK_URL + item.getWriterId());
				startActivity(intent);
				overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
			}
		});
		
		itemView.setTag(item.getSource());
		new DownLoadImageBitmap().execute(itemView);
		
		/*
		writerPhoto.setTag(item.getWriterPhoto());
		new DownLoadImageBitmap().execute(writerPhoto);
		*/

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setTitle(R.string.app_name);
		
		DownLoadImage imageTask = new DownLoadImage();
		imageTask.execute(BASE_URL + item.getId() + PARAMS);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_item, menu);
		replyMenu = menu.findItem(R.id.detail_item_action_reply);
		likeMenu = menu.findItem(R.id.detail_item_action_like);

		return true;
	}
	
	public void sendUrlLink() throws NameNotFoundException {
		// Recommended: Use application context for parameter.
		KakaoLink kakaoLink = KakaoLink.getLink(getApplicationContext());

		// check, intent is available.
		if (!kakaoLink.isAvailableIntent()) {
			Toast.makeText(this, "Not installed KakaoTalk", Toast.LENGTH_SHORT).show();		
			return;
		}

		/**
		 * @param activity
		 * @param url
		 * @param message
		 * @param appId
		 * @param appVer
		 * @param appName
		 * @param encoding
		 */
		kakaoLink.openKakaoLink(this, 
				"http://link.kakao.com/?test-android-app", 
				"First KakaoLink Message for send url.", 
				getPackageName(), 
				getPackageManager().getPackageInfo(getPackageName(), 0).versionName, 
				"KakaoLink Test App", 
				"UTF-8");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			/*
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
			*/
			/*
			NavUtils.navigateUpFromSameTask(this);
			*/
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
		case R.id.detail_item_sub_action_kakao:
			/*
			Intent kintent = new Intent(Intent.ACTION_SEND);
			kintent.setType("text/plain");
			kintent.putExtra(Intent.EXTRA_SUBJECT, "TEST_Subject");
			kintent.putExtra(Intent.EXTRA_TEXT, "Å×½ºÆ®¸Þ¼¼Áö¿¡¿ä È«È«");
			kintent.setPackage("com.kakao.talk");
			startActivity(kintent);
			*/
			try {
				sendUrlLink();
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(this, R.string.detail_item_sub_action_kakao_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_item_sub_action_detailview:
			Intent intent = new Intent(DetailItemActivity.this, WebViewActivity.class);
			intent.putExtra("LINK", this.item.getLink());
			this.startActivity(intent);
			this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
			Toast.makeText(this, R.string.detail_item_sub_action_detailview_toast, Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickItemImage(View view) {
		Intent intent = new Intent(DetailItemActivity.this, PhotoActivity.class);
		intent.putExtra("IMAGE", item);
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
	}
	
	public class DownLoadImageBitmap extends AsyncTask<ImageView, Integer, Bitmap> {
		
		private static final String DEBUG_TAG = "DownLoadImageBitmap";
		
		private ImageView imageView = null;
		
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(DetailItemActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setTitle(R.string.loading_title);
			pd.setMessage("Please, wait...");
			pd.show();
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

	public class DownLoadImage extends AsyncTask<String, Integer, String> {
		
		private static final String DEBUG_TAG = "DownLoadImage";
		
		@Override
		protected String doInBackground(String... urls) {
			
			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			}catch(IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		// onPostExecute displays the results of the AsyncTask
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject json = new JSONObject(result);
				itemTitle.setText(json.getString("name"));
				item.setWriterId(json.getJSONObject("from").getString("id"));
				writerName.setText(json.getJSONObject("from").getString("name"));
				writerDate.setText(json.getString("created_time"));
				item.setLink(json.getString("link"));
				int count;
				count = json.getJSONObject("comments").getJSONArray("data").length();
				replyMenu.setTitle(
						context.getResources().getString(R.string.detail_item_action_reply)
						+ "(" + Integer.toString(count) + ")");
				count = json.getJSONObject("likes").getJSONArray("data").length();
				likeMenu.setTitle(
						context.getResources().getString(R.string.detail_item_action_like)
						+ "(" + Integer.toString(count) + ")");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private String downloadUrl(String myurl) throws IOException {
			InputStream is = null;
			
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
				
				// Convert the InputStream into a string
				String contentAsString = this.convertStreamToString(is);
				return contentAsString;
			}finally {
				if(is != null) {
					is.close();
				}
			}
		}
		
		public String convertStreamToString(InputStream is) throws IOException {
			if(is != null) {
				Writer writer = new StringWriter();
				
				char[] buffer = new char[1024];
				try {
					Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					int n;
					while((n=reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
				} finally {
					is.close();
				}
				return writer.toString();
			}else {
				return null;
			}
		}
	}
	
}
