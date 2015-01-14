package com.example.budget.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.budget.database.BudgetContract.Categories;
import com.example.budget.database.BudgetContract.TransactionTypes;
import com.example.budget.database.BudgetContract.Transactions;
import com.example.budget.model.Category;
import com.example.budget.model.Transaction;
import com.example.budget.model.TransactionDisplayContainer;
import com.example.budget.model.TransactionType;

public class TransactionsDataSource {
	
	private SQLiteDatabase database;
	private BudgetDatabaseHelper dbHelper;
	private final String LOG_TAG = "TransactionsDataSource";
	private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public TransactionsDataSource(Context context){
		
		dbHelper = new BudgetDatabaseHelper(context);
	}
	
	public void open() throws SQLException{
		
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public void insertDefaultCategories(){
		dbHelper.populateDefaultCategories(database);
	}
	
	public void insertDefaultTransactionTypes(){
		dbHelper.populateDefaultTransactionTypes(database);
	}
	
	public double getBudgetValue(){
		//build query
		String [] projection = {Transactions.COLUMN_NAME_POSTTRANSACTION_AMOUNT};
		
		String sortOrder = Transactions.COLUMN_NAME_TIMESTAMP + " DESC";
		
		String limit = "1";
		
		Cursor c = database.query(
				Transactions.TABLE_NAME, 
				projection, 
				null, 
				null, 
				null, 
				null, 
				sortOrder,
				limit);
		
		if(c.moveToFirst()){
			return c.getDouble(c.getColumnIndexOrThrow(Transactions.COLUMN_NAME_POSTTRANSACTION_AMOUNT));
		}else{
			return 0.0;
		}
		
		
	}
	
	
	public void addSingleTransaction(Transaction t){
		
		double currentBudget = getBudgetValue();
		
		double newBudget = currentBudget + t.getTransactionAmount();
		
		int transactionID = getLatestTransactionId();
		
		ContentValues values = new ContentValues();
		
		values.put(Transactions.COLUMN_NAME_TRANSACTION_ID, transactionID+1);
		values.put(Transactions.COLUMN_NAME_TIMESTAMP, new SimpleDateFormat(DATE_FORMAT).format(new Date()));
		values.put(Transactions.COLUMN_NAME_CATEGORY_ID, t.getCategoryId());
		values.put(Transactions.COLUMN_NAME_TRANSACTION_TYPE_ID, BudgetDatabaseHelper.TRANSACTION_TYPES_CREDIT);
		values.put(Transactions.COLUMN_NAME_TRANSACTION_REASON, t.getTransactionReason());
		values.put(Transactions.COLUMN_NAME_TRANSACTION_AMOUNT, t.getTransactionAmount());
		values.put(Transactions.COLUMN_NAME_PRETRANSACTION_AMOUNT, currentBudget);
		values.put(Transactions.COLUMN_NAME_POSTTRANSACTION_AMOUNT, newBudget);
		
		database.insert(Transactions.TABLE_NAME, null, values);
	}
	
	public String [] getCategoryNames(){
		
		String [] projection = {Categories.COLUMN_NAME_CATEGORY_NAME};
		Cursor c = database.query(
				Categories.TABLE_NAME,
				projection, 
				null, 
				null, 
				null, 
				null, 
				null,
				null);
		
		String [] list = new String[c.getCount()];
		
		if(c.moveToFirst()){
			
			for(int i = 0; i < list.length; i++){
				list[i] = (c.getString(c.getColumnIndex(Categories.COLUMN_NAME_CATEGORY_NAME)));
				c.moveToNext();
				
			}
		}
		
		return list;
	}
	
	public String [] getTransactionTypeNames(){
		
		String [] projection = {TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME};
		Cursor c = database.query(
				TransactionTypes.TABLE_NAME,
				projection, 
				null, 
				null, 
				null, 
				null, 
				null,
				null);
		
		String [] list = new String[c.getCount()];
		
		if(c.moveToFirst()){
			
			for(int i = 0; i < list.length; i++){
				list[i] = (c.getString(c.getColumnIndex(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME)));
				c.moveToNext();
				
			}
		}
	
		return list;
	}
	
    private int getLatestTransactionId(){
    	
    	String [] projection = {Transactions.COLUMN_NAME_TRANSACTION_ID};
    	
    	String sortOrder = Transactions.COLUMN_NAME_TRANSACTION_ID + " DESC";
    	String limit = "1";
    	
    	Cursor c = database.query(
    			Transactions.TABLE_NAME, 
    			projection, 
    			null, 
    			null, 
    			null, 
    			null,
    			sortOrder,
    			limit);
    	
    	if(c.moveToFirst()){
    	
    		return c.getInt(c.getColumnIndexOrThrow(Transactions.COLUMN_NAME_TRANSACTION_ID));
    	
    	}else{
    		return 0;
    	}
    	
    }

	public ArrayList<TransactionDisplayContainer> getTransactionHistory() {
		// TODO Auto-generated method stub
		ArrayList<TransactionDisplayContainer> history = new ArrayList<TransactionDisplayContainer>();
		
		Log.v(LOG_TAG, "History query = [" + BudgetDatabaseHelper.SQL_SELECT_TRANSACTION_HISTORY +"]");
		Cursor cursor = database.rawQuery(BudgetDatabaseHelper.SQL_SELECT_TRANSACTION_HISTORY, null);
		
		Log.v(LOG_TAG, "Cursor count is: " + cursor.getCount());
		if(cursor.moveToFirst()){
			
			int transactionIdColIndex = cursor.getColumnIndexOrThrow(Transactions.COLUMN_NAME_TRANSACTION_ID);
			int transactionReasonColIndex = cursor.getColumnIndexOrThrow(Transactions.COLUMN_NAME_TRANSACTION_REASON);
			int transactionTimeColIndex = cursor.getColumnIndexOrThrow(Transactions.COLUMN_NAME_TIMESTAMP);
			int categoryIdColIndex = cursor.getColumnIndexOrThrow(Categories.COLUMN_NAME_CATEGORY_ID);
			int categoryNameColIndex = cursor.getColumnIndexOrThrow(Categories.COLUMN_NAME_CATEGORY_NAME);
			int transactionTypeIdColIndex = cursor.getColumnIndexOrThrow(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID);
			int transactionTypeNameColIndex = cursor.getColumnIndexOrThrow(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME);
			
			while(!cursor.isAfterLast()){
				
				//get transactions
				Transaction transaction = new Transaction();
				transaction.setTransactionId(cursor.getInt(transactionIdColIndex));
				transaction.setTransactionReason(cursor.getString(transactionReasonColIndex));
				try{
					transaction.setTimestamp(new SimpleDateFormat(DATE_FORMAT).parse(cursor.getString(transactionTimeColIndex)));
				}catch(ParseException error){
					Log.e(LOG_TAG, error.toString());
				}
				
				//get category
				Category category = new Category();
				category.setCategoryId(cursor.getInt(categoryIdColIndex));
				category.setCategoryName(cursor.getString(categoryNameColIndex));
				
				//get transaction types
				TransactionType transType = new TransactionType();
				transType.setTransactionTypeId(cursor.getInt(transactionTypeIdColIndex));
				transType.setTransactionTypeName(cursor.getString(transactionTypeNameColIndex));
				
				history.add(new TransactionDisplayContainer(transaction, category, transType));
				
				cursor.moveToNext();
				
			}
		}
		
		return history;
	}

}
