package kr.contextlogic.android.home;

import kr.contextlogic.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeImageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	
	public HomeImageAdapter(Context c) {
		mInflater = LayoutInflater.from(c);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rootView;
		if(convertView == null) {
			rootView = mInflater.inflate(R.layout.item,	null);
			ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
			imageView.setImageResource(mThumbIds[position]);
		}else {
			rootView = (LinearLayout) convertView;
		}
		return rootView;
	}
	
	private Integer[] mThumbIds = {
			R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5,
			R.drawable.sample_6
	};

}
