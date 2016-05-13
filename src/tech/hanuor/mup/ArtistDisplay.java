package tech.hanuor.mup;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import tech.hanuor.mup.R;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArtistDisplay extends Fragment implements OnItemClickListener {
	Cursor c,chelsea;
	int index;
	int count, countFetcher;
	int i;
	String str;
	int action_random;
	ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.artistdisplay, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String columns[] = { MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM };
		
		/*chelsea = getActivity().managedQuery(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
				selectionMimeType, selectionArgsMp3, null);
		String where = MediaStore.Audio.Media.IS_MUSIC + "=1";*/

		String cols[] = { MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ALBUM_ID };
		String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE
				+ "=?";
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				"mp3");
		String[] selectionArgsMp3 = new String[] { mimeType };
		c = getActivity().managedQuery(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols,selectionMimeType,selectionArgsMp3,
				null);

		for (i = 0; i < c.getColumnCount(); i++)
			Log.d("col_info", c.getColumnName(i));
		count = c.getCount();
		//Toast.makeText(getActivity(),
			//	count + " sound files loaded successfully", 50).show();
		if(c==null && c.getCount()==0){
			Toast.makeText(getActivity(), "Failed to load content.",Toast.LENGTH_LONG).show();
		}
		lv = (ListView) getView().findViewById(R.id.listView1);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setAdapter(new MusicAdapter(getActivity()));
		lv.setOnItemClickListener(this);

	}

	/*
	 * private void ActionBarGen() { // TODO Auto-generated method stub int max
	 * = 5; int min = 0; Random rand = new Random();
	 * 
	 * action_random = rand.nextInt(max-min+1)+min; ActionBar ab =
	 * getActivity().getActionBar(); if(action_random==0){ ColorDrawable
	 * colorDrawable = new ColorDrawable( Color.parseColor("#4A148C"));
	 * ab.setBackgroundDrawable(colorDrawable);} else if(action_random==1){
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#3F51B5")); ab.setBackgroundDrawable(colorDrawable);}
	 * else if(action_random==2){ ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#004D40"));
	 * ab.setBackgroundDrawable(colorDrawable);} else if(action_random==3){
	 * ColorDrawable colorDrawable = new ColorDrawable(
	 * Color.parseColor("#3E2723")); ab.setBackgroundDrawable(colorDrawable);}
	 * else if(action_random==4){ ColorDrawable colorDrawable = new
	 * ColorDrawable( Color.parseColor("#F44336"));
	 * ab.setBackgroundDrawable(colorDrawable);} else { ColorDrawable
	 * colorDrawable = new ColorDrawable( Color.parseColor("#1A237E"));
	 * ab.setBackgroundDrawable(colorDrawable);}
	 * 
	 * //Toast.makeText(this, "RAndom numbers "+action_random,
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 */

	private class MusicAdapter extends BaseAdapter {
		Context music_context;

		private MusicAdapter(Context c) {
			music_context = c;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) music_context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View tv = inflater.inflate(R.layout.single_row, parent, false);

			TextView titleArtist = (TextView) tv.findViewById(R.id.Titletext);
			TextView songName = (TextView) tv.findViewById(R.id.subText);
			ImageView aa = (ImageView) tv.findViewById(R.id.imageView1);
			Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/roboto.ttf");
			c.moveToPosition(position);
			titleArtist.setTypeface(tf);
			songName.setTypeface(tf);
			String s = c.getString(0);
			String subs = c.getString(1);
			titleArtist.setText(s);
			songName.setText(subs);

			return tv;
		}

	}

	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		lv.setItemChecked(position, true);
		ConnectivityManager connec = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connec.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			Toast.makeText(getActivity(),
					"Successfully connected to the internet",
					Toast.LENGTH_SHORT).show();
			char ch;
			view.setSelected(true);

			c.moveToPosition(position);
			// str = c.getString(0);
			str = c.getString(0);
			str = str.toLowerCase();
			// int strlength = str.length();

			// Toast.makeText(getApplicationContext(), strlength + " "+ str,
			// Toast.LENGTH_LONG)
			// .show();
			/*
			 * str += c.getString(1); str = str.trim(); int artlength =
			 * str.length(); for (int j = 0; i < artlength; j++) { ch =
			 * str.charAt(i); if (ch != ' ') { newstring = ch + newstring;
			 * 
			 * } else{ break; } }
			 */
			// Toast.makeText(getActivity(), str + " ", Toast.LENGTH_LONG).show();
			Intent brow = new Intent("tech.hanuor.mup.BROWSER");
			brow.putExtra("send", str);
			startActivity(brow);
		} else {
			// display error
			Intent openMain = new Intent("tech.hanuor.mup.NOTDETECTED");
			startActivity(openMain);
			//Toast.makeText(getActivity(), "Please connect to the internet",
					//Toast.LENGTH_SHORT).show();
		}
		

	}
}
