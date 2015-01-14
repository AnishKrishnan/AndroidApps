package com.example.budget.model;

public class Category {
	
	private int categoryId;
	private String categoryName;
	
	public Category(){
	}
	
	public Category(String name){
		
		categoryName = name;
	}
	
	public int getCategoryId(){
		return categoryId;
	}
	
	public String getCategoryName(){
		return categoryName;
	}
	
	public void setCategoryId(int id){
		
		categoryId = id;
	}
	
	public void setCategoryName(String name){
		
		categoryName = name;
	}

}
