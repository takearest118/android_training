package com.topicinside.girlsday;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

public class IntroActivity extends FragmentActivity {
		
	private static final int DELAY_TIME = 2000;
	
	private IntroFragment introFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.topicinside.girlsday", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		
		if(savedInstanceState == null) {
			// Add the fragment on initial activity setup
			introFragment = new IntroFragment();
			getSupportFragmentManager().beginTransaction()
			.add(android.R.id.content, introFragment)
			.commit();
		}else {
			// Or set the fragment from restored state info
			introFragment = (IntroFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(IntroActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		}, DELAY_TIME);	}
	
}
