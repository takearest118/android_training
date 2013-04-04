package com.topicinside.girlsday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity {
	
	public final static long INTRO_DELAY_TIME = 3000;
	
	/*
	private Handler mHandler;
	private Runnable mRunnable;
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		/*
		mRunnable = new Runnable() {
			@Override
			public void run() {
		        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
		        startActivity(intent);
			}
		};
		
		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, IntroActivity.INTRO_DELAY_TIME);
		*/
	}
	
	public void clickFacebook(View view) {
		goMainActivity(view);
	}
	
	public void clickGuest(View view) {
		goMainActivity(view);
	}
	
	public void goMainActivity(View view) {
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
	
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*
        this.finish();
        */
    }

    @Override
    protected void onDestroy() {
    	/*
        this.mHandler.removeCallbacks(mRunnable);
        */
        super.onDestroy();
    }
}
