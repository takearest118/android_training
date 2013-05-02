package kr.contextlogic.android;

import kr.contextlogic.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeTabActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hometab);
		
		GridView mHomeGridView = (GridView) this.findViewById(R.id.home_tab_grid_view);
		mHomeGridView.setAdapter(new HomeImageAdapter(this));

	}

}
