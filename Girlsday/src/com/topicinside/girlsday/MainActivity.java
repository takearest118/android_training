package com.topicinside.girlsday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.getIntent().getExtras().getParcelableArrayList("IMAGES");
		GridView imageView = (GridView) this.findViewById(R.id.item_grid_view);
		ArrayList<Image> imageList = this.getIntent().getExtras().getParcelableArrayList("IMAGES");
		ImageAdapter ia = new ImageAdapter(this, mThumbIds, imageList);
//		ImageAdapter ia = new ImageAdapter(this, mThumbIds);
		imageView.setAdapter(ia);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_refresh:
			Toast.makeText(this, R.string.action_refresh_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_settings:
			Toast.makeText(this, R.string.action_settings_toast, Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void clickItem(View view) {
		ImageView imageView = (ImageView)view;
		Intent intent = new Intent(MainActivity.this, DetailItemActivity.class);
		intent.putExtra("image_id", imageView.getId());
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
		Toast.makeText(this, R.string.click_item, Toast.LENGTH_SHORT).show();
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
