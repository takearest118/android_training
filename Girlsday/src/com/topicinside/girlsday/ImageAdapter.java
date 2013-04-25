package com.topicinside.girlsday;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static final String DEBUG_TAG = "ImageAdapter";
	
	private LayoutInflater mInflater;
	private ArrayList<Image> imageList;
	
	private Display display;
	
	public ImageAdapter(Context c, ArrayList<Image> _imageList) {
		mInflater = LayoutInflater.from(c);
		this.imageList = _imageList;
		display = ((WindowManager)c.getSystemService(c.WINDOW_SERVICE)).getDefaultDisplay();
	}
	

	@Override
	public int getCount() {
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return imageList.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		View v = convertView;
		ViewHolder holder;
		if(v == null) {
			holder = new ViewHolder();
			v = mInflater.inflate(R.layout.item, null);
			holder.image = (ImageView) v.findViewById(R.id.item_image);
			holder.image.setTag(imageList.get(position).getPicture());
			holder.image.setId(position);
			v.setTag(holder);
			new DownLoadImageBitmap().execute(holder.image);
		}else {
			holder = (ViewHolder) v.getTag();
		}
		
		return v;
		*/
		View rootView;
		rootView = mInflater.inflate(R.layout.item,	null);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
		imageView.getLayoutParams().height = display.getHeight() / 3;

		Image tmpImg = imageList.get(position);
		Bitmap tmpBmp = ImageCache.getImage(tmpImg.getSource());
		if(tmpBmp == null) {
			imageView.setTag(tmpImg.getSource());
			DownLoadImageBitmap task = new DownLoadImageBitmap();
			task.execute(imageView);
		}else {
			imageView.setImageBitmap(tmpBmp);
		}
		imageView.setId(position);
		
		return rootView;
	}
	
	class ViewHolder {
		ImageView image;
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
				
				BitmapFactory.Options bfo = new BitmapFactory.Options();
				bfo.inSampleSize = 2;
				BufferedInputStream bis = new BufferedInputStream(is);
				bm = BitmapFactory.decodeStream(bis, null, bfo);
				ImageCache.setImage(myurl, bm);
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
