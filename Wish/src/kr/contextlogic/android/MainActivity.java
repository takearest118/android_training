package kr.contextlogic.android;

import kr.contextlogic.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final static String DEBUG = MainActivity.class.getSimpleName();
	
	public enum Tab {
		HOME, PROFILE, FRIEND, MORE
	};
	
	View homeView;
	View profileView;
	View friendView;
	View moreView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createTabHost();
		
		homeView = (LinearLayout) this.findViewById(R.id.home_tab);
		profileView = (LinearLayout) this.findViewById(R.id.profile_tab);
		friendView = (LinearLayout) this.findViewById(R.id.friend_tab);
		moreView = (LinearLayout) this.findViewById(R.id.more_tab);
		
		GridView mHomeGridView = (GridView) this.findViewById(R.id.home_tab_grid_view);
		mHomeGridView.setAdapter(new HomeImageAdapter(this));
	}
	
	private void createTabHost() {
		TabHost tabHost = (TabHost) this.findViewById(R.id.wish_tabhost);
		tabHost.setup();
		for(Tab t : Tab.values()){
			createTabView(tabHost, t);
		}
	}
	
	private void createTabView(TabHost tabHost, Tab tab) {
		TabHost.TabSpec spec = tabHost.newTabSpec(getTabTextString(tab));

		View tv = LayoutInflater.from(this).inflate(R.layout.tab_custom, null, false);
		
		ImageView iconView = (ImageView) tv.findViewById(R.id.tab_custom_icon);
		iconView.setImageResource(getTabIconResource(tab));
		TextView textView = (TextView) tv.findViewById(R.id.tab_custom_text);
		textView.setText(getTabTextString(tab));
		
		spec.setIndicator(tv);
		spec.setContent(getTabResource(tab));
		tabHost.addTab(spec);
	}
	
	private int getTabIconResource(Tab tab) {
		int resId = -1;
		
		switch(tab) {
		case HOME:
			resId = R.drawable.tab_home_selector;
			break;
		case PROFILE:
			resId = R.drawable.tab_profile_selector;
			break;
		case FRIEND:
			resId = R.drawable.tab_friend_selector;
			break;
		case MORE:
			resId = R.drawable.tab_more_selector;
			break;
		default:
			break;
		}
		
		return resId;
	}
	
	private String getTabTextString(Tab tab) {
		String str = null;
		
		switch(tab) {
		case HOME:
			str = this.getResources().getString(R.string.tab_home_text);
			break;
		case PROFILE:
			str = this.getResources().getString(R.string.tab_profile_text);
			break;
		case FRIEND:
			str = this.getResources().getString(R.string.tab_friend_text);
			break;
		case MORE:
			str = this.getResources().getString(R.string.tab_more_text);
			break;
		default:
			break;
		}
		
		return str;
	}
	
	private int getTabResource(Tab tab) {
		int resId = -1;
		
		switch(tab) {
		case HOME:
			resId = R.id.home_tab;
			break;
		case PROFILE:
			resId = R.id.profile_tab;
			break;
		case FRIEND:
			resId = R.id.friend_tab;
			break;
		case MORE:
			resId = R.id.more_tab;
			break;
		default:
			break;
		}
		
		return resId;
	}
	
}
