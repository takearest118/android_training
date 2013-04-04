package com.topicinside.girlsday;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhotoActivity extends Activity {
	
	enum SCREEN_MODE {
		FULL,
		NORMAL
	};
	
	public SCREEN_MODE mode = SCREEN_MODE.FULL;
	public int image_id = -1;
	
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		this.mode = SCREEN_MODE.FULL;
		// Get the id of item from the intent
		Intent intent = getIntent();
		image_id = intent.getIntExtra("image_id", -1);
		ImageView itemView = (ImageView) this.findViewById(R.id.item_image);
		
		itemView.setImageResource(mThumbIds[image_id]);

		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.app_name);
		actionBar.hide();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			backDetailItemActivity();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickImage(View view) {
		backDetailItemActivity();
	}
	
	public void backDetailItemActivity() {
		Intent intent = new Intent(this, DetailItemActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("image_id", image_id);
		startActivity(intent);
		this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
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
