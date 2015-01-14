package com.example.csmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class CoursesList extends ListFragment {
	
	private ArrayList<Courses> courses;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		courses = new ArrayList<Courses>();
		
		generateDummyCourses();
		
		setListAdapter(new CourseListAdapter(getActivity(), R.layout.course_list_fragment, courses));
		ArrayAdapter adapter = (ArrayAdapter)getListAdapter();
		
		adapter.setNotifyOnChange(true);
	}
	
	public void onStart(){
		super.onStart();
		
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getActivity().registerForContextMenu(getListView());
	
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
	}
	
	private void generateDummyCourses(){
		
		for(int i = 0; i < 10; i++){
			courses.add(new Courses("COMPSCI 101", "Semester 1", "Principles of something"));
		}
	}
	
	

}
