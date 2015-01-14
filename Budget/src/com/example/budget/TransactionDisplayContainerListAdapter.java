package com.example.budget;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.budget.model.TransactionDisplayContainer;

public class TransactionDisplayContainerListAdapter extends
		ArrayAdapter<TransactionDisplayContainer> {

	private Context context;
	private ArrayList<TransactionDisplayContainer> transactions;
	
	public TransactionDisplayContainerListAdapter(Context context, ArrayList<TransactionDisplayContainer> trans){
		super(context, R.layout.transaction_display_layout, trans);
		this.context = context;
		transactions = trans;
	}
	
	public View getView(int position, View row, ViewGroup parent){
		
		if(row == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.transaction_display_layout, null);
		}
		
		TransactionDisplayContainer transaction = transactions.get(position);
		
		if(transaction != null){
			
			TextView reason = (TextView)row.findViewById(R.id.trans_display_layout_reason);
			TextView amount = (TextView)row.findViewById(R.id.trans_display_layout_amount);
			TextView category = (TextView)row.findViewById(R.id.trans_display_layout_category);
			TextView date = (TextView)row.findViewById(R.id.trans_display_layout_date);
			
			if(reason != null){
				reason.setText(transaction.getTransaction().getTransactionReason());
			}
			
			if(amount != null){
				
				amount.setText("" + transaction.getTransaction().getTransactionAmount());
			}
			
			if(category != null){
				category.setText(transaction.getCategory().getCategoryName());
			}
			
			if(date != null){
				date.setText(transaction.getTransaction().getTimestamp().toString());
			}
		}
		
		return row;
	}

	public void updateTransactionList(ArrayList<TransactionDisplayContainer> transactionHistory) {
		// TODO Auto-generated method stub
		if(transactionHistory != null){
			
			transactions = transactionHistory;
			
		}
	}
}
