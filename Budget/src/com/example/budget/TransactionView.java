package com.example.budget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.budget.database.BudgetDatabaseHelper;
import com.example.budget.database.TransactionsDataSource;
import com.example.budget.model.Transaction;

public class TransactionView extends Activity implements OnClickListener {
	
	public static final String TRANSACTION_VIEW_TYPE = "TRANSACTION_VIEW_TYPE";
	public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
	
	public static final String NEW_TRANSACTION = "NEW_TRANSACTION";
	public static final String EXISTING_TRANSACTION = "EXISTING_TRANSACTION";
	
	public static final String NEW_EXPENSE_TRANSACTION = "NEW EXPENSE TRANSACTION";
	public static final String NEW_CREDIT_TRANSACTION = "NEW CREDIT TRANSACTION";
	public static final String NEW_RECURRING_EXPENSE_TRANSACTION = "NEW RECURRING EXPENSE TRANSACTION ";
	public static final String NEW_RECURRING_CREDIT_TRANSACTION = "NEW RECURRING CREDIT TRANSACTION";
	
	private TransactionsDataSource dataSource;
	private String transactionViewType, transactionType;
	private Bundle extras;
	private Transaction transaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transactionview);
		
		
		dataSource = new TransactionsDataSource(getApplicationContext());
		dataSource.open();
		
		extras = getIntent().getExtras();
		
		transactionViewType = extras.getString(TRANSACTION_VIEW_TYPE);
		transactionType = extras.getString(TRANSACTION_TYPE);
		
		setTitle(transactionViewType);
		
		loadCategorySpinnerValues();
		loadTransactionTypeSpinnerValues();
		
		disableTransactionTypeSpinner();
		
		enableButtons();
		

		
	}
	
	private void loadCategorySpinnerValues(){
		
		Spinner categorySpinner = (Spinner)findViewById(R.id.transaction_view_category_spinner);
		
		String [] categoryNames = dataSource.getCategoryNames();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(dataAdapter);
	}
	
	private void loadTransactionTypeSpinnerValues(){
		
		Spinner transactionTypeSpinner = (Spinner)findViewById(R.id.transaction_view_transaction_type_spinner);
		
		String [] transactionTypeNames = dataSource.getTransactionTypeNames();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transactionTypeNames);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		transactionTypeSpinner.setAdapter(dataAdapter);
	}
	
	private void disableTransactionTypeSpinner(){
		Spinner transactionTypeSpinner = (Spinner)findViewById(R.id.transaction_view_transaction_type_spinner);

		if(transactionType.equals(NEW_EXPENSE_TRANSACTION)){
			
			transactionTypeSpinner.setSelection(BudgetDatabaseHelper.TRANSACTION_TYPES_EXPENSE-1);
			
		}else if(transactionType.equals(NEW_CREDIT_TRANSACTION)){
			
			transactionTypeSpinner.setSelection(BudgetDatabaseHelper.TRANSACTION_TYPES_CREDIT-1);
			
		}else if(transactionType.equals(NEW_RECURRING_EXPENSE_TRANSACTION)){
			
			transactionTypeSpinner.setSelection(BudgetDatabaseHelper.TRANSACTION_TYPES_RECURRING_EXPENSE-1);
		
		}else if(transactionType.equals(BudgetDatabaseHelper.TRANSACTION_TYPES_RECURRING_CREDIT)){
			
			transactionTypeSpinner.setSelection(BudgetDatabaseHelper.TRANSACTION_TYPES_RECURRING_CREDIT-1);
		}
		transactionTypeSpinner.setEnabled(false);
		
	}
	
	private void enableButtons(){
		
		Button okButton = (Button)findViewById(R.id.transaction_view_ok_button);
		okButton.setOnClickListener(this);
		
		Button cancelButton = (Button)findViewById(R.id.transaction_view_cancel_button);
		cancelButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		
		case R.id.transaction_view_cancel_button:
			finish();
			break;
			
		case R.id.transaction_view_ok_button:
			if(transactionViewType.equals(NEW_TRANSACTION)){
				
				int categoryId = ((Spinner)findViewById(R.id.transaction_view_category_spinner)).getSelectedItemPosition();
				int transactionTypeId = ((Spinner)findViewById(R.id.transaction_view_transaction_type_spinner)).getSelectedItemPosition();
				
				double amount = Double.parseDouble(((EditText)findViewById(R.id.transaction_view_amount)).getText().toString());
				
				if(transactionType.equals(NEW_EXPENSE_TRANSACTION) || transactionType.equals(NEW_RECURRING_EXPENSE_TRANSACTION)){
					amount *= -1;
				}
				String transactionReason = ((EditText)findViewById(R.id.transaction_view_reason_text)).getText().toString();
				
				transaction = new Transaction(categoryId, transactionTypeId, amount, transactionReason);
				
				dataSource.addSingleTransaction(transaction);
				finish();
			}
			
			break;
		}
		
	}
	


}
