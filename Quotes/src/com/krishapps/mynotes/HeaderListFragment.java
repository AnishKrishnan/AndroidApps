package com.krishapps.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import com.krishapps.mynotes.*;


public class HeaderListFragment extends ListFragment implements OnItemLongClickListener{
	private QuoteList quoteList;
	
	OnHeaderSelectedListener callBackListener;
	public interface OnHeaderSelectedListener{
		public void onQuoteSelected(Quote chosenQuote, int position);
		public void onQuoteLongClick(Quote chosenQuote, int position);
	}
	
	public enum QuoteParamsUsed{
		AUTHOR_USED,
		YEAR_USED
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		quoteList = new QuoteList(getActivity(),10);
		
		
		setListAdapter(new QuoteListAdapter(getActivity(), R.layout.activity_header_list_fragment, quoteList.getQuotesList()));
		ArrayAdapter adapter = (ArrayAdapter)(getListAdapter());
		adapter.setNotifyOnChange(true);
		
	}
	
	public void onStart(){
		super.onStart();
		
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getActivity().registerForContextMenu(getListView());
		getListView().setOnItemLongClickListener(this);
	}
	 
	

	

	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try{
			callBackListener = (OnHeaderSelectedListener)activity;
			
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString() + "must implement OnHeaderSelectedListener");
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id){
		Quote q = quoteList.getQuote(position);
		callBackListener.onQuoteSelected(q, position);
	}
	
	public void addQuote(Quote q, int position){
		if(position != -1){
			quoteList.setQuote(position, q);
		}else{
			quoteList.setQuote(q);
		}
		
		ArrayAdapter adapter = (ArrayAdapter)(getListAdapter());
		adapter.notifyDataSetChanged();
	}
	
	public int getListSize(){
		return quoteList.getQuotesList().size();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
		// TODO Auto-generated method stub
		callBackListener.onQuoteLongClick(quoteList.getQuote(position), position);
		return true;
	}
	
	public void deleteQuote(int position){
		quoteList.deleteQuote(position);
		ArrayAdapter adapter = (ArrayAdapter)(getListAdapter());
		adapter.notifyDataSetChanged();
	}
	
	public void saveQuotes(){
		quoteList.saveList();
	}
}
