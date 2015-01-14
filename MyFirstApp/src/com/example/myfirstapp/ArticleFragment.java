package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		return inflater.inflate(R.layout.activity_article_fragment, container, false);
	}
	
	public void onStart(){
		super.onStart();
		
		Bundle args = getArguments();
		if(args != null){
			
			TextView article = (TextView)getActivity().findViewById(R.id.fragmentID);
			article.setText(args.getString(ActivityWithFragments.FRAGMENT_MESSAGE));
		}
	}

}