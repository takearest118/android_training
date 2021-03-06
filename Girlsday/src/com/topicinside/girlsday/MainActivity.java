package com.topicinside.girlsday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;





public class MainActivity extends SherlockActivity implements OnScrollListener {
	
	static final String DEBUG_TAG = "MainActivity";
	
	private boolean wifiConnected = false;
	private boolean mobileConnected = false;
	
	static final String STATE_IMAGES = "imageList";
	
	private static final String ROOT_URL = "https://graph.facebook.com" +
			"/GirlsDayParty" +
			"/photos" +
			"?limit=30" +
			"&type=uploaded" +
			"&fields=id,picture,source" +
			"&after=";
	
	private ArrayList<Image> imageList;
	private String next;
	
	private ImageAdapter ia;
	
	private GridView imageView;
	private boolean lockGridView;
	private boolean endList;
	
	private ProgressDialog pd;
	
	private Handler mHandler;
	
	private boolean mBackFlag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBackFlag = false;
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 0) {
					mBackFlag = false;
				}
			}
		};

		updateConnectedFlags();
		
		lockGridView = false;
		endList = false;
		
		imageView = (GridView) this.findViewById(R.id.item_grid_view);
		
		imageList = new ArrayList<Image>();
		ia = new ImageAdapter(this, imageList);
		
		if(wifiConnected || mobileConnected) {
        	Toast.makeText(getApplicationContext(), R.string.connection_message, Toast.LENGTH_LONG).show();

			imageView.setAdapter(ia);
			imageView.setOnScrollListener(this);
		}else {
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        	alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			});
        	alert.setTitle(R.string.connection_title);
        	alert.setMessage(R.string.connection_warning_message);
        	alert.show();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			if(!mBackFlag) {
				Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료합니다.", Toast.LENGTH_SHORT).show();
				mBackFlag = true;
				mHandler.sendEmptyMessageDelayed(0, 2000);
				return false;
			}else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(STATE_IMAGES, imageList);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		imageList = savedInstanceState.getParcelableArrayList(STATE_IMAGES);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_refresh:
			updateConnectedFlags();
			if(wifiConnected || mobileConnected) {
				imageList.clear();
				ia.notifyDataSetChanged();
				Toast.makeText(this, R.string.action_refresh_toast, Toast.LENGTH_SHORT).show();
				endList = false;
			}else {
	        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
	        	alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});
	        	alert.setTitle(R.string.connection_title);
	        	alert.setMessage(R.string.connection_warning_message);
	        	alert.show();
			}
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
    private void updateConnectedFlags() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

	public void clickItem(View view) {
		ImageView imageView = (ImageView)view;
		Intent intent = new Intent(MainActivity.this, DetailItemActivity.class);
		intent.putExtra("ID", imageList.get(imageView.getId()).getId());
		intent.putExtra("SOURCE", imageList.get(imageView.getId()).getSource());
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_zoomout_forward_leave);
		Toast.makeText(this, R.string.click_item, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem == 0 && visibleItemCount == 0 && lockGridView == false && endList == false) {
			lockGridView = true;
			new DownLoadImageUrl().execute(ROOT_URL);
		}
		
		int count = firstVisibleItem + visibleItemCount;
		if(count >= totalItemCount
				&& firstVisibleItem != 0
				&& lockGridView == false
				&& endList == false) {
			lockGridView = true;
			if(next.equals("")) {
				Toast.makeText(this, R.string.list_end_message, Toast.LENGTH_SHORT).show();
				lockGridView = false;
				endList = true;
				return;
			}
			new DownLoadImageUrl().execute(ROOT_URL + next);
		}
		Log.d(DEBUG_TAG, "firstVisibleItem = " + Integer.toString(firstVisibleItem));
		Log.d(DEBUG_TAG, "visibleItemCount = " + Integer.toString(visibleItemCount));	
		Log.d(DEBUG_TAG, "totalItemCount = " + Integer.toString(totalItemCount));
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "scrollState = " + Integer.toString(scrollState));
	}
			
	public class DownLoadImageUrl extends AsyncTask<String, Integer, String> {
		
		private static final String DEBUG_TAG = "DownLoadImageUrl";
		
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
			pd = new ProgressDialog(MainActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setTitle(R.string.loading_title);
			pd.setMessage("Please, wait...");
			pd.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		// onPostExecute displays the results of the AsyncTask
		@Override
		protected void onPostExecute(String result) {
			JSONObject mGirlsDay;
			try {
				mGirlsDay = new JSONObject(result);
				JSONArray mData = mGirlsDay.getJSONArray("data");
				if(mGirlsDay.has("paging")) {
					next = mGirlsDay.getJSONObject("paging")
							.getJSONObject("cursors").getString("after");
				}else {
					next = "";
				}
				for(int i=0; i<mData.length(); i++) {
					JSONObject el = mData.getJSONObject(i);
					String id = el.getString("id");
					String source = el.getString("source");
					String picture = el.getString("picture");
					imageList.add(new Image(id, source, picture));	
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for(Image img: imageList) {
				Log.d(DEBUG_TAG, img.toString());
			}
			
			pd.dismiss();
			
			ia.notifyDataSetChanged();
			lockGridView = false;
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
		
		// Reads an InputStream and converts it to a String.
		public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
			Reader reader = null;
			reader = new InputStreamReader(stream, "UTF-8");
			char[] buffer = new char[len];
			reader.read(buffer);
			return new String(buffer);
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
