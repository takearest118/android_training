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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.Session;

public class IntroActivity extends FragmentActivity {
		
	private IntroFragment introFragment;
	
	private ArrayList<Image> imageList;
	private static final String ROOT_URL = "https://graph.facebook.com/GirlsDayParty?fields=photos.limit(100).type(uploaded).fields(source)";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.topicinside.girlsday", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		
		setContentView(R.layout.activity_intro);
		
		if(savedInstanceState == null) {
			// Add the fragment on initial activity setup
			introFragment = new IntroFragment();
			getSupportFragmentManager().beginTransaction()
			.add(android.R.id.content, introFragment)
			.commit();
		}else {
			// Or set the fragment from restored state info
			introFragment = (IntroFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
		
		new DownLoadImageUrl().execute(ROOT_URL);
	}
	
    public void clickGuest(View view) {
    	goMainActivity(Session.getActiveSession());
    }
    
    public void goMainActivity(Session session) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("IMAGES", imageList);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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

		// onPostExecute displays the results of the AsyncTask
		@Override
		protected void onPostExecute(String result) {
			// TODO json object
//			Log.i(DEBUG_TAG, result);
			
			imageList = new ArrayList<Image>();

			JSONObject mGirlsDay;
			try {
				mGirlsDay = new JSONObject(result);
//				Log.i(DEBUG_TAG, "id: " + id);
				JSONObject mPhotos = mGirlsDay.getJSONObject("photos");
//				Log.i(DEBUG_TAG, "photos: " + mPhotos.toString(4));
				JSONArray mData = mPhotos.getJSONArray("data");
//				Log.i(DEBUG_TAG, "data: " + mData.toString(4));
				for(int i=0; i<mData.length(); i++) {
					JSONObject el = mData.getJSONObject(i);
					String id = el.getString("id");
					String source = el.getString("source");
//					Log.i(DEBUG_TAG, "id: " + id + ", source: " + source);
					imageList.add(new Image(id, source));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			for(Image img: imageList) {
				Log.i(DEBUG_TAG, "id: " + img.getId() + ", source: " + img.getSource());
			}
			/*
			textView.setText(result);
			scrollView.setVisibility(View.VISIBLE);
			scrollView.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					progressBar.setAlpha(0f);
				}
			});
			*/
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
