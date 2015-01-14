package com.example.budget.database;

import android.provider.BaseColumns;

public final class BudgetContract {
	
	public BudgetContract(){}
	
	/***** TABLES *****/
	public static abstract class Transactions implements BaseColumns{
		
		public static final String TABLE_NAME = "transactions";
		public static final String COLUMN_NAME_TRANSACTION_ID = "transactionid";
		public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
		public static final String COLUMN_NAME_CATEGORY_ID= "categoryid";
		public static final String COLUMN_NAME_TRANSACTION_TYPE_ID = "transaction_typeid";
		public static final String COLUMN_NAME_TRANSACTION_REASON = "transaction_reason";
		public static final String COLUMN_NAME_TRANSACTION_AMOUNT = "transaction_amount";
		public static final String COLUMN_NAME_PRETRANSACTION_AMOUNT = "pretransaction_amount";
		public static final String COLUMN_NAME_POSTTRANSACTION_AMOUNT = "posttransaction_amount";
		
	}
	
	public static abstract class Categories implements BaseColumns{
		
		public static final String TABLE_NAME = "categories";
		public static final String COLUMN_NAME_CATEGORY_ID = "categoryid";
		public static final String COLUMN_NAME_CATEGORY_NAME = "categoryname";
		
		
	}
	
	public static abstract class TransactionTypes implements BaseColumns{
	
		public static final String TABLE_NAME = "transaction_type";
		public static final String COLUMN_NAME_TRANSACTION_TYPE_ID = "transaction_typeid";
		public static final String COLUMN_NAME_TRANSACTION_TYPE_NAME = "transaction_type_name";
	
	}
	
}
