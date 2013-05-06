package kr.contextlogic.android.home;

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

import kr.contextlogic.R;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HomeTabFragment extends Fragment implements OnScrollListener {
	
	public final static String DEBUG_TAG = HomeTabFragment.class.getSimpleName();
	
	private Context mContext;
	
	private ArrayList<String> itemList;
	
	private static final String ROOT_URL = "http://clk01.cafe24.com:9999/product/list.api?start=0&length=10";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mContext = this.getActivity();
		
		View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
		// Create the view of home tab 
		GridView mHomeGridView = (GridView) rootView.findViewById(R.id.home_tab_grid_view);
		mHomeGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO call detail activity
				
			}
		});
		mHomeGridView.setAdapter(new HomeImageAdapter(mContext));
		UpdateItemListTask updateItemListTask = new UpdateItemListTask(mContext);
		updateItemListTask.execute(ROOT_URL);
		return rootView;
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	public class UpdateItemListTask extends AsyncTask<String, Integer, String> {
		
		private Context mContext;
		
		private ProgressDialog pd;
		
		public UpdateItemListTask(Context mContext) {
			super();
			this.mContext = mContext;
		}

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
			pd = new ProgressDialog(mContext);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setTitle("Loading");
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
			Log.d(DEBUG_TAG, result);
			try {
				JSONArray json = new JSONArray(result);
				for(int i=0; i<json.length(); i++) {
					itemList.add(json.getJSONObject(i).getString("image"));
				}
				Log.d(DEBUG_TAG, itemList.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pd.dismiss();
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
