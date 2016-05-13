package tech.hanuor.mup;

import tech.hanuor.mup.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class Browser extends Activity implements OnClickListener {
	WebView theBrow;
	Button bringBack;
	Button bringForward;
	Button bRefresh;
	ProgressBar progressbar;
	String finalS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Fullscreen Change!

		/*
		 * ActionBar ab = getActionBar(); SpannableString s = new
		 * SpannableString("   Mup"); s.setSpan(new TypefaSpan(this,
		 * "billanew.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		 * ab.setTitle(s); ColorDrawable colorDrawable = new ColorDrawable(
		 * Color.parseColor("#4A148C"));
		 * ab.setBackgroundDrawable(colorDrawable);
		 */
		setContentView(R.layout.browser);
		initialization();
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);

		progressbar.getIndeterminateDrawable().setColorFilter(0xFFFFC107,
				android.graphics.PorterDuff.Mode.MULTIPLY);
		Intent brow = this.getIntent();
		String reciever = brow.getExtras().getString("send");
		finalS = capitalizeString(reciever);
		// Toast.makeText(this, finalS + " ", Toast.LENGTH_LONG).show();
		/*
		 * WordUtils.capitalize(reciever); int calci = reciever.length(); for
		 * (int i = 0; i < calci; i++) { char ch; ch = reciever.charAt(i); if
		 * (ch == ' ') { i = i + 1; char chr = reciever.charAt(i); char A =
		 * chr.toUpperCase(); Toast.makeText(this, chr + " ",
		 * Toast.LENGTH_LONG).show(); }
		 */

		// }
		try {
			theBrow.loadUrl("https://en.wikipedia.org/wiki/" + finalS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Toast.makeText(this, reciever + "  Recieved", Toast.LENGTH_SHORT)
		// .show();
		theBrow.setWebViewClient(new clickClient());
		theBrow.getSettings().setJavaScriptEnabled(true);
		theBrow.getSettings().setLoadWithOverviewMode(true);
		theBrow.getSettings().setUseWideViewPort(true);
		// theBrow.getSettings().setBuiltInZoomControls(true);
		bringBack.setOnClickListener(this);
		bRefresh.setOnClickListener(this);
		bringForward.setOnClickListener(this);
	}

	public static String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.'
					|| chars[i] == '\'') { // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	private void initialization() {
		// TODO Auto-generated method stub
		theBrow = (WebView) findViewById(R.id.clickBrower);
		bringBack = (Button) findViewById(R.id.bBack);
		bringForward = (Button) findViewById(R.id.bForward);
		bRefresh = (Button) findViewById(R.id.brefresh);
	}

	public class clickClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView v, String url) {
			v.loadUrl(url);
			return true;

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			// TODO Auto-generated method stub

			super.onPageStarted(view, url, favicon);
			progressbar.setVisibility(View.VISIBLE);

		}

		// @Override

		public void onPageFinished(WebView view, String url) {

			// TODO Auto-generated method stub

			super.onPageFinished(view, url);

			// Toast.makeText(getApplicationContext(),
			// "Loading done , function executed", Toast.LENGTH_SHORT)
			// .show();

			progressbar.setVisibility(View.GONE);

		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bBack:
			if (theBrow.canGoBack()) {
				theBrow.goBack();
			}
			break;
		case R.id.brefresh:
			theBrow.reload();
			break;
		case R.id.bForward:
			if (theBrow.canGoForward()) {
				theBrow.goForward();
			}
			break;
		}
	}

}
