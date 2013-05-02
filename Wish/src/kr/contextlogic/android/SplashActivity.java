package kr.contextlogic.android;

import kr.contextlogic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashActivity extends Activity {
	
	private static final int DELAY_TIME = 3000;
	
	private ImageView splashImage;
	
	private int splashCount = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		splashImage = (ImageView) this.findViewById(R.id.splash_image);
		splashImage.setScaleType(ScaleType.FIT_CENTER);
		splashImage.setImageResource(R.drawable.splash_wish_01);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		}, DELAY_TIME); 
	}
	
	public void clickSplash(View v) {
		switch (splashCount) {
		case 0:
			splashImage.setImageResource(R.drawable.splash_wish_01);
			splashCount = 1;
			break;
		case 1:
			splashImage.setImageResource(R.drawable.splash_wish_02);
			splashCount = 2;
			break;
		case 2:
			splashImage.setImageResource(R.drawable.splash_wish_03);
			splashCount = 0;
			break;

		default:
			break;
		}
	}

}
