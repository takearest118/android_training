package com.topicinside.girlsday;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailItemActivity extends Activity {
	
	public int image_id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detail_item);

		// Get the id of item from the intent
		Intent intent = getIntent();
		image_id = intent.getIntExtra("image_id", -1);
		ImageView itemView = (ImageView) this.findViewById(R.id.item_image);
		
		itemView.setImageResource(mThumbIds[image_id]);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.app_name);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_item, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("image_id", image_id);
			startActivity(intent);
			this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
			break;
		case R.id.detail_item_action_like:
			Toast.makeText(this, R.string.detail_item_action_like_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_item_action_reply:
			Toast.makeText(this, R.string.detail_item_action_reply_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_item_action_info:
			Toast.makeText(this, R.string.detail_item_action_info_toast, Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickItemImage(View view) {
		Intent intent = new Intent(DetailItemActivity.this, PhotoActivity.class);
		intent.putExtra("image_id", image_id);
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
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
