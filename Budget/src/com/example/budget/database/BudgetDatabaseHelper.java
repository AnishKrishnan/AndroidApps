package com.example.budget.database;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import com.example.budget.database.BudgetContract.Categories;
import com.example.budget.database.BudgetContract.TransactionTypes;
import com.example.budget.database.BudgetContract.Transactions;

public class BudgetDatabaseHelper extends SQLiteOpenHelper {
	
	
	public enum TRANSACTION_TYPES{
		Expense,
		Recurring_Expense,
		Credit,
		Recurring_Credit
	}
	
	public static final int CATEGORIES_HOUSEHOLD = 0;
	public static final int CATEGORIES_FOOD = 1;
	public static final int CATEGORIES_ENTERTAINMENT = 2;
	public static final int CATEGORIES_UNFORSEEN = 3;
	public static final int CATEGORIES_ONEOFF= 4;
	
	public static final int TRANSACTION_TYPES_EXPENSE = 0;
	
	public static final int TRANSACTION_TYPES_CREDIT = 1;
	public static final int TRANSACTION_TYPES_RECURRING_EXPENSE = 2;
	
	public static final int TRANSACTION_TYPES_RECURRING_CREDIT = 3;

	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "Budget.db";
	

	/***** QUERY VARIABLES *****/
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String DECIMAL_TYPE = " DECIMAL";
	
	private static final String COMMA_SEP = ",";

	private static final String FOREIGN_KEY = "FOREIGN KEY ";
	private static final String REFERENCES = " REFERENCES ";
	
	/***** QUERIES -- CREATE*****/
	
	private static final String SQL_CREATE_TRANSACTIONS=
			"CREATE TABLE " + Transactions.TABLE_NAME + "( " +
					  Transactions.COLUMN_NAME_TRANSACTION_ID + INT_TYPE + " PRIMARY KEY" +  COMMA_SEP +
					  Transactions.COLUMN_NAME_TIMESTAMP + " DATETIME" + COMMA_SEP +
					  Transactions.COLUMN_NAME_CATEGORY_ID + INT_TYPE + COMMA_SEP +
					  Transactions.COLUMN_NAME_TRANSACTION_TYPE_ID + INT_TYPE + COMMA_SEP +
					  Transactions.COLUMN_NAME_TRANSACTION_REASON + TEXT_TYPE + COMMA_SEP +
					  Transactions.COLUMN_NAME_TRANSACTION_AMOUNT +  DECIMAL_TYPE + COMMA_SEP +
					  Transactions.COLUMN_NAME_PRETRANSACTION_AMOUNT + 	DECIMAL_TYPE + COMMA_SEP +
					  Transactions.COLUMN_NAME_POSTTRANSACTION_AMOUNT + 	DECIMAL_TYPE + COMMA_SEP +
					  FOREIGN_KEY +"(" + Transactions.COLUMN_NAME_CATEGORY_ID +")" + REFERENCES + Categories.TABLE_NAME + "(" + Categories.COLUMN_NAME_CATEGORY_ID + ")" + COMMA_SEP +
					  FOREIGN_KEY +"(" + Transactions.COLUMN_NAME_TRANSACTION_TYPE_ID +")" + REFERENCES + TransactionTypes.TABLE_NAME + "(" + TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID + ")" + 
					  " )";
	
	private static final String SQL_CREATE_CATEGORIES =
			"CREATE TABLE " + Categories.TABLE_NAME + "( " + 
					Categories.COLUMN_NAME_CATEGORY_ID + INT_TYPE + " PRIMARY KEY"  + COMMA_SEP +
					Categories.COLUMN_NAME_CATEGORY_NAME + TEXT_TYPE +
					")";
	
	private static final String SQL_CREATE_TRANSACTION_TYPE =
			"CREATE TABLE " + TransactionTypes.TABLE_NAME + "( " + 
					TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP +
					TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME + TEXT_TYPE +
					")";
	
	/***** QUERIES -- DELETE *****/
	
	private static final String SQL_DELETE_TRANSACTIONS =
			"DROP TABLE IF EXISTS " + Transactions.TABLE_NAME;
	
	private static final String SQL_DELETE_CATEGORIES =
			"DROP TABLE IF EXISTS " + Categories.TABLE_NAME;
	
	private static final String SQL_DELETE_TRANSACTION_TYPE=
			"DROP TABLE IF EXISTS " + TransactionTypes.TABLE_NAME;
	
	/***** QUERIES -- SELECT *****/
	public static final String SQL_SELECT_TRANSACTION_HISTORY =
			"SELECT " + Transactions.TABLE_NAME + "." + Transactions.COLUMN_NAME_TRANSACTION_ID + COMMA_SEP +
				Transactions.TABLE_NAME + "." + Transactions.COLUMN_NAME_TRANSACTION_REASON + COMMA_SEP +
				Transactions.TABLE_NAME + "." + Transactions.COLUMN_NAME_TIMESTAMP + COMMA_SEP +
				Categories.TABLE_NAME + "." + Categories.COLUMN_NAME_CATEGORY_ID + COMMA_SEP +
				Categories.TABLE_NAME + "." + Categories.COLUMN_NAME_CATEGORY_NAME + COMMA_SEP +
				TransactionTypes.TABLE_NAME + "." + TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID + COMMA_SEP +
				TransactionTypes.TABLE_NAME + "." + TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME +
				
			" FROM " + Transactions.TABLE_NAME + COMMA_SEP +
				Categories.TABLE_NAME + COMMA_SEP +
				TransactionTypes.TABLE_NAME +
				
			" WHERE " + Transactions.TABLE_NAME + "." + Transactions.COLUMN_NAME_CATEGORY_ID + "=" + Categories.TABLE_NAME + "." + Categories.COLUMN_NAME_CATEGORY_ID +
				" AND " + Transactions.TABLE_NAME + "." + Transactions.COLUMN_NAME_TRANSACTION_TYPE_ID + "=" + TransactionTypes.TABLE_NAME + "." + TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID;
	
	public static final String SQL_SELECT_TRANSACTION_TEST = "SELECT * FROM " + Transactions.TABLE_NAME;
	
	private SQLiteDatabase db;
	
	public BudgetDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	
	}
			

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create tables
		db.execSQL(SQL_CREATE_CATEGORIES);
		db.execSQL(SQL_CREATE_TRANSACTION_TYPE);
		db.execSQL(SQL_CREATE_TRANSACTIONS);
		
		//populate categories and transaction type
		/*
		db.execSQL(SQL_INSERT_DEFAULT_CATEGORIES);
		db.execSQL(SQL_INSERT_DEFAULT_TRANSACTION_TYPES);
		*/
		
		populateDefaultCategories(db);
		populateDefaultTransactionTypes(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//delete old tables
		db.execSQL(SQL_DELETE_TRANSACTIONS);
		db.execSQL(SQL_DELETE_CATEGORIES);
		db.execSQL(SQL_DELETE_TRANSACTION_TYPE);
		
		//recreate tables
		onCreate(db);

	}
	
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    public void populateDefaultCategories(SQLiteDatabase db){
    	
    	ContentValues values = new ContentValues();
    	
    	values.put(Categories.COLUMN_NAME_CATEGORY_ID, CATEGORIES_HOUSEHOLD);
    	values.put(Categories.COLUMN_NAME_CATEGORY_NAME, "Household");
    	db.insert(Categories.TABLE_NAME, null, values);
    	
    	values.put(Categories.COLUMN_NAME_CATEGORY_ID, CATEGORIES_FOOD);
    	values.put(Categories.COLUMN_NAME_CATEGORY_NAME, "Food");
    	db.insert(Categories.TABLE_NAME, null, values);
    	
    	values.put(Categories.COLUMN_NAME_CATEGORY_ID, CATEGORIES_ENTERTAINMENT);
    	values.put(Categories.COLUMN_NAME_CATEGORY_NAME, "Entertainment");
    	db.insert(Categories.TABLE_NAME, null, values);
    	
    	values.put(Categories.COLUMN_NAME_CATEGORY_ID, CATEGORIES_UNFORSEEN);
    	values.put(Categories.COLUMN_NAME_CATEGORY_NAME, "Unforseen");
    	db.insert(Categories.TABLE_NAME, null, values);
    	
    	values.put(Categories.COLUMN_NAME_CATEGORY_ID, CATEGORIES_ONEOFF);
    	values.put(Categories.COLUMN_NAME_CATEGORY_NAME, "One off");
    	db.insert(Categories.TABLE_NAME, null, values);
    	
    }
    
    public void populateDefaultTransactionTypes(SQLiteDatabase db){
    	ContentValues values = new ContentValues();
    	
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID, TRANSACTION_TYPES_EXPENSE);
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME, "Expense");
    	db.insert(TransactionTypes.TABLE_NAME, null, values);
    	
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID, TRANSACTION_TYPES_RECURRING_EXPENSE);
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME, "Recurring Expense");
    	db.insert(TransactionTypes.TABLE_NAME, null, values);
    	
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID, TRANSACTION_TYPES_CREDIT);
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME, "Credit");
    	db.insert(TransactionTypes.TABLE_NAME, null, values);
    	
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_ID, TRANSACTION_TYPES_RECURRING_CREDIT);
    	values.put(TransactionTypes.COLUMN_NAME_TRANSACTION_TYPE_NAME, "Recurring Credit");
    	db.insert(TransactionTypes.TABLE_NAME, null, values);
    	
    }
    

    



}
