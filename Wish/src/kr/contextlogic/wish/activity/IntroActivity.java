package kr.contextlogic.wish.activity;

import kr.contextlogic.wish.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class IntroActivity extends Activity {
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				finish();
			}
		};
		handler.sendEmptyMessageDelayed(0, 3000);
	}
}
