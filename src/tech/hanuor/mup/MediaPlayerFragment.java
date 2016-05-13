package tech.hanuor.mup;

import java.util.Random;

import tech.hanuor.mup.R;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerFragment extends Fragment implements
		OnItemClickListener, OnClickListener, OnSeekBarChangeListener {
	Cursor c2, cplayer, cingplayer;
	int index, newly;
	int truey;
	int count, countFetcher;
	int i, newpos, shufflepos;
	String str;
	int flag = 0;
	int newposcount = 0;
	static MediaPlayer mp;
	Button playButton;
	RelativeLayout rel;
	Button pauseButton;
	Button resetButton;
	Button forwardButton;
	double startTime = 0;
	double finalTime = 0;
	SeekBar songprogressbar;
	private TextView songCurrentDurationLabel;
	private TextView songTotalDurationLabel;

	// SeekBar seekbar;
	Handler myhandler = new Handler();
	static int oneTimeOnly = 0;
	double pauseTime;
	String fn, metaArt;
	Handler mHandler2 = new Handler();
	Handler mHandler = new Handler();
	int selectedListItem;
	ListView lv;
	private Utilities utils;
	MediaMetadataRetriever metaRetriver;
	byte[] art;
	Cursor cursor;
	int currentVolume;
	Cursor chleas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.mediaplayer, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// ActionBar abes = getActivity().getActionBar();
		super.onActivityCreated(savedInstanceState);
		initialStore();
		rel.setVisibility(View.GONE);
		metaRetriver = new MediaMetadataRetriever();
		AudioManager audio = (AudioManager) getActivity().getSystemService(
				Context.AUDIO_SERVICE);
		currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		String mediasongs[] = { MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA };
		cplayer = getActivity().managedQuery(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediasongs,
				MediaStore.Audio.Media.DURATION + ">= 180000", null, null);
		String[] ALBUM_PROJ = { MediaStore.Audio.Albums.ALBUM_ART };
		String selection = MediaStore.Audio.Albums.ALBUM + "=?";
		if (cplayer == null && cplayer.getCount() == 0) {
			Toast.makeText(getActivity(), "Failed to load content.",
					Toast.LENGTH_LONG).show();
		}
		// cingplayer = getActivity().managedQuery(
		// MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,ALBUM_PROJ,selection,null,null);
		/*
		 * cursor = getActivity() .managedQuery(
		 * MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[] {
		 * MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART },
		 * MediaStore.Audio.Albums._ID + "=?", new String[] { String
		 * .valueOf(MediaStore.Audio.Media.ALBUM_ID) }, null); metaRetriver =
		 * new MediaMetadataRetriever(); metaRetriver.setDataSource("/sdcard/");
		 */
		//
		// String[] values = new String[]{aSongsList.getAlbum()};
		// Cursor cur =
		// c.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
		// ALBUM_PROJ, selection, values, null);
		// int cnt;
		// if (cur != null) {
		// cnt = cur.getCount();
		// if (cnt > 0) {
		// cur.moveToFirst();
		// do {
		// String art = cur.getString(cur.getColumnIndex(ALBUM_PROJ[0]));
		// if (art != null)
		// aSongsList.setAlbumArt(art);
		// } while (cur.moveToNext());
		// cur.close();
		// }
		// }
		// metaArt =
		// cingplayer.getString(cingplayer.getColumnIndex(ALBUM_PROJ[1]));
		// metaRetriver.setDataSource(metaArt);
		String columns[] = { MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.TITLE };
		String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE
				+ "=?";
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				"mp3");
		String[] selectionArgsMp3 = new String[] { mimeType };
		chleas = getActivity().managedQuery(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
				selectionMimeType, selectionArgsMp3, null);

		String cols[] = { MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.TITLE };
		c2 = getActivity().managedQuery(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols,
				MediaStore.Audio.Media.DURATION + ">= 180000", null, null);
		for (i = 0; i < c2.getColumnCount(); i++)
			Log.d("col_info", c2.getColumnName(i));
		count = c2.getCount();
		// Toast.makeText(getActivity(),
		// count + " sound files loaded successfully", 50).show();
		mp = new MediaPlayer();
		utils = new Utilities();
		/*
		 * cursor = getActivity().managedQuery(
		 * MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]
		 * {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ARTIST},
		 * MediaStore.Audio.Albums._ID+ "=?", new String[]
		 * {MediaStore.Audio.Media.ALBUM_ID}, null);
		 */
		/*
		 * for (int i = 0; i < tasks.size(); i++) { Log.d("Running task",
		 * "Running task: " + tasks.get(i).baseActivity.toShortString() +
		 * "\t\t ID: " + tasks.get(i).id); }
		 */
		lv = (ListView) getView().findViewById(R.id.listView1);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setAdapter(new MediaMusicAdapter(getActivity()));

		lv.setOnItemClickListener(this);
		resetButton.setOnClickListener(this);
		forwardButton.setOnClickListener(this);
		playButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		songprogressbar.setOnSeekBarChangeListener(this);
		mp.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mplay) {

				// playButton.setVisibility(View.VISIBLE);
				// pauseButton.setVisibility(View.INVISIBLE);
				try {
					if (newpos <= count) {
						cplayer.moveToPosition(newpos + 1);
						newpos = newpos + 1;
						String next = cplayer.getString(2);
						lv.setItemChecked(newpos, true);
						lv.setSelection(newpos);
						mp.reset();
						try {
							mp.setDataSource(next);
							mp.prepare();
						} catch (Exception e) {
							e.printStackTrace();
						}
						mp.start();
						pauseButton.setVisibility(View.VISIBLE);
						playButton.setVisibility(View.INVISIBLE);
					} else {
						rel.setVisibility(View.GONE);
						mp.stop();
						pauseButton.setVisibility(View.INVISIBLE);
						playButton.setVisibility(View.VISIBLE);
						Toast.makeText(getActivity(),
								"List finished! Please select another song.",
								Toast.LENGTH_SHORT).show();
					}

				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// seekbar.setClickable(false);

	}

	private void initialStore() {
		// TODO Auto-generated method stub
		songCurrentDurationLabel = (TextView) getView().findViewById(
				R.id.currentDuration);
		songTotalDurationLabel = (TextView) getView().findViewById(
				R.id.totalDuration);

		songprogressbar = (SeekBar) getView().findViewById(R.id.seekBar1);
		rel = (RelativeLayout) getView().findViewById(R.id.ConP);
		playButton = (Button) getView().findViewById(R.id.play);
		pauseButton = (Button) getView().findViewById(R.id.pause);
		resetButton = (Button) getView().findViewById(R.id.reset);
		forwardButton = (Button) getView().findViewById(R.id.forward);
		// seekbar = (SeekBar) getView().findViewById(R.id.seekBar1);
	}

	// public void play(View view){
	// mp.start();
	// finalTime = mp.getDuration();
	// startTime = mp.getCurrentPosition();
	// /*if(oneTimeOnly == 0){
	// seekbar.setMax((int) finalTime);
	// oneTimeOnly = 1;
	// }*/
	// //TimeUnit.MILLISECONDS.toMinutes((long) finalTime);
	// //TimeUnit.MILLISECONDS.toSeconds((long) finalTime);
	// //TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)finalTime));
	// //TimeUnit.MILLISECONDS.toMinutes((long) startTime);
	// //TimeUnit.MILLISECONDS.toSeconds((long) startTime);
	// //TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime));
	// //seekbar.setProgress((int) startTime);
	// //myhandler.postDelayed(UpdateSongTime, 100);
	//
	//
	// //
	// }
	/*
	 * Runnable run = new Runnable() {
	 * 
	 * @Override public void run() { // // TODO Auto-generated method stub //
	 * seekbar.setProgress(mp.getCurrentPosition()); myhandler.postDelayed(run,
	 * 1000); }
	 * 
	 * };
	 */

	private class MediaMusicAdapter extends BaseAdapter {
		Context music_context;

		private MediaMusicAdapter(Context c) {
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
			// ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) music_context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View tv = inflater.inflate(R.layout.mediaplayerrow, parent, false);
			// String coverPath = cursor.getString(cursor
			// .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
			// Drawable img = Drawable.createFromPath(coverPath);
			// ImageView album_art = (ImageView)
			// tv.findViewById(R.id.imageView1);
			// album_art.setImageDrawable(img);

			/*
			 * String coverPath = cursor.getString(cursor
			 * .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)); Drawable img
			 * = Drawable.createFromPath(coverPath); ImageView album_art =
			 * (ImageView) tv.findViewById(R.id.imageView1);
			 * album_art.setImageDrawable(img);
			 */
			// if (cursor.moveToPosition(position)) {
			// String path = cursor.getString(cursor
			// .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)); // do
			// Drawable img = Drawable.createFromPath(path);

			// album_art.setImageDrawable(img);

			// }
			// try {
			// art = metaRetriver.getEmbeddedPicture();
			// Bitmap songImage = BitmapFactory.decodeByteArray(art, 0,
			// art.length);
			// album_art.setImageBitmap(songImage);
			// album.setText(metaRetriver
			// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
			// artist.setText(metaRetriver
			// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
			// genre.setText(metaRetriver
			// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
			// } catch (Exception e) {
			// album_art.setBackgroundColor(Color.GRAY);
			// album.setText("Unknown Album");
			// artist.setText("Unknown Artist");
			// genre.setText("Unknown Genre");
			// }

			/*
			 * try { art = metaRetriver.getEmbeddedPicture(); Bitmap songImage =
			 * BitmapFactory.decodeByteArray(art, 0, art.length);
			 * album_art.setImageBitmap(songImage); } catch (Exception e) {
			 * album_art.setBackgroundColor(Color.GRAY); }
			 */TextView titleArtist = (TextView) tv
					.findViewById(R.id.Titletext);
			TextView songName = (TextView) tv.findViewById(R.id.subText);
			Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/roboto.ttf");
			titleArtist.setTypeface(tf);
			songName.setTypeface(tf);
			// c2.moveToPosition(position);
			c2.moveToPosition(position);
			String s = c2.getString(0);
			String subs = c2.getString(1);
			titleArtist.setText(subs);
			songName.setText(s);
			return tv;
		}

	}

	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// ((AbsListView) arg0).setItemChecked(position, true);
		// view.setBackgroundColor(Color.BLUE);

		lv.setItemChecked(position, true);

		// lv.setSelection(position);
		rel.setVisibility(View.VISIBLE);
		// rel.setBackgroundResource(R.drawable.play_gray);
		newpos = position;
		newly = position;
		truey = position;
		mp.reset();
		cplayer.moveToPosition(position);
		fn = cplayer.getString(2);
		songprogressbar.setProgress(0);
		songprogressbar.setMax(100);
		updateProgressBar();
		// seekbar.setProgress(mp.getCurrentPosition());
		// myhandler.postDelayed(run, 1000);
		try {
			mp.setDataSource(fn);
			mp.prepare();
			mp.start();

			if (mp.isPlaying()) {
				playButton.setVisibility(View.INVISIBLE);
				pauseButton.setVisibility(View.VISIBLE);
			} else {
				pauseButton.setVisibility(View.INVISIBLE);
				playButton.setVisibility(View.VISIBLE);

				// handle the pause event

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void shuffler() {
		Random rand = new Random();
		shufflepos = rand.nextInt(count);
	}

	@Override
	public void onClick(View viewer) {
		// TODO Auto-generated method stub
		int currentPosition;
		int totalDuration;
		switch (viewer.getId()) {
		case R.id.reset:
			// if(newly == truey){

			mp.reset();
			flag = 1;
			if (newpos != newly) {
				newpos = newpos - newposcount;
				lv.setItemChecked(newpos, true);
				lv.setSelection(newpos);
			}

			try {
				mp.setDataSource(fn);
				mp.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mp.start();
			// rel.setBackgroundResource(R.drawable.play_gray);
			pauseButton.setVisibility(View.VISIBLE);
			playButton.setVisibility(View.INVISIBLE);

			/*
			 * else{ try { if (newpos <= count) { cplayer.moveToPosition(newpos
			 * - 1); newpos = newpos - 1; String next = cplayer.getString(2);
			 * mp.reset(); lv.setItemChecked(newpos, true);
			 * lv.setSelection(newpos); try { mp.setDataSource(next);
			 * mp.prepare(); } catch (Exception e) { e.printStackTrace(); }
			 * mp.start(); pauseButton.setVisibility(View.VISIBLE);
			 * playButton.setVisibility(View.INVISIBLE); } else {
			 * //rel.setBackgroundColor(0xFFFFCDD2); mp.stop();
			 * pauseButton.setVisibility(View.INVISIBLE);
			 * playButton.setVisibility(View.VISIBLE);
			 * Toast.makeText(getActivity(),
			 * "Cannot play, reached the end of the list",
			 * Toast.LENGTH_SHORT).show(); } } catch (Exception e) {
			 * e.printStackTrace(); } }
			 */
			break;

		case R.id.pause:
			// rel.setBackgroundResource(R.drawable.stop_flop);
			mp.pause();

			// onStopTrackingTouch(songprogressbar);
			mHandler.removeCallbacks(mUpdateTimeTask);
			pauseTime = mp.getCurrentPosition();
			// updateProgressBar();
			// totalDuration = mp.getDuration();
			// currentPosition =
			// utils.progressToTimer(songprogressbar.getProgress(),
			// totalDuration);
			pauseButton.setVisibility(View.INVISIBLE);
			playButton.setVisibility(View.VISIBLE);
			break;
		case R.id.play:
			// rel.setBackgroundResource(R.drawable.play_gray);
			// mp.seekTo((int) pauseTime);
			// Thread timer = new Thread() {
			// public void run() {
			// / try {
			// mp.setVolume(0, currentVolume);
			// }
			// sleep();

			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// totalDuration = mp.getDuration();
			// currentPosition =
			// utils.progressToTimer(songprogressbar.getProgress(),
			// (int) totalDuration);
			// onStopTrackingTouch(songprogressbar);
			// if(currentPosition != pauseTime){
			// mp.seekTo((int)currentPosition);
			// }
			/*
			 * // else{ long totalDuratio = mp.getDuration(); long
			 * currentDuration = mp.getCurrentPosition();
			 * 
			 * int progress = (int) (utils.getProg(currentDuration,
			 * totalDuratio)); songprogressbar.setProgress(progress);
			 */
			totalDuration = mp.getDuration();
			currentPosition = utils.progressToTimer(
					songprogressbar.getProgress(), totalDuration);
			mp.seekTo((int) currentPosition);

			// update timer progress again
			updateProgressBar();
			// }
			mp.start();
			// updateProgressBar();
			pauseButton.setVisibility(View.VISIBLE);
			playButton.setVisibility(View.INVISIBLE);
			break;
		case R.id.forward:
			// rel.setBackgroundResource(R.drawable.play_gray);

			try {
				if (newpos <= count) {
					cplayer.moveToPosition(newpos + 1);
					newpos = newpos + 1;
					if (flag == 0) {
						newposcount++;
					} else {
						newposcount = 0;
						newposcount++;
					}
					String next = cplayer.getString(2);
					mp.reset();
					lv.setItemChecked(newpos, true);
					lv.setSelection(newpos);
					try {
						mp.setDataSource(next);
						mp.prepare();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mp.start();
					pauseButton.setVisibility(View.VISIBLE);
					playButton.setVisibility(View.INVISIBLE);
				} else {
					// rel.setBackgroundColor(0xFFFFCDD2);
					mp.stop();
					pauseButton.setVisibility(View.INVISIBLE);
					playButton.setVisibility(View.VISIBLE);
					Toast.makeText(getActivity(),
							"Cannot play, reached the end of the list",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * //Shuffle Code is Here shuffler();
			 * cplayer.moveToPosition(shufflepos); String next =
			 * cplayer.getString(2); mp.reset(); try{ mp.setDataSource(next);
			 * mp.prepare(); }catch(Exception e){ e.printStackTrace(); }
			 * mp.start(); pauseButton.setVisibility(View.VISIBLE);
			 * playButton.setVisibility(View.INVISIBLE);
			 */

		}
	}

	public void updateProgressBart() {
		mHandler2.postDelayed(mUpdateTimeTaskt, 1);
	}

	private Runnable mUpdateTimeTaskt = new Runnable() {
		public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();

			// Displaying Total Duration time
			songTotalDurationLabel.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			songCurrentDurationLabel.setText(""
					+ utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProg(currentDuration, totalDuration));
			// Log.d("Progress", ""+progress);
			// songprogressbar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler2.postDelayed(this, 1);
		}
	};

	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 0);
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();

			// Displaying Total Duration time
			songTotalDurationLabel.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			songCurrentDurationLabel.setText(""
					+ utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
			// Log.d("Progress", ""+progress);
			songprogressbar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 0);
		}
	};

	public void updateProgressBar2() {
		mHandler.postDelayed(mUpdateTimeTask2, 1);
	}

	private Runnable mUpdateTimeTask2 = new Runnable() {
		public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();

			// Displaying Total Duration time
			songTotalDurationLabel.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			songCurrentDurationLabel.setText(""
					+ utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProg(currentDuration, totalDuration));
			// Log.d("Progress", ""+progress);
			songprogressbar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 1);
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekbar, int progress, boolean ontouch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekbar) {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mUpdateTimeTask);

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);
		// currentPosition = currentPosition + 1000;

		if (currentPosition != pauseTime) {
			mp.seekTo(currentPosition);
		} else {
			// forward or backward to certain seconds
			mp.seekTo((int) pauseTime);

		}
		// update timer progress again
		updateProgressBar();

	}
}
