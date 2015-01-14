package com.example.csmobile;

public class Courses {
	
	private String codeField, semesterField, titleField;
	
	public Courses(String codeField, String semesterField, String titleField){
		
		this.codeField = codeField;
		this.semesterField = semesterField;
		this.titleField = titleField;
	}
	
	public void setCodeField(String codeField){
		this.codeField = codeField;
	}
	
	public void setSemesterField(String semesterField){
		
		this.semesterField = semesterField;
	}
	
	public void setTitleField(String titleField){
		this.titleField = titleField;
	}
	
	public String getCodeField(){
		return codeField;
	}
	
	public String getSemesterField(){
		return semesterField;
	}

	public String getTitleField(){
		return titleField;
	}
}
