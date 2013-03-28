package com.blackroom.contactssample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static final String SELECTED_PHONE = "selectedphone";
	public static final int SUCCESS = 1;
	public static final int FAIL = -1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_get_contact).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showContactlist();
			}
		});
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10001) {
			if (resultCode == SUCCESS) {
				 ((TextView)findViewById(R.id.tv_selected_phone)).setText(data.getStringExtra(SELECTED_PHONE));
			} else {
				 ((TextView)findViewById(R.id.tv_selected_phone)).setText("");
			}
		}
	}

	private void showContactlist() {
		Intent intent = new Intent(MainActivity.this,
				ContactListActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		startActivityForResult(intent, 10001);
	}
    
}
