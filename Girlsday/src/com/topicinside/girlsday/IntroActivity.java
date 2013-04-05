package com.topicinside.girlsday;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class IntroActivity extends FragmentActivity {
	
	private IntroFragment introFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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
	
	private void onClickLogin() {
		/*
		Session session = Session.getActiveSession();
		if(!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
			.setPermissions(Arrays.asList("basic_info"))
			.setCallback(statusCallback));
		}else {
			Session.openActiveSession(this, null, true, statusCallback);
		}
		*/
	}
	
    

    public static class IntroFragment extends Fragment {
    	
    	private static final String TAG = "IntroFragment";

    	private Session.StatusCallback callback = new Session.StatusCallback() {
    		
    		@Override
    		public void call(Session session, SessionState state, Exception exception) {
    			onSessionStateChange(session, state, exception);
    		}
    	};

    	private UiLifecycleHelper uiHelper;
    	
    	@Override
    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		uiHelper = new UiLifecycleHelper(getActivity(), callback);
    		uiHelper.onCreate(savedInstanceState);
    	}
    	
		@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		View view = inflater.inflate(R.layout.activity_intro, container, false);
    		LoginButton authButton = (LoginButton) view.findViewById(R.id.facebook_login);
    		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
    		authButton.setFragment(this);
    		return view;
    	}
    
	    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    	if(state.isOpened()) {
	    		Log.i(TAG, "Logged in...");
	    	}else if(state.isClosed()) {
	    		Log.i(TAG, "Logged out...");
	    	}
	    }
    
    }

}
