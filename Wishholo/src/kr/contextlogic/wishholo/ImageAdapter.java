package kr.contextlogic.wishholo;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	public ImageAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		wishButton.setText("Wish");
		wishButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
		wishButton.setWidth(LayoutParams.WRAP_CONTENT);
		wishButton.setHeight(LayoutParams.WRAP_CONTENT);
		btnLayout.addView(wishButton, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Button recommendButton = new Button(mContext);
		recommendButton.setText("Recommend");
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
	}
	
	private Integer[] mThumbIds = {
			R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5,
			R.drawable.sample_6, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5,
			R.drawable.sample_6, R.drawable.sample_0
	};

}
