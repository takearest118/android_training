package com.topicinside.girlsday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	
	public ImageAdapter(Context c) {
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
		rootView = mInflater.inflate(R.layout.item,	null);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.item_image);
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
	
	private Integer[] mThumbIds = {
			R.drawable.girlsday_sample00,
			R.drawable.girlsday_sample01,
			R.drawable.girlsday_sample02,
			R.drawable.girlsday_sample03,
			R.drawable.girlsday_sample04,
			R.drawable.girlsday_sample05,
			R.drawable.girlsday_sample06,
			R.drawable.girlsday_sample07,
			R.drawable.girlsday_sample08,
			R.drawable.girlsday_sample09,
			R.drawable.girlsday_sample10
			/*
			R.drawable.girlsday_sample11,
			R.drawable.girlsday_sample12,
			R.drawable.girlsday_sample13,
			R.drawable.girlsday_sample14,
			R.drawable.girlsday_sample15,
			R.drawable.girlsday_sample16,
			R.drawable.girlsday_sample17,
			R.drawable.girlsday_sample18,
			R.drawable.girlsday_sample19,
			R.drawable.girlsday_sample20,
			R.drawable.girlsday_sample21,
			R.drawable.girlsday_sample22,
			R.drawable.girlsday_sample23,
			R.drawable.girlsday_sample24,
			R.drawable.girlsday_sample25,
			R.drawable.girlsday_sample26,
			R.drawable.girlsday_sample27,
			R.drawable.girlsday_sample28,
			R.drawable.girlsday_sample29,
			R.drawable.girlsday_sample30,
			R.drawable.girlsday_sample31,
			R.drawable.girlsday_sample32,
			R.drawable.girlsday_sample33,
			R.drawable.girlsday_sample34,
			R.drawable.girlsday_sample35,
			R.drawable.girlsday_sample36,
			R.drawable.girlsday_sample37,
			R.drawable.girlsday_sample38
			*/
	};

}
