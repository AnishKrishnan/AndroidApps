package com.krishapps.mynotes;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends FragmentActivity implements
		HeaderListFragment.OnHeaderSelectedListener, DeleteQuoteFragment.VerificationDialogListener, ViewQuoteFragment.ViewQuoteButtonPressed {

	public final static String QUOTE_AUTHOR = "com.krishapps.mynotes.AUTHOR";
	public final static String QUOTE_TEXT = "com.krishapps.mynotes.TEXT";
	public final static String QUOTE_YEAR = "com.krishapps.mynotes.YEAR";
	public final static String QUOTE_NEW_QUOTE = "com.krishapps.mynotes.NEW_QUOTE";
	public final static String QUOTE_LIST_FILE_DIR = "com.krishapps.mynotes.LIST_FILE_DIR";

	HeaderListFragment listFragment;

	int selectedPosition, longClickPosition, chosenPos, posToDelete;

	Quote longClickQuote;
    Quote chosenQuote;
	private final static int EDIT_WINDOW_LAUNCHED = 0;
	private final static int NEW_WINDOW_LAUNCHED = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.frameContainer) != null) {

			listFragment = new HeaderListFragment();

			listFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
					.add(R.id.frameContainer, listFragment).commit();

		}

		registerForContextMenu(findViewById(R.id.frameContainer));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_new:
			launchNewWindow();
			break;
		}

		return true;
	}

	public void onQuoteSelected(Quote q, int position) {
		// DO something here later.
		

		//launchEditWindow(chosenQuote, position);
		this.chosenQuote = q;
		chosenPos = position;
		ViewQuoteFragment viewFragment = ViewQuoteFragment.newInstance(chosenQuote);
		
		viewFragment.show(getSupportFragmentManager(), "ViewQuoteFragment");
		// HeaderListFragment newList = new HeaderListFragment();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {

		case (Activity.RESULT_OK):

				// String author = "";
				// int year = 0;
				String author = data.getStringExtra(QUOTE_AUTHOR);
				int year = data.getIntExtra(QUOTE_YEAR, 0);
				String quoteText = data.getStringExtra(QUOTE_TEXT);

				Quote q = new Quote(quoteText);

				if (!author.equals("")) {
					q.setAuthor(author);
				}

				if (year != 0) {
					q.setYear(year);
				}
				listFragment.addQuote(q, selectedPosition);
				listFragment.saveQuotes();
			}

	}

	@Override
	public void onQuoteLongClick(Quote chosenQuote, int position) {
		// TODO Auto-generated method stub

		openContextMenu(findViewById(R.id.frameContainer));
		longClickPosition = position;
		longClickQuote = chosenQuote;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.list_context_menu, menu);

	}

	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.context_menu_edit:
			launchEditWindow(longClickQuote, longClickPosition);
			return true;
		case R.id.context_menu_delete:
			posToDelete = longClickPosition;
			launchDeleteWindow();
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	private void launchEditWindow(Quote chosenQuote, int position) {
		selectedPosition = position;
		Intent intent = new Intent(this, QuoteView.class);
		intent.putExtra(QUOTE_AUTHOR, chosenQuote.getAuthor());
		intent.putExtra(QUOTE_TEXT, chosenQuote.getQuote());
		intent.putExtra(QUOTE_YEAR, chosenQuote.getYear());
		intent.putExtra(QUOTE_NEW_QUOTE, false);

		startActivityForResult(intent, EDIT_WINDOW_LAUNCHED);
	}
	
	private void launchNewWindow(){
		selectedPosition = -1;
		Intent intent = new Intent(this, QuoteView.class);
		intent.putExtra(QUOTE_NEW_QUOTE, true);
		startActivityForResult(intent, NEW_WINDOW_LAUNCHED);
	}
	
	private void launchDeleteWindow(){
		posToDelete = chosenPos;
		DialogFragment deleteFragment = new DeleteQuoteFragment();
		deleteFragment.show(getSupportFragmentManager(), "DeleteQuoteFragment");
	}

	@Override
	public void deleteButtonPressed() {
		// TODO Auto-generated method stub
		listFragment.deleteQuote(posToDelete);
		listFragment.saveQuotes();
	}

	@Override
	public void cancelButtonPressed() {
		// TODO Auto-generated method stub
		return;
	}
	
	public void viewQuoteFragmentEditPressed(){
		
		launchEditWindow(chosenQuote, chosenPos);
	}
	public void viewQuoteFragmentDeletePressed(){
		launchDeleteWindow();
	}
	
	

}
