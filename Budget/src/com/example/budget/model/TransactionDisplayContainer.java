package com.example.budget.model;

public class TransactionDisplayContainer {

	private Transaction transaction;
	private Category category;
	private TransactionType transType;
	
	public TransactionDisplayContainer(Transaction transaction, Category cat, TransactionType transType){
		
		this.transaction = transaction;
		category = cat;
		this.transType = transType;
	}
	
	public Transaction getTransaction(){
		return transaction;
	}
	
	public Category getCategory(){
		return category;
	}
	
	public TransactionType getTransactionType(){
		return transType;
	}
}
