package com.example.budget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.budget.database.TransactionsDataSource;
import com.example.budget.model.TransactionDisplayContainer;

public class MainActivity extends Activity implements OnClickListener, OnTabChangeListener  {
	
	private TransactionsDataSource dataSource;
	private ArrayList<TransactionDisplayContainer> transactionHistory;
	private TransactionDisplayContainerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpTabs();
		dataSource = new TransactionsDataSource(getApplicationContext());
		//TODO: move to Async task
		dataSource.open();
		
	//	dataSource.addCredit(100.0, BudgetDatabaseHelper.CATEGORIES_HOUSEHOLD, "Just Cause");
		setBudgetValue();
		
		Button addExpenseText = (Button)findViewById(R.id.addExpense);
		addExpenseText.setOnClickListener(this);
		
		Button addCreditText = (Button)findViewById(R.id.addCredit);
		addCreditText.setOnClickListener(this);
		
		transactionHistory = new ArrayList<TransactionDisplayContainer>();
		ListView history = (ListView)findViewById(R.id.historyListView);
		adapter = new TransactionDisplayContainerListAdapter(this, transactionHistory);
		history.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	protected void onResume(){
		super.onResume();
		dataSource.open();
		setBudgetValue();
		
	}
	
	protected void onPause(){
		dataSource.close();
		super.onPause();
	}
	
	private void setUpTabs(){
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		
		//TODO: Replace text with icons
		TabSpec spec1 = tabHost.newTabSpec("Action");
		spec1.setContent(R.id.action);
		spec1.setIndicator("Action");
		
		TabSpec spec2 = tabHost.newTabSpec(getResources().getString(R.string.history_tab_id));
		spec2.setContent(R.id.history);
		spec2.setIndicator("History");
		
		TabSpec spec3 = tabHost.newTabSpec("Favourites");
		spec3.setContent(R.id.favourites);
		spec3.setIndicator("Favourites");
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		
		tabHost.setOnTabChangedListener(this);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, TransactionView.class);
		intent.putExtra(TransactionView.TRANSACTION_VIEW_TYPE, TransactionView.NEW_TRANSACTION);
		
		
		switch(view.getId()){
		
		case R.id.addExpense:
			intent.putExtra(TransactionView.TRANSACTION_TYPE, TransactionView.NEW_EXPENSE_TRANSACTION);
			
			break;
			
		case R.id.addCredit:
			intent.putExtra(TransactionView.TRANSACTION_TYPE, TransactionView.NEW_CREDIT_TRANSACTION);
			break;
		
		}
		
		startActivity(intent);
		
	}
	
	private void setBudgetValue(){
		double budgetValue = dataSource.getBudgetValue();
	
		
		TextView budgetValueText = (TextView)findViewById(R.id.remaining_budget);
		budgetValueText.setText(""+budgetValue);

	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
		if(tabId.equals(getResources().getString(R.string.history_tab_id))){
				
			transactionHistory = dataSource.getTransactionHistory();
			Log.v("", "transaction history size is: " +transactionHistory.size());
			adapter.updateTransactionList(transactionHistory);
			adapter.notifyDataSetChanged();
		}
		
	}
	
	

}
