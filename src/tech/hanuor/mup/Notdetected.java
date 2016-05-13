package tech.hanuor.mup;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;

public class Notdetected extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notde);
		/*ActionBar ab = getActionBar();
		SpannableString s = new SpannableString("   Mup");
		s.setSpan(new TypefaSpan(this,"billanew.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ab.setTitle(s);
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#4A148C"));
		ab.setBackgroundDrawable(colorDrawable);*/
		/*Thread timer = new Thread() {
			public void run() {
				try {
					sleep(10000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMain = new Intent("tech.hanuor.mup.MAINACTIVITY");
					startActivity(openMain);
				}

			}
		};
		timer.start();*/
	}
	

}
