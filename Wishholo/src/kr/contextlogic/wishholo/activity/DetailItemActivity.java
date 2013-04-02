package kr.contextlogic.wishholo.activity;

import kr.contextlogic.wishholo.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detail_item);

		/*
		// Get the id of item from the intent
		Intent intent = getIntent();
		String drawable = intent.getStringExtra(MainActivity.ITEM_ID);
		ImageView itemView = (ImageView) this.findViewById(R.id.item_image);
		
		itemView.setImageResource(drawable);
		*/

		ActionBar actionBar = this.getActionBar();
		actionBar.setCustomView(R.layout.actionbar_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.action_backhome);
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
			startActivity(intent);
			this.overridePendingTransition(R.anim.slide_backward_enter, R.anim.slide_backward_leave);
			break;
		case R.id.detail_item_action_share:
			Toast.makeText(this, R.string.detail_item_action_share_toast, Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}
