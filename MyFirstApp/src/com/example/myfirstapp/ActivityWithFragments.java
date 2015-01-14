package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class ActivityWithFragments extends FragmentActivity {
	
	String message;
	public final static String FRAGMENT_MESSAGE = "com.example.ActivityWithFragments.FRAGMENT_MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_with_fragments);
		Intent intent = getIntent();
		
		if(findViewById(R.id.fragmentContainer) != null){
			message = intent.getStringExtra(FRAGMENT_MESSAGE);
			
			ArticleFragment frag = new ArticleFragment();
			
			frag.setArguments(intent.getExtras());
			
			getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, frag).commit();
		}
			
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.activity_activity_with_fragments, menu);
		return true;
	}
	
	public void addFragment(View view){
		
		ArticleFragment frag = new ArticleFragment();
		Bundle args = new Bundle();
		EditText fragTextField = (EditText)findViewById(R.id.fragText);
		String fragText = fragTextField.getText().toString();
		args.putString(FRAGMENT_MESSAGE, fragText);
		frag.setArguments(args);
		
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, frag).commit();
	}
	
	

}
