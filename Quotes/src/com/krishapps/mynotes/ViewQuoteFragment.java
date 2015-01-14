package com.krishapps.mynotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewQuoteFragment extends DialogFragment {
	
	private final static String VIEW_QUOTE_SEPERATOR = "--";
	
	public interface ViewQuoteButtonPressed{
		public void viewQuoteFragmentEditPressed();
		public void viewQuoteFragmentDeletePressed();
	}
	
	ViewQuoteButtonPressed buttonPressedListener;
	
	
	static ViewQuoteFragment newInstance(Quote q){
		ViewQuoteFragment view = new ViewQuoteFragment();
		
		Bundle args = new Bundle();
		args.putString(MainActivity.QUOTE_AUTHOR, q.getAuthor());
		args.putString(MainActivity.QUOTE_TEXT, q.getQuote());
		args.putInt(MainActivity.QUOTE_YEAR, q.getYear());
		
		view.setArguments(args);
		
		return view;
	}
	
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			buttonPressedListener = (ViewQuoteButtonPressed) activity;
			
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString());
		}
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		int style = DialogFragment.STYLE_NO_TITLE;
		int theme = android.R.style.Theme_Holo_Dialog;
		
		setStyle(style,theme);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View v = inflater.inflate(R.layout.view_quote_no_edit, container, false);
		TextView textField = (TextView)v.findViewById(R.id.view_quote_text_field);
		
		Quote q = unbundleQuote(savedInstanceState);
		
		
		textField.setText(q.getQuote());
		
		TextView authorField = (TextView)v.findViewById(R.id.view_quote_author_field);
		authorField.setText(formatAuthorText(q));
		
		Button editButton = (Button)v.findViewById(R.id.viewQuoteEditButon);
		editButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // When button is clicked, call up to owning activity.
                buttonPressedListener.viewQuoteFragmentEditPressed();
                dismiss();
            }
        });
		
		Button deleteButton = (Button)v.findViewById(R.id.viewQuoteDeleteButton);
		deleteButton.setOnClickListener(new OnClickListener(){
			public void onClick(View view2){
				buttonPressedListener.viewQuoteFragmentDeletePressed();
				dismiss();
			}
		});
		return v;
	}
	
	private Quote unbundleQuote(Bundle savedInstanceState){
		
		String author = getArguments().getString(MainActivity.QUOTE_AUTHOR);
		String quote = getArguments().getString(MainActivity.QUOTE_TEXT);
		int year = getArguments().getInt(MainActivity.QUOTE_YEAR);
		
		Quote q = new Quote(quote, author, year);
		return q;
	}
	
	private String formatAuthorText(Quote q){
		String formattedText = "";
		
		if(q.getAuthor() != null){
			formattedText += q.getAuthor();
		}
		
		if(q.getYear() != 0){
			formattedText += ", " + q.getYear();
		}
		return formattedText;
	}
}
