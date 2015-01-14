package com.krishapps.mynotes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuoteListAdapter extends ArrayAdapter<Quote> {
	private ArrayList<Quote> quoteList;

	public QuoteListAdapter(Context context, int textViewResourceId,
			ArrayList<Quote> quotes) {
		super(context, textViewResourceId, quotes);
		this.quoteList = quotes;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_header_list_fragment, null);
		}

		Quote quote = quoteList.get(position);

		TextView author = (TextView) v.findViewById(R.id.author);
		TextView quoteText = (TextView) v.findViewById(R.id.quote);

		//quoteText.setText("alksdfjladsfjlajfldasjfldsjaflsdajflkasdjf;ladsjldkfjasf)");
		quoteText.setText(quote.getQuoteHeader());
		String quoteAuthor = quote.getAuthor();

		if (quoteAuthor != null) {
			author.setText(quote.getAuthor());
		}

		return v;
	}
}
