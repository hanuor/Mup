package tech.hanuor.mup;

import tech.hanuor.mup.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Material_Navi_Frag extends Fragment implements OnItemClickListener {
	public static final String PREF_FILE_NAME = "testpref";
	public static final String KEY_USER_LEARNED = "user_learned_drawer";
	ActionBarDrawerToggle drawertoggle;
	DrawerLayout mdrawerlayout;
	private boolean userlearneddrawer;
	private boolean mFromSavedInstanceState;
	View containView;
	ListView listeww;
	NaviAdapter naviadap;
	String[] listDisplay;
	NetworkInfo networkInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.material_navi_frag, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listeww = (ListView) getView().findViewById(R.id.drawerList);

		listeww.setAdapter(new NaviAdapter(getActivity()));
		listeww.setOnItemClickListener(this);
		userlearneddrawer = Boolean.valueOf(readfromPreferences(getActivity(),
				KEY_USER_LEARNED, "false"));
		if (savedInstanceState != null) {
			mFromSavedInstanceState = true;
		}
	}

	public void setUp(int FragmentId, DrawerLayout drawerlayout, Toolbar toolbar) {
		containView = getActivity().findViewById(FragmentId);
		mdrawerlayout = drawerlayout;
		drawertoggle = new ActionBarDrawerToggle(getActivity(), drawerlayout,
				toolbar, R.string.drawer_open, R.string.drawer_close) {

			@SuppressLint("NewApi")
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				getActivity().invalidateOptionsMenu();

			}

			@SuppressLint("NewApi")
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				if (!userlearneddrawer) {
					userlearneddrawer = true;
					savetoPreferences(getActivity(), KEY_USER_LEARNED,
							userlearneddrawer + "");
				}
				getActivity().invalidateOptionsMenu();
				super.onDrawerOpened(drawerView);
			}

		};
		if (!userlearneddrawer && !mFromSavedInstanceState) {
			mdrawerlayout.openDrawer(containView);
		}
		mdrawerlayout.setDrawerListener(drawertoggle);
		mdrawerlayout.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				drawertoggle.syncState();
			}

		});
	}

	@SuppressLint("NewApi")
	public static void savetoPreferences(Context context, String prefName,
			String prefValue) {
		SharedPreferences sharedpref = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedpref.edit();
		editor.putString(prefName, prefValue);
		editor.apply();
	}

	public static String readfromPreferences(Context context, String prefName,
			String defValue) {
		SharedPreferences sharedpref = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		return sharedpref.getString(prefName, defValue);
	}

	class NaviAdapter extends BaseAdapter {

		Context contextu;
		int[] images = { R.drawable.ic_brow, R.drawable.ic_guide,
				R.drawable.ic_info };

		public NaviAdapter(Context context) {
			contextu = context;
			listDisplay = context.getResources()
					.getStringArray(R.array.Details);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listDisplay.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listDisplay[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			// View senorita = null;
			// if (view == null) {
			// Resources resources = getApplicationContext().getResources();

			// String[] textString = resources.getStringArray(R.array.Details);
			// Toast.makeText(getActivity(), "Incheck",
			// Toast.LENGTH_LONG).show();
			LayoutInflater inflater = (LayoutInflater) contextu
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View se = inflater.inflate(R.layout.navigation_custom, parent,
					false);
			// } else {
			// senorita = view;
			// }
			TextView navisubtext = (TextView) se.findViewById(R.id.textnavi);
			TextView navitext = (TextView) se.findViewById(R.id.naviText);
			ImageView img = (ImageView) se.findViewById(R.id.imageView1);
			navitext.setText(listDisplay[position]);
			if (position == 0) {
				navisubtext.setText("Access the world!");
			} else if (position == 2) {
				navisubtext.setText("Know the details!");
			} else {
				navisubtext.setText("How to use Mup?");
			}
			/*
			 * else{ navisubtext.setText("Change the color of the title bar!");
			 * }
			 */
			/*
			 * if (networkInfo != null && networkInfo.isConnected()) { // fetch
			 * data //Toast.makeText(getActivity(),
			 * "Successfully connected to the internet",
			 * Toast.LENGTH_SHORT).show(); navitext.setText(Incheck[0]); } else
			 * { // display error //Toast.makeText(getActivity(),
			 * "Please connect to the internet", Toast.LENGTH_SHORT).show();
			 * navitext.setText(Incheck[1]); } //
			 * navisubtext.setText(textString[2]);
			 */
			img.setImageResource(images[position]);
			return se;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg, View view, int position, long id) {
		// TODO Auto-generated method stub
		selectItem(position);
		ConnectivityManager connec = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connec.getActiveNetworkInfo();
		if (position == 0) {
			Intent lala = new Intent("tech.hanuor.mup.INBUILTBROWSER");
			startActivity(lala);
			if (networkInfo != null && networkInfo.isConnected()) {
				// fetch data
				Toast.makeText(getActivity(),
						"Successfully connected to the internet",
						Toast.LENGTH_SHORT).show();

			}

			else {
				Toast.makeText(getActivity(), "No Internet Connection",
						Toast.LENGTH_SHORT).show();
			}
		} else if (position == 2) {
			// Toast.makeText(this,
			// "Mup® is a registered trademark of Hanuor.Developed By Hanuor.©Hanuor.All Rights Reserved.",
			// Toast.LENGTH_LONG).show();
			Intent zolala = new Intent("tech.hanuor.mup.HAN");
			startActivity(zolala);
		} else if (position == 1) {
			Intent colala = new Intent("tech.hanuor.mup.ABOUT");
			startActivity(colala);
		} else {

		}

	}

	private void selectItem(int position) {
		// TODO Auto-generated method stub
		listeww.setItemChecked(position, true);
	}

}