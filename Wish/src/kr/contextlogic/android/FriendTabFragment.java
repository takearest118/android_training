package kr.contextlogic.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FriendTabFragment extends Fragment {
	
	final static String DEBUG = FriendTabFragment.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		TextView tv = new TextView(this.getActivity());
		tv.setText(DEBUG);

		return tv;
	}

}
