package kr.contextlogic.android;

import kr.contextlogic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final static String DEBUG = MainActivity.class.getSimpleName();
	
	public enum Tab {
		HOME, PROFILE, FRIEND, MORE
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createTabHost();
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
		spec.setContent(getTabIntent(tab));
		tabHost.addTab(spec);
	}
	
	private int getTabIconResource(Tab tab) {
		int resId = -1;
		
		switch(tab) {
		case HOME:
			resId = R.drawable.ic_action_select_all;
			break;
		case PROFILE:
			resId = R.drawable.ic_action_user;
			break;
		case FRIEND:
			resId = R.drawable.ic_action_video;
			break;
		case MORE:
			resId = R.drawable.ic_action_share;
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
	
	private Intent getTabIntent(Tab tab) {
		Intent intent = null;
		
		switch(tab) {
		case HOME:
			intent = new Intent(this, HomeTabActivity.class);
			break;
		case PROFILE:
			intent = new Intent(this, ProfileTabActivity.class);
			break;
		case FRIEND:
			intent = new Intent(this, FriendTabActivity.class);
			break;
		case MORE:
			intent = new Intent(this, MoreTabActivity.class);
			break;
		default:
			break;
		}
		
		return intent;
	}
	
}
