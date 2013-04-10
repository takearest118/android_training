package com.topicinside.girlsday;

import java.io.BufferedInputStream;
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
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static final String DEBUG_TAG = "ImageAdapter";
	private static final String ROOT_URL = "https://graph.facebook.com/GirlsDayParty?fields=photos.limit(100).type(uploaded).fields(source)";
	
	private LayoutInflater mInflater;
	private Integer[] mThumbIds;
	private ArrayList<Image> imageList;
	
	public ImageAdapter(Context c, Integer[] thumbs, ArrayList<Image> _imageList) {
		mInflater = LayoutInflater.from(c);
		if(thumbs != null) {
			mThumbIds = thumbs;
		}
		this.imageList = _imageList;
		Log.i(DEBUG_TAG, this.imageList.toString());
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

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return mThumbIds[position].hashCode();
	}
	
	private Bitmap getImageFromUrl(String imageUrl) {
		Bitmap imgBitmap = null;
		
		try {
			URL url = new URL(imageUrl);
			URLConnection conn = url.openConnection();
			conn.connect();
			
			int nSize = conn.getContentLength();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
			imgBitmap = BitmapFactory.decodeStream(bis);
			
			bis.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return imgBitmap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rootView;
		rootView = mInflater.inflate(R.layout.item,	null);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
		
		// TODO DownloadImage extends AsyncTask
		
		imageView.setImageResource(mThumbIds[position]);
		imageView.setId(position);
		/*
		if(convertView == null) {
			rootView = mInflater.inflate(R.layout.item,	null);
			ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
			imageView.setImageResource(mThumbIds[position]);
		}else {
			rootView = (LinearLayout) convertView;
		}
		*/
		return rootView;
		/*
//		View rootView;
		ImageView imageView;
		if(convertView == null) {
			imageView = new ImageView(mContext);
//			rootView = View.inflate(mContext, R.layout.item, parent);
//			ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
//			imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			imageView.setPadding(8, 8, 8, 8);
			imageView.setImageResource(mThumbIds[position]);
		}else {
//			rootView = convertView;
			imageView = (ImageView) convertView;
		}
		
//		return rootView;
		return imageView;
		*/
		/*
		LinearLayout cardLayout;
		LinearLayout btnLayout;
		ImageView imageView;
		
		cardLayout = new LinearLayout(mContext);
		cardLayout.setOrientation(LinearLayout.VERTICAL);
		cardLayout.setGravity(Gravity.CENTER);
		
		btnLayout = new LinearLayout(mContext);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		btnLayout.setGravity(Gravity.CENTER);
		Button wishButton = new Button(mContext);
		wishButton.setText(R.string.item_wish_button);
		wishButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
		wishButton.setWidth(LayoutParams.WRAP_CONTENT);
		wishButton.setHeight(LayoutParams.WRAP_CONTENT);
		btnLayout.addView(wishButton, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Button recommendButton = new Button(mContext);
		recommendButton.setText(R.string.item_wish_button);
		recommendButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
		recommendButton.setWidth(LayoutParams.WRAP_CONTENT);
		recommendButton.setHeight(LayoutParams.WRAP_CONTENT);
		btnLayout.addView(recommendButton, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		imageView = new ImageView(mContext);
		imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(8, 8, 8, 8);
		
		imageView.setImageResource(mThumbIds[position]);
		
		cardLayout.addView(imageView);
		cardLayout.addView(btnLayout);
		
		return cardLayout;
		*/
	}
	
	public class DownLoadImageBitmap extends AsyncTask<String, Integer, Bitmap> {
		
		private static final String DEBUG_TAG = "DownLoadImageBitmap";

		@Override
		protected Bitmap doInBackground(String... urls) {
			
			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			}catch(IOException e) {
				return null;
			}
		}

		// onPostExecute displays the results of the AsyncTask
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO set image to ImageView
			Log.d(DEBUG_TAG, result.toString());
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
