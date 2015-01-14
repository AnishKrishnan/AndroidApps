package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Show the Up button in the action bar.
		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		setContentView(R.layout.activity_display_message);
		TextView textView = (TextView)findViewById(R.id.repeat_word);
		textView.setTextSize(40);
		textView.setText(message);
		
		//setContentView(textView);
		

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void goToFragment(View view){
    	Intent intent = new Intent(this, ActivityWithFragments.class);
    	intent.putExtra(ActivityWithFragments.FRAGMENT_MESSAGE, message);
    	startActivity(intent);
		
	}

}
