package kr.contextlogic.wishholo.adapter;

import kr.contextlogic.wishholo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MoreImageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	
	public MoreImageAdapter(Context c) {
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
		ImageView imageView;
		if(convertView == null) {
			imageView = new ImageView(mInflater.getContext());
			imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, 128));
			imageView.setScaleType(ScaleType.FIT_CENTER);
			imageView.setImageResource(mThumbIds[position]);
		}else {
			imageView = (ImageView) convertView;
		}
		return imageView;
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
			R.drawable.more_settings,
			R.drawable.more_notifications,
			R.drawable.more_invite_friend
	};

}
