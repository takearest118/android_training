package kr.contextlogic.android;

import kr.contextlogic.R;
import kr.contextlogic.android.friend.FriendTabFragment;
import kr.contextlogic.android.home.HomeTabFragment;
import kr.contextlogic.android.more.MoreTabFragment;
import kr.contextlogic.android.profile.ProfileTabFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	
	final static String DEBUG = MainActivity.class.getSimpleName();
	
	public enum Tab {
		HOME, PROFILE, FRIEND, MORE
	};
	
	FragmentTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createTabHost();
	}
	
	private void createTabHost() {
		tabHost = (FragmentTabHost) this.findViewById(R.id.wish_tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.content);
		for(Tab t : Tab.values()){
			tabHost.addTab(createTabSpec(t), getTabFragment(t), null);
		}
	}
	
	private TabHost.TabSpec createTabSpec(Tab tab) {
		TabHost.TabSpec spec = tabHost.newTabSpec(getTabTextString(tab));

		View tv = LayoutInflater.from(this).inflate(R.layout.tab_custom, null, false);
		
		ImageView iconView = (ImageView) tv.findViewById(R.id.tab_custom_icon);
		iconView.setImageResource(getTabIconResource(tab));
		TextView textView = (TextView) tv.findViewById(R.id.tab_custom_text);
		textView.setText(getTabTextString(tab));
		
		spec.setIndicator(tv);
		
		return spec;
	}
	
	private Class<?> getTabFragment(Tab t) {
		Class<?> c = null;
		
		switch(t) {
		case HOME:
			c = HomeTabFragment.class;
			break;
		case PROFILE:
			c = ProfileTabFragment.class;
			break;
		case FRIEND:
			c = FriendTabFragment.class;
			break;
		case MORE:
			c = MoreTabFragment.class;
			break;
		default:
			break;
		}
		
		return c;
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
	
	public String getTabTextString(Tab tab) {
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
	
}
