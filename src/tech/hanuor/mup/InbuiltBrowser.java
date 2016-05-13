package tech.hanuor.mup;

import tech.hanuor.mup.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;

public class InbuiltBrowser extends Activity implements OnClickListener {
	WebView theBrow;
	Button bringBack;
	Button bringForward;
	Button bRefresh;
	EditText inputText;
	Button bingo;
	ProgressBar progressbar;
	RelativeLayout rel;
	final Activity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.inbuiltbrowser);
		/*ActionBar ab = getActionBar();
		SpannableString s = new SpannableString("   Mup");
		s.setSpan(new TypefaSpan(this, "billanew.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ab.setTitle(s);
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#4A148C"));
		ab.setBackgroundDrawable(colorDrawable);*/
		// SpannableStringBuilder ss = new SpanningStringBuilder(s);
		initialization();
		try {
			theBrow.loadUrl("https://www.google.com");
		} catch (Exception e) {
			e.printStackTrace();
		}

		theBrow.setWebViewClient(new OverrideBrowserClient());
		theBrow.getSettings().setJavaScriptEnabled(true);
		theBrow.getSettings().setLoadWithOverviewMode(true);
		theBrow.getSettings().setUseWideViewPort(true);
		theBrow.getSettings().getBuiltInZoomControls();
		theBrow.getSettings().enableSmoothTransition();
		theBrow.clearCache(true);
		theBrow.clearHistory();
		bringBack.setOnClickListener(this);
		bRefresh.setOnClickListener(this);
		bringForward.setOnClickListener(this);
		bingo.setOnClickListener(this);
		inputText.setOnClickListener(this);

	}

	private void initialization() {
		// TODO Auto-generated method stub
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		progressbar.getIndeterminateDrawable().setColorFilter(0xFFFFC107,
				android.graphics.PorterDuff.Mode.MULTIPLY);
		theBrow = (WebView) findViewById(R.id.clickBrower);
		rel = (RelativeLayout) findViewById(R.id.reL);
		bringBack = (Button) findViewById(R.id.bBack);
		bringForward = (Button) findViewById(R.id.bForward);
		bRefresh = (Button) findViewById(R.id.brefresh);
		inputText = (EditText) findViewById(R.id.editUrl);
		bingo = (Button) findViewById(R.id.bGo);
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// if(event.getAction()==event.ACTION_MOVE){
	// rel.setVisibility(View.VISIBLE);
	// }
	// else{
	// rel.setVisibility(View.GONE);
	//
	// }
	// return false;
	// }

	public class OverrideBrowserClient extends WebViewClient {

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
			//
			progressbar.setVisibility(View.GONE);

		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bGo:
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
			String theWebsite = inputText.getText().toString();
			theBrow.loadUrl(("http:" + theWebsite));
			inputText.setCursorVisible(false);

			break;
		case R.id.editUrl:
			inputText.setCursorVisible(true);
			break;
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
