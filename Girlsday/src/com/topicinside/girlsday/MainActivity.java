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
	
	static final String STATE_IMAGES = "imageList";
	
	private ArrayList<Image> imageList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView imageView = (GridView) this.findViewById(R.id.item_grid_view);
		
		imageList = this.getIntent().getExtras().getParcelableArrayList("IMAGES");
		ImageAdapter ia = new ImageAdapter(this, imageList);
		imageView.setAdapter(ia);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(STATE_IMAGES, imageList);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		imageList = savedInstanceState.getParcelableArrayList(STATE_IMAGES);
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
		intent.putExtra("IMAGE", imageList.get(imageView.getId()));
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.slide_forward_enter, R.anim.slide_forward_leave);
		Toast.makeText(this, R.string.click_item, Toast.LENGTH_SHORT).show();
	}
	
}
