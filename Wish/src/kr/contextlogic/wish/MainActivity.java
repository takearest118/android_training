package kr.contextlogic.wish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

public class MainActivity extends Activity {
	
	private TabHost mTabHost;
	private ListView mFriendsView;
	private GridView mItemsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Display Wish Logo
		startActivity(new Intent(this, IntroActivity.class));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (TabHost) this.findViewById(R.id.tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("items_tag").setContent(R.id.home_tab).setIndicator(this.getString(R.string.home_tab)));
		mTabHost.addTab(mTabHost.newTabSpec("profile_tag").setContent(R.id.profile_tab).setIndicator(this.getString(R.string.profile_tab)));
		mTabHost.addTab(mTabHost.newTabSpec("friends_tag").setContent(R.id.friends_tab).setIndicator(this.getString(R.string.friends_tab)));
		mTabHost.addTab(mTabHost.newTabSpec("more_tag").setContent(R.id.more_tab).setIndicator(this.getString(R.string.more_tab)));		
		mTabHost.setCurrentTab(0);
		
		ArrayAdapter<CharSequence> friendsAdapter;
		friendsAdapter = ArrayAdapter.createFromResource(this, R.array.dump_friends_array, android.R.layout.simple_dropdown_item_1line);
		mFriendsView = (ListView) this.findViewById(R.id.friends_list_view);
		mFriendsView.setAdapter(friendsAdapter);

		// slide menu
		mItemsView = (GridView) this.findViewById(R.id.home_grid_view);
		mItemsView.setAdapter(new ImageAdapter(this));
		
		Spinner categorySpinner = (Spinner) this.findViewById(R.id.category_spinner);
		ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.dump_category_array, android.R.layout.simple_spinner_dropdown_item);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoryAdapter);
	}
}
