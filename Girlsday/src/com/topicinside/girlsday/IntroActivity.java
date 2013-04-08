package com.topicinside.girlsday;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

public class IntroActivity extends FragmentActivity {
		
	private IntroFragment introFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.facebook.samples.loginhowto", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		
		setContentView(R.layout.activity_intro);
		
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
	}

}
