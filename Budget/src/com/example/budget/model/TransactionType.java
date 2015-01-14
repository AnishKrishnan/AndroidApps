package com.example.budget.model;

public class TransactionType {

	private int transactionTypeId;
	private String transactionTypeName;
	
	public TransactionType(){
		
	}
	
	public TransactionType(String name){
		transactionTypeName = name;
	}
	
	public int getTransactionTypeId(){
		return transactionTypeId;
	}
	
	public String getTransactionTypeName(){
		return transactionTypeName;
	}
	
	public void setTransactionTypeId(int id){
		transactionTypeId = id;
	}
	
	public void setTransactionTypeName(String name){
		transactionTypeName = name;
	}
}
