package com.example.budget.model;

import java.util.Date;

public class Transaction {
	
	private int transactionId;
	private Date timestamp;
	private int categoryId;
	private int transactionTypeId;
	private String transactionReason;
	private double transactionAmount;
	private double preTransactionAmount;
	private double postTransactionAmount;
	
	public Transaction(){
		
	}
	
	public Transaction(double amount){
		
		this.transactionAmount = amount;
	}
	
	public Transaction( int categoryId, int transactionTypeId, double amount){
		
		this.transactionAmount = amount;
		this.categoryId = categoryId;
		this.transactionTypeId = transactionTypeId;
	}
	
	public Transaction(int categoryId, int transactionTypeId, double amount, String transactionReason){
		
		this.transactionAmount = amount;
		this.categoryId = categoryId;
		this.transactionTypeId = transactionTypeId;
		this.transactionReason = transactionReason;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public int getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(int trasactionTypeId) {
		this.transactionTypeId = trasactionTypeId;
	}
	
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getTransactionReason() {
		return transactionReason;
	}
	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}
	
	public double getPreTransactionAmount() {
		return preTransactionAmount;
	}
	public void setPreTransactionAmount(double preTransactionAmount) {
		this.preTransactionAmount = preTransactionAmount;
	}
	
	public double getPostTransactionAmount() {
		return postTransactionAmount;
	}
	public void setPostTransactionAmount(double postTransactionAmount) {
		this.postTransactionAmount = postTransactionAmount;
	}
	
	

}
