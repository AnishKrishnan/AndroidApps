package com.krishapps.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class QuoteView extends Activity {
	Quote quote;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quote_view);
		boolean newQuote = getIntent().getBooleanExtra(MainActivity.QUOTE_NEW_QUOTE, true);
		
		if(!newQuote){
			String author = getIntent().getStringExtra(MainActivity.QUOTE_AUTHOR);
			if(author != null){
				EditText authorField = (EditText)findViewById(R.id.quoteView_Author);
				authorField.setText(author);
			}

			
			String quoteText = getIntent().getStringExtra(MainActivity.QUOTE_TEXT);
			
			EditText quoteField = (EditText)findViewById(R.id.quoteView_Quote);
			quoteField.setText(quoteText);
			
			int year = getIntent().getIntExtra(MainActivity.QUOTE_YEAR, 0);
			if(year != 0){
				EditText yearField = (EditText)findViewById(R.id.quoteView_Year);
				if(yearField != null){
					yearField.setText(Integer.toString(year));
				}
				
			}
		}

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
		MenuItem cancelItem = menu.findItem(R.id.menu_cancel);
		cancelItem.setVisible(true);
		cancelItem.setEnabled(true);
		
		MenuItem saveItem =	menu.findItem(R.id.menu_save);
		saveItem.setVisible(true);
		saveItem.setEnabled(true);
		
		MenuItem newItem = menu.findItem(R.id.menu_new);
		newItem.setEnabled(false);
		newItem.setVisible(false);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.menu_new:

			break;
			
		case R.id.menu_save:
			saveButtonPressed();
			break;
			
		case R.id.menu_cancel:
			cancelButtonPressed();
			break;
		}

		
		return true;
	}
	
	public void saveButtonPressed(){
		
		EditText quoteText = (EditText)findViewById(R.id.quoteView_Quote);
		
		if(!quoteText.getText().toString().equals("")){
			Intent intent = new Intent();
			EditText author = (EditText)findViewById(R.id.quoteView_Author);
			
			intent.putExtra(MainActivity.QUOTE_AUTHOR,author.getText().toString());
			
			EditText year = (EditText)findViewById(R.id.quoteView_Year);
			String yearText = year.getText().toString();
			if(!yearText.equals("")){
				intent.putExtra(MainActivity.QUOTE_YEAR,Integer.parseInt(year.getText().toString()));
			}else{
				intent.putExtra(MainActivity.QUOTE_YEAR,0);
			}
			
			
			
			intent.putExtra(MainActivity.QUOTE_TEXT, quoteText.getText().toString());
			setResult(Activity.RESULT_OK,intent);
			finish();
		}else{
			cancelButtonPressed();
		}

		
	}
	
	
	public void cancelButtonPressed(){
		Intent intent = new Intent();
		setResult(Activity.RESULT_CANCELED,intent);
		finish();
	}

}
