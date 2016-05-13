package tech.hanuor.mup;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

public class Han extends ActionBarActivity {
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hanuor);
		toolbar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolbar);
		SpannableString s = new SpannableString(" Mup");
		s.setSpan(new TypefaSpan(this, "billanew.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getSupportActionBar().setTitle(s);
		/*
		 * ActionBar ab = getActionBar(); SpannableString s = new
		 * SpannableString("   Mup"); s.setSpan(new
		 * TypefaSpan(this,"billanew.ttf"), 0, s.length(),
		 * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); ab.setTitle(s); ColorDrawable
		 * colorDrawable = new ColorDrawable( Color.parseColor("#4A148C"));
		 * ab.setBackgroundDrawable(colorDrawable);
		 */
		/*
		 * TextView title = (TextView) findViewById(R.id.tMup); Typeface tf =
		 * Typeface.createFromAsset(this.getAssets(), "fonts/billanew.ttf");
		 */
		// title.setTypeface(tf);
		TextView subs = (TextView) findViewById(R.id.textcent);
		Typeface fg = Typeface.createFromAsset(this.getAssets(),
				"fonts/roboto.ttf");
		subs.setTypeface(fg);

	}
}