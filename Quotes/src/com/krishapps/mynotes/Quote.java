package com.krishapps.mynotes;

public class Quote {
	private String author = "Anonymous"; 
	private int year = 0;
	private String quote;
	private String quoteHeader;
	
	private final int HEADER_LENGTH = 20;
	public Quote(String quote){
		this.quote = quote;
		generateHeader();
	}
	
	public Quote(String quote, String author){
		this.quote = quote;
		this.author = author;
		generateHeader();
	}
	
	public Quote(String quote, String author, int year){
		this.quote = quote;
		this.author = author;
		this.year = year;
		generateHeader();
	}
	
	public String getQuote(){
		return quote;
	}
	
	public String getQuoteHeader(){
		return quoteHeader;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	private void generateHeader(){
		int quoteLength = quote.length();
		quoteHeader = quote.substring(0,Math.min(HEADER_LENGTH, quoteLength)) + "...";
	}

}
