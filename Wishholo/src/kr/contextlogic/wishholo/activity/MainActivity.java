package kr.contextlogic.wishholo.activity;

import java.util.Locale;

import kr.contextlogic.wishholo.R;
import kr.contextlogic.wishholo.adapter.HomeImageAdapter;
import kr.contextlogic.wishholo.adapter.MoreImageAdapter;
import kr.contextlogic.wishholo.adapter.ProfileImageAdapter;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(R.string.app_name);
		actionBar.setSubtitle(R.string.app_desc);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_notifications:
			Toast.makeText(this, R.string.action_notifications_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_search:
			Toast.makeText(this, R.string.action_search_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_refresh:
			Toast.makeText(this, R.string.action_refresh_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_share:
			Toast.makeText(this, R.string.action_share_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_settings:
			Toast.makeText(this, R.string.action_settings_toast, Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_help:
			Toast.makeText(this, R.string.action_help_toast, Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			Bundle args = new Bundle();
			if(position == 0) {
				fragment = new DummyHomeSectionFragment();
				args.putInt(DummyHomeSectionFragment.ARG_SECTION_HOME, position + 1);
			}else if(position == 1) {
				fragment = new DummyProfileSectionFragment();
				args.putInt(DummyProfileSectionFragment.ARG_SECTION_PROFILE, position + 1);
			}else if(position == 2) {
				fragment = new DummyFriendsSectionFragment();
				args.putInt(DummyFriendsSectionFragment.ARG_SECTION_FRIENDS, position + 1);
			}else if(position == 3) {
				fragment = new DummyMoreSectionFragment();
				args.putInt(DummyMoreSectionFragment.ARG_SECTION_MORE, position + 1);
			}else {
				// getItem is called to instantiate the fragment for the given page.
				// Return a DummySectionFragment (defined as a static inner class
				// below) with the page number as its lone argument.
				fragment = new DummySectionFragment();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			}
			fragment.setArguments(args);
			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
		
		public int getPageIcon(int position) {
			switch(position) {
			case 0:
				return R.drawable.tab_home;
			case 1:
				return R.drawable.tab_profile;
			case 2:
				return R.drawable.tab_friends;
			case 3:
				return R.drawable.action_settings;
			}
			return -1;
		}
	}
	
	public void clickItem(View view) {
		Toast.makeText(this, R.string.item_image_desc, Toast.LENGTH_SHORT).show();
	}
	
	public void clickWish(View view) {
		Toast.makeText(this, R.string.item_wish_button, Toast.LENGTH_SHORT).show();
	}
	
	public void clickRecommend(View view) {
		Toast.makeText(this, R.string.item_recommend_button, Toast.LENGTH_SHORT).show();
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	public static class DummyHomeSectionFragment extends Fragment {
		public static final String ARG_SECTION_HOME = "section_home";
		
		public DummyHomeSectionFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
			// Create the view of home tab 
			GridView mHomeGridView = (GridView) rootView.findViewById(R.id.home_tab_grid_view);
			mHomeGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					// TODO call detail activity
					
				}
			});
			mHomeGridView.setAdapter(new HomeImageAdapter(this.getActivity()));
			return rootView;
		}
	}

	public static class DummyProfileSectionFragment extends Fragment {
		public static final String ARG_SECTION_PROFILE = "section_profile";
		
		public DummyProfileSectionFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
			// Create the view of profile tab 
			GridView mProfileGridView = (GridView) rootView.findViewById(R.id.profile_tab_grid_view);
			mProfileGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					// TODO call detail activity
					
				}
			});
			mProfileGridView.setAdapter(new ProfileImageAdapter(this.getActivity()));
			return rootView;
		}
	}

	public static class DummyFriendsSectionFragment extends Fragment {
		public static final String ARG_SECTION_FRIENDS = "section_friends";
		
		private SimpleCursorAdapter mCursorAdapter;
		
		public DummyFriendsSectionFragment() {
		}
		
		private final String[] PROJECTION = {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
		};
		
		private final int[] TO = {
			R.id.friend_name
		};
	   

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_friends_tab, container, false);
			// Create the view of friends tab 
			ListView mFriendsListView = (ListView) rootView.findViewById(R.id.friends_tab_list_view);
			mFriendsListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					// TODO call detail activity
					
				}
			});

			ArrayAdapter<CharSequence> friendsAdapter;
			friendsAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.dump_friends_array, android.R.layout.simple_list_item_1);
			mFriendsListView = (ListView) rootView.findViewById(R.id.friends_tab_list_view);
			mFriendsListView.setAdapter(friendsAdapter);

			/*
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			String selection = "((" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOTNULL))";
			String[] selectionArgs = null;
			String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
			
			CursorLoader cl = new CursorLoader(
					this.getActivity(),
					uri,
					PROJECTION,
					selection,
					selectionArgs,
					sortOrder);
			
			this.mCursorAdapter = new SimpleCursorAdapter(
					this.getActivity(),
					R.id.friends_tab_list_view,
					cl.loadInBackground(),
					this.PROJECTION,
					this.TO);
			
			mFriendsListView.setAdapter(this.mCursorAdapter);
			*/

			return rootView;
		}
	}
	
		
	public static class DummyMoreSectionFragment extends Fragment {
		public static final String ARG_SECTION_MORE = "section_more";
		
		public DummyMoreSectionFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_more_tab, container, false);
			// Create the view of home tab 
			GridView mMoreGridView = (GridView) rootView.findViewById(R.id.more_tab_grid_view);
			mMoreGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					// TODO call detail activity
					
				}
			});
			mMoreGridView.setAdapter(new MoreImageAdapter(this.getActivity()));
			return rootView;
		}
	}

}
