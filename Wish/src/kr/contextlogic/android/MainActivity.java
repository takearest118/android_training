package kr.contextlogic.android;

import kr.contextlogic.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost) this.findViewById(R.id.wish_tabhost);
		tabHost.setup();
		
		TabHost.TabSpec spec = tabHost.newTabSpec("home");
		spec.setContent(R.id.home_tab);
		spec.setIndicator("HOME", this.getResources().getDrawable(R.drawable.btn_notify));
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("profile");
		spec.setContent(R.id.profile_tab);
		spec.setIndicator("PROFILE", this.getResources().getDrawable(R.drawable.btn_notify));
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("friend");
		spec.setContent(R.id.friend_tab);
		spec.setIndicator("FRIEND", this.getResources().getDrawable(R.drawable.btn_notify));
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("more");
		spec.setContent(R.id.more_tab);
		spec.setIndicator("MORE", this.getResources().getDrawable(R.drawable.btn_notify));
		tabHost.addTab(spec);
	}
	
}
