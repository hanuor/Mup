/*©Hanuor Corporation.
This project is performed and completed by members at Hanuor.This is not an Open Source project and using the source code of this project for any other means 
without the prior information will result in violation of terms.
Mup® is a hybrid application and a product of Hanuor. */

/*Author - Shantanu Johri (Founder and Chief Executive Officer Hanuor)
 V-1.0.0*/

package tech.hanuor.mup;

import java.util.List;
import java.util.Random;

import tech.hanuor.mup.tabslayout.*;
import tech.hanuor.mup.Material_Navi_Frag;
import tech.hanuor.mup.R;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	ViewPager viewPager;
	SlidingTabLayout sTab;
	int titleId;
	Context context;
	DrawerLayout dLayout;
	ListView listeww;
	ActionBarDrawerToggle drawerListener;
	String[] listDisplay;

	// NaviAdapter naviadap;
	NetworkInfo networkInfo;
	int inlen;
	int action_random;
	Toolbar toolbar;

	// ActionBar ab;
	// Handler handle;

	// SlidingTabStrip stripper = new SlidingTabStrip(MainActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_appbar);
		// handle = new Handler();
		ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connec.getActiveNetworkInfo();
		// ab = getActionBar();
		toolbar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolbar);
		Material_Navi_Frag drawerfragment = (Material_Navi_Frag) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_drawer);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		drawerfragment.setUp(R.id.fragment_drawer,
				(DrawerLayout) findViewById(R.id.drawerLayout), toolbar);
		SpannableString s = new SpannableString(" Mup");
		s.setSpan(new TypefaSpan(this, "billanew.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getSupportActionBar().setTitle(s);
		// ActionBarGen();
		//
		//

		// Update the action bar title with the TypefaceSpan instance

		// ab.setTitle(s);
		// ColorDrawable colorDrawable = new ColorDrawable(
		// Color.parseColor("#4A148C"));
		// ab.setBackgroundDrawable(colorDrawable);

		// LayoutInflater inf = LayoutInflater.from(this);
		// View v = inf.inflate(R.id.action_custom, null);
		// ((TextView)v.findViewById(R.id.tile)).setText(this.getTitle());
		// Create a custom font for the action bar

		FragmentManager fragManager = getSupportFragmentManager();
		viewPager = (ViewPager) findViewById(R.id.pager);
		sTab = (SlidingTabLayout) findViewById(R.id.slidingTab);
		viewPager.setAdapter(new MyAdapter(fragManager));
		sTab.setViewPager(viewPager);

		/*
		 * ActivityManager activityManager = (ActivityManager)
		 * getSystemService(Context.ACTIVITY_SERVICE); List<RunningTaskInfo>
		 * tasks = activityManager.getRunningTasks(Integer.MAX_VALUE); int
		 * tiktak = tasks.size(); if(tiktak>=7){ Toast.makeText(this,
		 * "Too many appliactions opened, Mup will behave abnormally.Please close some applications."
		 * , Toast.LENGTH_SHORT).show();
		 * 
		 * } else{ Log.d("Tasks message", "Running normally"); }
		 */
		// listeww = (ListView) findViewById(R.id.drawerList);
		// dLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		// naviadap = new NaviAdapter(this);
		// listeww.setAdapter(naviadap);
		// listeww.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// listeww.setOnItemClickListener(this);
	}

	// Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
	// setSupportActionBar(mtoolbar);
	// DrawerLayout mDrawerLayout = (DrawerLayout)
	// findViewById(R.id.drawerLayout);
	// ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
	// mDrawerLayout, mtoolbar, R.string.drawer_open,
	// R.string.drawer_close) {
	// drawerListener = new ActionBarDrawerToggle(this, dLayout,
	// R.drawable.ic_drawer, R.string.drawer_open,
	// R.string.drawer_close) {

	/*
	 * @Override public void onDrawerSlide(View drawerView, float slideOffset) {
	 * // TODO Auto-generated method stub super.onDrawerSlide(drawerView,
	 * slideOffset); }
	 *//** Called when a drawer has settled in a completely closed state. */
	/*
	 * @Override public void onDrawerClosed(View view) {
	 * super.onDrawerClosed(view); // getSupportActionBar().setTitle("hello"); }
	 *//** Called when a drawer has settled in a completely open state. */
	/*
	 * @Override public void onDrawerOpened(View drawerView) {
	 * super.onDrawerOpened(drawerView); //
	 * getSupportActionBar().setTitle("hi"); } };
	 */
	// @Override
	// public void onDrawerClosed(View drawerView) { // TODO Auto-generated
	// super.onDrawerClosed(drawerView);
	// }

	// @Override
	// public void onDrawerOpened(View drawerView) { // TODO Auto-generated

	// super.onDrawerOpened(drawerView);
	// }

	// };
	// dLayout.setDrawerListener(drawerListener);
	// mDrawerLayout.setDrawerListener(mDrawerToggle);
	// getSupportActionBar().setHomeButtonEnabled(true);
	// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	// mDrawerToggle.syncState();
	// mDrawerLayout.setStatusBarBackgroundColor(Color.BLUE);

	/*
	 * private void ActionBarGen() { // TODO Auto-generated method stub int max
	 * = 10; int min = 0; Random rand = new Random();
	 * 
	 * action_random = rand.nextInt(max - min + 1) + min; ActionBar ab =
	 * getActionBar(); if (action_random == 0) { ColorDrawable colorDrawable =
	 * new ColorDrawable( Color.parseColor("#4A148C"));
	 * //stripper.change(0xFF4A148C); ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 1) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#3F51B5")); //stripper.change(0xFF3F51B5);
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 2) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#004D40")); //stripper.change(0xFF004D40);
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 3) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#3E2723")); //stripper.change(0xFF3E2723);
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 4) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#F44336")); //stripper.change(0xFFF44336);
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 5) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#90CAF9")); //stripper.change(0xFF90CAF9);
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } // perform the operation of
	 * gettting other colors here(YAY!!) else if (action_random == 6) {
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#D4E157")); //stripper.change(0xFFD4E157);
	 * 
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 7) {
	 * //stripper.change(0xFFFF9800); ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#FF9800"));
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 8) {
	 * //stripper.change(0xFF43A047); ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#43A047"));
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else if (action_random == 9) {
	 * //stripper.change(0xFF757575); ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#757575"));
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); } else {
	 * //stripper.change(0xFFE040FB); ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#E040FB"));
	 * ab.setBackgroundDrawable(colorDrawable);
	 * ab.setDisplayShowTitleEnabled(false);
	 * ab.setDisplayShowTitleEnabled(true); }
	 * 
	 * // Toast.makeText(this, "RAndom numbers "+action_random, //
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 */

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	// if (mDrawerLayout.onOptionsItemSelected(item)) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);

	// }

	// @Override
	// public void onConfigurationChanged(Configuration newconfig) {
	// TODO Auto-generated method stub
	// super.onConfigurationChanged(newconfig);
	// drawerListener.onConfigurationChanged(newconfig);
	// if (getResources().getConfiguration().orientation ==
	// Configuration.ORIENTATION_LANDSCAPE) {
	// setContentView(R.layout.landscapeView);
	//
	// } else {
	// setContentView(R.layout.portraitView);
	// }
	// }

	@Override
	public void onBackPressed() {
		moveTaskToBack(true); // "Hide"
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * ((keyCode == KeyEvent.KEYCODE_BACK)) { onPause(); } return
	 * super.onKeyDown(keyCode, event); }
	 */

	// @Override
	// protected void onPostCreate(Bundle Streaksarehere) { // TODO
	// super.onPostCreate(Streaksarehere);
	// drawerListener.syncState();
	// }

	/*
	 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { // TODO Auto-generated method stub
	 * listeww.setItemChecked(arg2, true); selectItem(arg2); if (arg2 == 0) {
	 * Intent lala = new Intent("tech.hanuor.mup.INBUILTBROWSER");
	 * startActivity(lala); if (networkInfo != null &&
	 * networkInfo.isConnected()) { // fetch data Toast.makeText(this,
	 * "Successfully connected to the internet", Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 * 
	 * else { Toast.makeText(this, "No Internet Connection",
	 * Toast.LENGTH_SHORT).show(); } } else if (arg2 == 3) { //
	 * Toast.makeText(this, //
	 * "Mup® is a registered trademark of Hanuor.Developed By Hanuor.©Hanuor.All Rights Reserved."
	 * , // Toast.LENGTH_LONG).show(); Intent zolala = new
	 * Intent("tech.hanuor.mup.HAN"); startActivity(zolala); } else if (arg2 ==
	 * 2) { Intent colala = new Intent("tech.hanuor.mup.ABOUT");
	 * startActivity(colala); } else { // ActionBarGen(); }
	 * 
	 * }
	 * 
	 * public void selectItem(int arg2) { // TODO Auto-generated method stub
	 * listeww.setItemChecked(arg2, true);
	 * 
	 * }
	 * 
	 * public void setTitle(String title) {
	 * getSupportActionBar().setTitle(title); }
	 * 
	 * }
	 */

	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int i) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
			if (i == 0) {
				fragment = new ArtistDisplay();
			}
			if (i == 1) {
				fragment = new MediaPlayerFragment();
			}

			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title = new String();
			if (position == 0) {
				String s = "Classic";
				SpannableString ss = new SpannableString(s);
				ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 7, 0);

				return ss;
			}
			if (position == 1) {
				String q = "Music Player";
				SpannableString sq = new SpannableString(q);
				sq.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 12, 0);

				return sq;
			}

			return null;

		}
	}
}
