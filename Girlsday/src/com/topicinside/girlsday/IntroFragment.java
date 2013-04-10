package com.topicinside.girlsday;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class IntroFragment extends Fragment {
	
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
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("basic_info"));
		return view;
	}
	
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    	if(state.isOpened()) {
    		Log.i(TAG, "Logged in...");
//    		requestInfo(session);
//    		makeMeRequest(session);
//    		goMainActivity(session);
    	}else if(state.isClosed()) {
    		Log.i(TAG, "Logged out...");
    	}
    }
    
    public void requestInfo(Session session) {
		Request request = new Request(session, "257397481049882",
				null, null, new Request.Callback() {
					@Override
					public void onCompleted(Response response) {
						GraphObject graphObject = response.getGraphObject();
						if(graphObject != null) {
							if(graphObject.getProperty("id") != null) {
								Log.i("id", graphObject.getProperty("id").toString());
								Log.i("name", graphObject.getProperty("name").toString());
							}
						}
					}
				});
		request.executeAsync();
    }
    
    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a 
        // new callback to handle the response.
        Request request = Request.newMeRequest(session, 
                new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                // If the response is successful
                if (session == Session.getActiveSession()) {
                    if (user != null) {
                        // Set the id for the ProfilePictureView
                        // view that in turn displays the profile picture.
                        //profilePictureView.setProfileId(user.getId());
                        // Set the Textview's text to the user's name.
                        //userNameView.setText(user.getName());
                    	Toast.makeText(getActivity(), user.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.getError() != null) {
                    // Handle errors, will do so later.
                }
            }
        });
        request.executeAsync();
    }
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if(session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}
		
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

}
